package az.processing.msuser.service.genericImpl;

import az.processing.msuser.dto.generic.PaymentDto;
import az.processing.msuser.dto.generic.ProductDto;
import az.processing.msuser.dto.response.TransactionResponseDto;
import az.processing.msuser.entity.CardEntity;
import az.processing.msuser.entity.ProductEntity;
import az.processing.msuser.entity.TransactionEntity;
import az.processing.msuser.entity.TransactionProductEntity;
import az.processing.msuser.entity.UserEntity;
import az.processing.msuser.enums.ProductStatus;
import az.processing.msuser.repository.CardRepository;
import az.processing.msuser.repository.ProductRepository;
import az.processing.msuser.repository.TransactionProductRepository;
import az.processing.msuser.repository.TransactionRepository;
import az.processing.msuser.repository.UserRepository;
import az.processing.msuser.service.factory.TransactionFactory;
import az.processing.msuser.util.EncoderUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionsService implements TransactionFactory {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;


    private final TransactionRepository transactionRepository;

    private final TransactionProductRepository transactionProductRepository;

    private final CardRepository cardRepository;

    private final EncoderUtil encoderUtil;


    public ResponseEntity<?> pay(PaymentDto paymentDto) {
        log.info("Initiating payment process...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserEntity user = userRepository.findUsersEntityByEmail(email);
        log.debug("User email retrieved: {}", email);
        TransactionEntity transaction = new TransactionEntity();
        transaction.setUser(user);

        for (ProductDto productDto : paymentDto.getProducts()) {
            ProductEntity product = productRepository.findById(productDto.getId())
                    .orElse(null);


            if (product == null || product.getStockCount() < productDto.getCount()) {
                log.warn("Insufficient stock for product: {}", productDto.getId());
                return new ResponseEntity<>(ProductStatus.NOT_ENOUGH_PRODUCTS,
                        HttpStatus.BAD_REQUEST);
            }

            CardEntity existingCard =
                    cardRepository.findByCardNumber(encoderUtil.encode(paymentDto.getCardNumber()));


            Date expirationDate = existingCard.getExpirationDate();
            Date currentDate = new Date();


            if (existingCard.getBalance() < productDto.getPrice() * productDto.getCount()) {
                return new ResponseEntity<>(ProductStatus.NOT_ENOUGH_BALANCE,
                        HttpStatus.BAD_REQUEST);
            } else if (expirationDate.before(currentDate)) {
                return new ResponseEntity<>(ProductStatus.CARD_EXPIRED,
                        HttpStatus.BAD_REQUEST);
            }

            Integer stockCount = product.getStockCount() - productDto.getCount();
            product.setStockCount(stockCount);

            TransactionProductEntity transactionProduct = new TransactionProductEntity();
            transactionProduct.setTransaction(transaction);
            transactionProduct.setProduct(product);
            transactionProduct.setQuantity(productDto.getCount());

            transactionProductRepository.save(transactionProduct);
        }
        return new ResponseEntity<>(ProductStatus.SUFFICIENT_PRODUCT, HttpStatus.OK);
    }


    public List<TransactionResponseDto> getAllTransactions() {
        log.info("Retrieving all transactions for the current user...");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findUsersEntityByEmail(email);
        log.debug("User email retrieved: {}", email);

        List<TransactionEntity> allByUserId = transactionRepository.findAllByUser_Id(user.getId());

        List<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
        for (TransactionEntity entity : allByUserId) {
            List<TransactionProductEntity> allByTransactionId =
                    transactionProductRepository.findAllByTransaction_Id(entity.getId());
            for (TransactionProductEntity transactionProduct : allByTransactionId) {
                ProductEntity product = transactionProduct.getProduct();
                Integer quantity = transactionProduct.getQuantity();

                TransactionResponseDto transactionResponseDto = TransactionResponseDto.builder()
                        .product(product)
                        .quantity(quantity)
                        .build();

                transactionResponseDtoList.add(transactionResponseDto);
            }
        }
        return transactionResponseDtoList;
    }
}

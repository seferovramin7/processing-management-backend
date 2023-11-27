package az.processing.msuser.service.genericImpl;

import az.processing.msuser.dto.generic.CardDto;
import az.processing.msuser.dto.request.CardSaveDto;
import az.processing.msuser.entity.CardEntity;
import az.processing.msuser.entity.UserEntity;
import az.processing.msuser.enums.Result;
import az.processing.msuser.repository.CardRepository;
import az.processing.msuser.repository.UserRepository;
import az.processing.msuser.service.factory.CardFactory;
import az.processing.msuser.util.EncoderUtil;
import java.util.ArrayList;
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
public class CardService implements CardFactory {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final EncoderUtil encoderUtil;

    public CardDto mapEntityToDto(CardEntity cardEntity) {
        log.debug("Mapping card entity to DTO");
        return CardDto.builder()
                .cardNumber(encoderUtil.decode(cardEntity.getCardNumber()))
                .expirationDate(cardEntity.getExpirationDate())
                .cvv(encoderUtil.decode(cardEntity.getCvv()))
                .balance(cardEntity.getBalance())
                .userId(cardEntity.getUser().getId())
                .build();
    }

    public ResponseEntity<Result> storeCard(CardSaveDto dto) {
        log.info("Attempting to store card for user");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserEntity user = userRepository.findUsersEntityByEmail(email);


        if (user == null) {
            return new ResponseEntity<>(Result.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        CardEntity cardEntity = CardEntity.builder()
                .cardNumber(encoderUtil.encode(dto.getCardNumber()))
                .cvv(encoderUtil.encode(dto.getCvv()))
                .balance(dto.getBalance())
                .expirationDate(dto.getExpirationDate())
                .user(user)
                .build();

        CardEntity existingCard =
                cardRepository.findByCardNumber(encoderUtil.encode(dto.getCardNumber()));

        if (existingCard != null) {
            return new ResponseEntity<>(Result.ALREADY_EXIST, HttpStatus.BAD_REQUEST);
        }

        cardRepository.save(cardEntity);
        log.debug("Card successfully stored for user");

        return new ResponseEntity<>(Result.SUCCESS, HttpStatus.OK);

    }

    public List<CardDto> retrieveCards() {
        log.info("Retrieving cards for user");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserEntity user = userRepository.findUsersEntityByEmail(email);

        List<CardEntity> cardEntities = cardRepository.findAllByUser_Id(user.getId());

        List<CardDto> cardDtos = new ArrayList<>();
        for (CardEntity cardEntity : cardEntities) {
            cardDtos.add(mapEntityToDto(cardEntity));
        }
        log.debug("Retrieved cards successfully for user");
        return cardDtos;
    }
}

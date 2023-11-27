package az.processing.msuser.service.factory;

import az.processing.msuser.dto.generic.PaymentDto;
import az.processing.msuser.dto.response.TransactionResponseDto;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface TransactionFactory {
    ResponseEntity<?> pay(PaymentDto paymentDto);

    List<TransactionResponseDto> getAllTransactions();
}


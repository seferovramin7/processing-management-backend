package az.processing.msuser.controller;

import az.processing.msuser.dto.generic.PaymentDto;
import az.processing.msuser.dto.response.TransactionResponseDto;
import az.processing.msuser.service.genericImpl.TransactionsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    @PostMapping("add")
    public ResponseEntity<?> addTransactions(@Valid @RequestBody PaymentDto paymentDto,
                                             BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder errorMsg = new StringBuilder("Validation error(s): ");
            for (FieldError error : errors) {
                errorMsg.append(error.getDefaultMessage()).append("; ");
            }
            return ResponseEntity.badRequest().body(errorMsg);
        }

        return ResponseEntity.ok(transactionsService.pay(paymentDto));
    }


    @GetMapping("list")
    public ResponseEntity<List<TransactionResponseDto>> getTransactions() {
        return ResponseEntity.ok(transactionsService.getAllTransactions());
    }
}




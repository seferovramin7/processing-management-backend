package az.processing.msuser.controller;

import az.processing.msuser.dto.generic.CardDto;
import az.processing.msuser.dto.request.CardSaveDto;
import az.processing.msuser.service.factory.CardFactory;
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
@RequestMapping("/card")
public class CardController {

    private final CardFactory cardFactory;

    @PostMapping("/add")
    public ResponseEntity<?> addCard(@Valid @RequestBody CardSaveDto cardDto,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder errorMsg = new StringBuilder("Validation error(s): ");
            for (FieldError error : errors) {
                errorMsg.append(error.getDefaultMessage()).append("; ");
            }
            return ResponseEntity.badRequest().body(errorMsg);
        }
        return cardFactory.storeCard(cardDto);
    }

    @GetMapping
    public ResponseEntity<List<CardDto>> retrieveCards() {
        return ResponseEntity.ok(cardFactory.retrieveCards());
    }
}


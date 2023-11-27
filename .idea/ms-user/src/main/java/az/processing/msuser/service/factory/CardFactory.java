package az.processing.msuser.service.factory;

import az.processing.msuser.dto.generic.CardDto;
import az.processing.msuser.dto.request.CardSaveDto;
import az.processing.msuser.enums.Result;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CardFactory {
    ResponseEntity<Result> storeCard(CardSaveDto dto);

    List<CardDto> retrieveCards();
}


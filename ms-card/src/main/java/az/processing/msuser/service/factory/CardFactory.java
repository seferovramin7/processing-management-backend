package az.processing.msuser.service.factory;

import az.processing.msuser.dto.generic.CardDto;
import az.processing.msuser.dto.generic.ResponseDto;
import az.processing.msuser.dto.request.CardSaveDto;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CardFactory {
    ResponseEntity<ResponseDto> storeCard(CardSaveDto dto);

    List<CardDto> retrieveCards();
}


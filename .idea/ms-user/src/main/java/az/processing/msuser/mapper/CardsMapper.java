package az.processing.msuser.mapper;

import az.processing.msuser.dto.generic.CardDto;
import az.processing.msuser.entity.CardEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardsMapper {

    CardsMapper INSTANCE = Mappers.getMapper(CardsMapper.class);

    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(target = "cardNumber", ignore = true),
    })
    CardDto cardEntityToCardDto(CardEntity cardEntity);

    List<CardDto> cardEntityListToCardDtoList(List<CardEntity> cardEntityList);

    @Mappings({
            @Mapping(target = "user.id", source = "userId"),
    })
    CardEntity cardDtoToCardEntity(CardDto cardDto);

    List<CardEntity> cardDtoListToCardEntityList(List<CardDto> cardDtoList);
}


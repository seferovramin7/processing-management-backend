package az.processing.msuser.mapper;

import az.processing.msuser.dto.generic.CardDto;
import az.processing.msuser.entity.CardEntity;
import az.processing.msuser.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-17T16:35:26+0400",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 17.0.8 (Amazon.com Inc.)"
)
@Component
public class CardsMapperImpl implements CardsMapper {

    @Override
    public CardDto cardEntityToCardDto(CardEntity cardEntity) {
        if ( cardEntity == null ) {
            return null;
        }

        CardDto cardDto = new CardDto();

        cardDto.setUserId( cardEntityUserId( cardEntity ) );
        cardDto.setExpirationDate( cardEntity.getExpirationDate() );
        cardDto.setCvv( cardEntity.getCvv() );
        cardDto.setBalance( cardEntity.getBalance() );

        return cardDto;
    }

    @Override
    public List<CardDto> cardEntityListToCardDtoList(List<CardEntity> cardEntityList) {
        if ( cardEntityList == null ) {
            return null;
        }

        List<CardDto> list = new ArrayList<CardDto>( cardEntityList.size() );
        for ( CardEntity cardEntity : cardEntityList ) {
            list.add( cardEntityToCardDto( cardEntity ) );
        }

        return list;
    }

    @Override
    public CardEntity cardDtoToCardEntity(CardDto cardDto) {
        if ( cardDto == null ) {
            return null;
        }

        CardEntity cardEntity = new CardEntity();

        cardEntity.setUser( cardDtoToUserEntity( cardDto ) );
        cardEntity.setCardNumber( cardDto.getCardNumber() );
        cardEntity.setExpirationDate( cardDto.getExpirationDate() );
        cardEntity.setCvv( cardDto.getCvv() );
        cardEntity.setBalance( cardDto.getBalance() );

        return cardEntity;
    }

    @Override
    public List<CardEntity> cardDtoListToCardEntityList(List<CardDto> cardDtoList) {
        if ( cardDtoList == null ) {
            return null;
        }

        List<CardEntity> list = new ArrayList<CardEntity>( cardDtoList.size() );
        for ( CardDto cardDto : cardDtoList ) {
            list.add( cardDtoToCardEntity( cardDto ) );
        }

        return list;
    }

    private Long cardEntityUserId(CardEntity cardEntity) {
        if ( cardEntity == null ) {
            return null;
        }
        UserEntity user = cardEntity.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserEntity cardDtoToUserEntity(CardDto cardDto) {
        if ( cardDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( cardDto.getUserId() );

        return userEntity;
    }
}

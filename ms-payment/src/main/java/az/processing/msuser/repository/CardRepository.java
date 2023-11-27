package az.processing.msuser.repository;

import az.processing.msuser.entity.CardEntity;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<CardEntity, Long> {

    ArrayList<CardEntity> findAllByUser_Id(Long userId);


     CardEntity findByCardNumber(String cardNumber);

}

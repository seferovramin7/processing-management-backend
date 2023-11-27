package az.processing.msuser.repository;

import az.processing.msuser.entity.TransactionEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByUser_Id(Long userId);

}

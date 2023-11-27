package az.processing.msuser.repository;

import az.processing.msuser.entity.TransactionProductEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionProductRepository
        extends CrudRepository<TransactionProductEntity, Long> {

    List<TransactionProductEntity> findAllByTransaction_Id(Long tranId);
}

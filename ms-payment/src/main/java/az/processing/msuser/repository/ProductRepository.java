package az.processing.msuser.repository;

import az.processing.msuser.entity.ProductEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    List<ProductEntity> findAll();

}

package az.processing.msuser.repository;

import az.processing.msuser.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findUsersEntityByEmail(String email);
}

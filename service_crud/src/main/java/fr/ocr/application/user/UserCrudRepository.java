package fr.ocr.application.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCrudRepository extends JpaRepository<User,Integer>
{
    Optional<UserCrudDtoWeb> findUserByUserName(String userName);
    Optional<UserCrudDto> findUserByIdUser(Integer idUser);
}


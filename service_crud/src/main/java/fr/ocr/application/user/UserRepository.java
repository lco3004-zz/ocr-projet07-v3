package fr.ocr.application.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{
    Optional<UserDtoWeb> findUserByUserName(String userName);
    Optional<UserDto> getUserByIdUser(Integer idUser);
}


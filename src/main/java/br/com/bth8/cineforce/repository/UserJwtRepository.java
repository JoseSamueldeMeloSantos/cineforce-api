package br.com.bth8.cineforce.repository;

import br.com.bth8.cineforce.model.entity.UserJwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserJwtRepository extends JpaRepository<UUID, UserJwt> {

    @Query("SELECT u FROM UserJwt u WHERE u.email = :email")
    UserJwt findByEmail(@Param("email") String email);
}

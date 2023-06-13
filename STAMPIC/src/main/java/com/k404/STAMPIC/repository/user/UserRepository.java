package com.k404.STAMPIC.repository.user;

import com.k404.STAMPIC.domain.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByNickName(String nickName);
    Optional<User> findOneWithAuthoritiesByEmail(String email);
    List<User> findByNickNameContainingIgnoreCase(String nickName);
}

package com.team5.surbee.repository;

import com.team5.surbee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}

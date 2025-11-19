package com.mertalptekin.springrestapplication.infra.repository;

import com.mertalptekin.springrestapplication.domain.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<AppUser, String> {

    Optional<AppUser> findByUsername(String username);
}

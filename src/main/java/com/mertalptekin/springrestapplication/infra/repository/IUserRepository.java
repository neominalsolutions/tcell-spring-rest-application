package com.mertalptekin.springrestapplication.infra.repository;

import com.mertalptekin.springrestapplication.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
}

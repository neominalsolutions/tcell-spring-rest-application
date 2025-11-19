package com.mertalptekin.springrestapplication.infra.repository;

import com.mertalptekin.springrestapplication.domain.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleRepository extends JpaRepository<AppRole, Integer> {

    // role varsa rolleri bul ve getir
    List<AppRole> findRoleByNameContainingIgnoreCase(String roleName);

    // Çoklu rol isimlerine göre rolleri bul (IN sorgusu) - Tam eşleşme
    List<AppRole> findByNameIgnoreCaseIn(List<String> roleNames);

    // Çoklu rol isimlerine göre rolleri bul - case insensitive
    @Query("SELECT r FROM AppRole r WHERE LOWER(r.name) IN (SELECT LOWER(name) FROM AppRole WHERE name IN :roleNames)")
    List<AppRole> findByNameInIgnoreCase(@Param("roleNames") List<String> roleNames);



}

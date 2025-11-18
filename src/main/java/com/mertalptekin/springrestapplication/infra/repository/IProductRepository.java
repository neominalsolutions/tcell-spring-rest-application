package com.mertalptekin.springrestapplication.infra.repository;

import com.mertalptekin.springrestapplication.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// Interfaceler bizim portumuz -> Interface üzerindne implemente olan classlarda bizim Adapterlarımız olur.
// Packagelar arası geçişler interface ile sağlanır. -> Hexagonal Architecture

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByName(String name,Pageable pageable);

    Page<Product> findAllByNameContains(String name, Pageable pageable);

}

package com.mertalptekin.springrestapplication.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;


    // mappedBy -> indicates that the "category" field in the Product entity owns the relationship
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products; // navigation property -> association with Product entity

}

// EAGER olunca atılan Sorgu getCategoryById ile test ettik
//select
//c1_0.id,
//c1_0.name,
//p1_0.category_id,
//p1_0.id,
//p1_0.name,
//p1_0.price,
//p1_0.stock
//        from
//categories c1_0
//left join
//products p1_0
//on c1_0.id=p1_0.category_id
//        where
//c1_0.id=?

// CASCADE ALL: This option propagates all operations (PERSIST, MERGE, REMOVE, REFRESH, DETACH) from the parent entity to the associated child entities. For example, if a Category is deleted, all associated Products will also be deleted.
// CASCADE PERSIST: This option propagates only the PERSIST operation from the parent entity to the associated child entities. When a new Category is saved, all new associated Products will also be saved automatically.
// CASCADE REMOVE: This option propagates only the REMOVE operation from the parent entity to the associated child entities. When a Category is deleted, all associated Products will also be deleted.
// CASCADE MERGE: This option propagates only the MERGE operation from the parent entity to the associated child entities. When a detached Category is merged, all associated Products will also be merged.
// CASCADE REFRESH: This option propagates only the REFRESH operation from the parent entity to the associated child entities. When a Category is refreshed, all associated Products will also be refreshed.

// FETCH TYPES:
// EAGER: With EAGER fetching, when a Category entity is loaded from the database, all associated Product entities are loaded immediately along with it.
// LAZY: With LAZY fetching, associated Product entities are not loaded immediately when a Category entity is loaded. Instead, they are loaded on-demand when you access the products property for the first time.

// var c = new Category();
// c.getProducts().add(new Product());
// categoryRepository.save(c); be persisted automatically
// entityManager.persist(c); // if CascadeType.PERSIST is set, the new Product will







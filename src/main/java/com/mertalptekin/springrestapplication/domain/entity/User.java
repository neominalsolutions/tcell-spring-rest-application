package com.mertalptekin.springrestapplication.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// User Entity spring Security ile entegre olacak şekilde UserDetails implement etmelidir.

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // user load ederken rolleride hemen gelsin
    // Many to Many ilişki CASCADE eklemedik çünkü roller genelde sabit kalır
    // Çokaçok ilişkiler tek taraflı olabilir çift taraflı da olabilir
    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST, // user üzerinden rol oluşturacağımız için persist olmalı, User silersek Role silinmesin diye REMOVE eklemedik
            CascadeType.MERGE
    })
    @JoinTable(
            name = "user_roles", // join table name, ara tablo
            joinColumns = @JoinColumn(name = "user_id"), // FK
            inverseJoinColumns = @JoinColumn(name = "role_id") // FK
    )
    private List<Role> roles; // unidirectional association with Role entity


    // kullanıcıya atanan yetkiler, authenticated kullanıcı yetkileri buradan gelecek
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }
}

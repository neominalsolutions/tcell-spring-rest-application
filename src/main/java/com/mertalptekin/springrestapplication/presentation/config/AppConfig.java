package com.mertalptekin.springrestapplication.presentation.config;

import com.mertalptekin.springrestapplication.application.products.request.product.ProductCreateRequest;
import com.mertalptekin.springrestapplication.application.users.UserDto;
import com.mertalptekin.springrestapplication.domain.entity.Product;
import com.mertalptekin.springrestapplication.domain.service.CustomUserDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    // veritabanından kullanıcı bilgilerini alıp spring security'e entegre etmek için kullanılır.
    private final CustomUserDetailService customUserDetailService;

    public AppConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Not: Yeni yöntemde constructor ile userDetailsService set ediliyor.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Uygulama içerisinde config değerleri okumak ve aynı zamanda uygulama için gerekli olan bean tanımlarını yapmak için kullanılır.


    @Bean(name = "messageBean")
    String message(ModelMapper mapper) {
        mapper.map(UserDto.class, System.out);
        return "Hello Spring Bean";
    }

    @Bean(name = "messageBean1")
    String message1(){
        return "Hello Spring Bean 1";
    }

    // Spring Context'e tanımlanan nesnlere Bean diyoruz
    // Manuel tanımlama için Bean anatasyonu kullanır.
    // 3rd party kütüphaneleri de Bean olarak tanımlamak için kullandık. Bazen böyle ihtiyaçlarımız oluyor çünkü Model mapper servisini bir çok yerde kullanmamız gerekiyor. Bu tarz durumlar için Bean tanımlaması yapıyoruz.
    // 2. kısım model Mapper kullanımı
    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();



        // Record sınıfları için gerekli konfigürasyon
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

//        modelMapper.createTypeMap(ProductCreateRequest.class, Product.class)
//                .setProvider(request -> {
//                    ProductCreateRequest source = (ProductCreateRequest) request.getSource();
//                    Product target = new Product();
//                    target.setName(source.name());
//                    target.setPrice(source.price());
//                    target.setStock(source.stock());
//                    target.setCategoryId(source.categoryId());
//                    target.setCategory(null);
//                    return target;
//                });

        return modelMapper;
    }

}

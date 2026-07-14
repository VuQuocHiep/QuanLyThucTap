package com.Hiep.B23DCCN295.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Hiep.B23DCCN295.entity.RoleEntity;
import com.Hiep.B23DCCN295.entity.UserEntity;
import com.Hiep.B23DCCN295.enums.Role;
import com.Hiep.B23DCCN295.repository.RoleRepository;
import com.Hiep.B23DCCN295.repository.UserRopository;

@Configuration
public class ApplicationInitCofig {
    @Bean
    ApplicationRunner applicationRunner(UserRopository userRopository,RoleRepository roleRepository){
        return arg->{
            if(userRopository.findByRole_Name(Role.ADMIN.name()).isEmpty()){
                Set<RoleEntity> role = new HashSet<>();
                RoleEntity x = roleRepository.findByName(Role.ADMIN.name()).orElseThrow(() -> new RuntimeException("ADMIN role not found"));
                role.add(x);
                UserEntity userEntity = new UserEntity();
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                userEntity.setFirstname("Hiep");
                userEntity.setLastname("VuQuoc");
                userEntity.setEmail("vuquochiep2005@gmail.com");
                userEntity.setPassword(passwordEncoder.encode("123456789"));
                userEntity.setRole(role);
                userRopository.save(userEntity);
            }
        };
    } 
}

package com.dev.microservices.repo;

import com.dev.microservices.user.Appuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepo extends JpaRepository<Appuser,Integer> {

   Optional<Appuser> findByEmail(String email);

   @Transactional
   @Modifying
   @Query("UPDATE Appuser a " +
           "SET a.enabled=true where a.email=?1")
    int enableAppUser(String email);
}

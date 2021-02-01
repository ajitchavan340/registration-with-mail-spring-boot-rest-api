package com.dev.microservices.repo;

import com.dev.microservices.communication.EmailSender;
import com.dev.microservices.token.ConfirmationToken;
import com.dev.microservices.token.ConfirmationTokenService;
import com.dev.microservices.user.Appuser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepo appUserRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return  appUserRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    @Transactional
    public String signUp(Appuser appuser){
        boolean exist =
                appUserRepo.findByEmail(appuser.getEmail()).isPresent();
        if (exist){
            throw new IllegalStateException("email is already exist");
        }
        String encodePassword = bCryptPasswordEncoder.encode(appuser.getPassword());
        appuser.setPassword(encodePassword);
        appUserRepo.save(appuser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
             token,
             LocalDateTime.now(),
             LocalDateTime.now().plusMinutes(15),
             appuser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableUser(String email){
        return appUserRepo.enableAppUser(email);
    }
}

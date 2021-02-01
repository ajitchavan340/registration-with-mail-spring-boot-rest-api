package com.dev.microservices.token;

import com.dev.microservices.user.Appuser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    private LocalDateTime confirmAt;

    @ManyToOne
    @JoinColumn(nullable = false,
    name = "app_user_id" )
    private Appuser appuser;

    public ConfirmationToken(
                             String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt ,
                             Appuser appuser) {
        this.token=token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appuser = appuser;
    }
}

package br.com.zup.bootcamp.domain.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDateTime;

// Intrinsic charge = 0
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String login;

    @NotBlank
    @Length(min = 6)
    @Column(nullable = false)
    private String password;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime registerDate;

    @Deprecated
    public User(){}

    public User(@Email @NotBlank String login, @Length(min = 6) @NotBlank String password, EntityManager manager) {
        this.login = login;
        this.password = password;
        this.registerDate = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }
}

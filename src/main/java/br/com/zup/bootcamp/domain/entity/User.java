package br.com.zup.bootcamp.domain.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

// Intrinsic charge = 2
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
    @Size(min = 6)
    @Length(min = 6)
    @Column(nullable = false)
    private String password;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<Opinion> opinions = new ArrayList<>();

    @Deprecated
    public User(){}

    public User(@Email @NotBlank String login, @Size(min = 6) @Length(min = 6) @NotBlank String password) {
        this.login = login;
        this.password = password;
        this.registerDate = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }
}

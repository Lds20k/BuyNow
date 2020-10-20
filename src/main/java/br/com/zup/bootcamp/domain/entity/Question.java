package br.com.zup.bootcamp.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

// Intrinsic charge = 2
@Entity
public class Question {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime creationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @Deprecated
    public Question() {}

    public Question(@NotBlank String title, @NotNull Product product, @NotNull User user) {
        this.title = title;
        this.creationDate = LocalDateTime.now();
        this.product = product;
        this.user = user;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public Product getProduct() {
        return product;
    }
}

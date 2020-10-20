package br.com.zup.bootcamp.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// Intrinsic charge = 1
@Entity
public class Image {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String link;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Product product;

    @Deprecated
    public Image(){}

    public Image(@NotBlank String link, @NotNull Product product) {
        this.link = link;
        this.product = product;
    }

    public String getLink() {
        return this.link;
    }
}

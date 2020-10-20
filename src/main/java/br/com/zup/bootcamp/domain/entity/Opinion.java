package br.com.zup.bootcamp.domain.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

// Intrinsic charge = 2
@Entity
public class Opinion  implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer rating;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Length(max = 500)
    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Product product;

    @Deprecated
    public Opinion() {}

    public Opinion(@NotNull @Min(1) @Max(5) Integer rating, @NotBlank String title, @NotBlank @Length(max = 500) String description, User user, Product product) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.user = user;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRating() {
        return rating;
    }
}

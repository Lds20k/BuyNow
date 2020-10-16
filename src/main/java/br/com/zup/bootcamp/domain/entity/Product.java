package br.com.zup.bootcamp.domain.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

// Intrinsic charge = 2
@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @DecimalMin("0.01")
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

    @Min(1)
    @NotNull
    @Column(nullable = false)
    private int availableQuantity;

    @Size(min = 3)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Characteristic> characteristics = new ArrayList<>();

    @NotBlank
    @Length(max = 1000)
    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Category category;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    public Product(@NotBlank String name, @DecimalMin("0.01") @NotNull BigDecimal price, @Min(1) @NotNull int availableQuantity, @NotBlank @Length(max = 1000) String description, @NotNull Category category) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.description = description;
        this.category = category;
        this.creationDate = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setCharacteristics(Collection<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}

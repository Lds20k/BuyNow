package br.com.zup.bootcamp.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

// Intrinsic charge = 1
@Entity
public class Category implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank(message = "{notblank}")
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "predecessor_id", referencedColumnName = "id")
    private Category predecessor;

    @OneToMany(mappedBy = "predecessor", cascade = CascadeType.ALL)
    private Collection<Category> successors = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private Collection<Product> products = new ArrayList<>();

    @Deprecated
    public Category(){};

    public Category(@NotBlank String name) {
        this.name = name;
    }

    public Category(@NotBlank String name, Category predecessor) {
        super();
        this.name = name;
        this.predecessor = predecessor;
        this.predecessor.successors.add(this);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static Category createCategoryByIdFactory(String id){
        Category category = new Category();
        category.setId(id);
        return category;
    }
}

package br.com.zup.bootcamp.domain.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Intrinsic charge = 12
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Image> images = new ArrayList<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Collection<Opinion> opinions = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Collection<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Collection<Purchase> purchases = new ArrayList<>();

    @Deprecated
    public Product(){}

    public Product(String id) {
        this.id = id;
    }

    public Product(@NotBlank String name, @DecimalMin("0.01") @NotNull BigDecimal price, @Min(1) @NotNull int availableQuantity, @NotBlank @Length(max = 1000) String description, @NotNull Category category, @NotNull User user) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.description = description;
        this.category = category;
        this.creationDate = LocalDateTime.now();
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Collection<Image> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Collection<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Collection<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Opinion> getOpinions() {
        return opinions;
    }

    public Collection<Question> getQuestions() {
        return questions;
    }

    public boolean reduceStock(@Positive Integer quantity) {
        Assert.isTrue(quantity > 0, "Quantity must be greater than 0");
        if(quantity > this.availableQuantity) return false;

        this.availableQuantity = this.availableQuantity - quantity;
        return true;
    }

    public void setImages(Collection<Image> images) {
        this.images = images;
    }

    public boolean isUserOwner(User userEntity) {
        return this.user.equals(userEntity);
    }

    public Collection<Map<String, String>> characteristicsToCollection(){
        Collection<Map<String, String>> characteristicsCollection = new ArrayList<>();
        for(Characteristic characteristic : this.characteristics) {
            Map<String, String> aux = new HashMap<>();
            aux.put(characteristic.getTitle(), characteristic.getValue());
            characteristicsCollection.add(aux);
        }
        return characteristicsCollection;
    }

    public Collection<Map<String, String>> opinionsToCollection(){
        Collection<Map<String, String>> opinionsCollection = new ArrayList<>();
        for(Opinion opinion : this.opinions){
            Map<String, String> aux = new HashMap<>();
            aux.put(opinion.getTitle(), opinion.getDescription());
            opinionsCollection.add(aux);
        }
        return opinionsCollection;
    }

    public Float calculateRatingAverage(){
        float sum = 0;
        for (Opinion opinion : this.opinions)
            sum += opinion.getRating();
        return sum / this.opinions.size();
    }

    // Ajustar, criar estrutura genérica para imagesToCollection e questionsToCollection
    // Pois são basicamente as mesmas coisas
    public Collection<String> imagesToCollection() {
        Collection<String> imagesCollection = new ArrayList<>();
        for(Image image : this.images)
            imagesCollection.add(image.getLink());

        return imagesCollection;
    }

    public Collection<String> questionsToCollection() {
        Collection<String> questionsCollection = new ArrayList<>();
        for(Question question : this.questions)
            questionsCollection.add(question.getTitle());
        return questionsCollection;
    }
}

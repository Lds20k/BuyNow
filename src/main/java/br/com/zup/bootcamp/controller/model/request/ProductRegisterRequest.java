package br.com.zup.bootcamp.controller.model.request;

import br.com.zup.bootcamp.domain.entity.Category;
import br.com.zup.bootcamp.domain.entity.Characteristic;
import br.com.zup.bootcamp.domain.entity.Product;
import br.com.zup.bootcamp.domain.entity.User;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

// Intrinsic charge = 5
public class ProductRegisterRequest {
    @NotBlank(message = "{mandatory}")
    private String name;

    @DecimalMin("0.01")
    @NotNull(message = "{mandatory}")
    private BigDecimal price;

    @Min(1)
    @NotNull(message = "{mandatory}")
    private int availableQuantity;

    @NotEmpty(message = "{mandatory}")
    @Size(min = 3, message = "{size.min}")
    private Collection<@Valid CharacteristicRequest> characteristics = new ArrayList<>();

    @NotBlank(message = "{mandatory}")
    @Size(max = 1000, message = "{size.max}")
    private String description;

    @NotBlank(message = "{mandatory}")
    private String category;

    public String getCategory() {
        return category;
    }

    /**
     * @param name String do nome do produto que não pode estar em branco ou nula
     * @param price BigDecimal do preço que tem que ser maior que ou igual 0.01 e não pode ser nulo
     * @param availableQuantity Inteiro da quantidade disponível que tem que ser maior que ou igual a 1  e não pode ser nulo
     * @param characteristics Collection com todas as características que precisa ter no mínimo 3 itens e não pode esta vazia
     * @param description String com a descrição do produto que tem o tamanho maximo de 1000 caracteres
     * @param category String da UUID da categoria
     */
    public ProductRegisterRequest(@NotBlank String name, @DecimalMin("0.01") @NotNull BigDecimal price, @Min(1) @NotNull int availableQuantity, @Size(min = 3) Collection<@Valid CharacteristicRequest> characteristics, @NotBlank @Size(max = 1000) String description, @NotBlank String category) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.characteristics = characteristics;
        this.description = description;
        this.category = category;
    }

    public Collection<CharacteristicRequest> getCharacteristics() {
        return characteristics;
    }

    /**
     * @param user User que é dono do produto, não pode ser nulo
     * @return Product com os atributos da request, categoria e as características convertidas
     */
    public Product toModel(@NotNull User user) {
        Category categoryEntity = Category.createCategoryByIdFactory(this.category);
        Product productEntity = new Product(this.name, this.price, this.availableQuantity, this.description, categoryEntity, user);

        Collection<Characteristic> characteristicsEntities = new ArrayList<>();
        for (CharacteristicRequest characteristic : this.characteristics){
            characteristicsEntities.add(characteristic.toModel(productEntity));
        }
        productEntity.setCharacteristics(characteristicsEntities);

        return productEntity;
    }
}

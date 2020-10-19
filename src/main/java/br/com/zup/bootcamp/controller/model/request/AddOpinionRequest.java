package br.com.zup.bootcamp.controller.model.request;

import br.com.zup.bootcamp.controller.validator.annotation.ExistInTable;
import br.com.zup.bootcamp.domain.entity.Opinion;
import br.com.zup.bootcamp.domain.entity.Product;
import br.com.zup.bootcamp.domain.entity.User;

import javax.validation.constraints.*;

// Intrinsic charge = 2
public class AddOpinionRequest {

    @NotNull(message = "{mandatory}")
    @Min(value = 1, message = "{range.min}")
    @Max(value = 5, message = "{range.max}")
    private Integer rating;

    @NotBlank(message = "{mandatory}")
    private String title;

    @NotBlank(message = "{mandatory}")
    @Size(max = 500, message = "{size.max}")
    private String description;

    @NotBlank(message = "{mandatory}")
    @ExistInTable(domain = Product.class)
    private String product;

    public AddOpinionRequest(@NotNull Integer rating, @NotBlank String title, @NotBlank @Size(max = 500) String description, @NotBlank String product) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.product = product;
    }

    /**
     * @param user User que ser adicionado como dono da opinião
     * @return Option com todos os campos da request convertidos
     */
    public Opinion toModel(User user){
        Product productEntity = new Product(this.product);
        return new Opinion(this.rating, this.title, this.description, user, productEntity);
    }
}

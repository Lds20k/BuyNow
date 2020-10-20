package br.com.zup.bootcamp.controller.model.request;

import br.com.zup.bootcamp.controller.validator.annotation.ExistInTable;
import br.com.zup.bootcamp.domain.entity.Product;
import br.com.zup.bootcamp.domain.entity.Question;
import br.com.zup.bootcamp.domain.entity.User;

import javax.validation.constraints.NotBlank;

// Intrinsic charge = 3
public class AddQuestionRequest {

    @NotBlank(message = "{mandatory}")
    private String title;

    @NotBlank(message = "{mandatory}")
    @ExistInTable(domain = Product.class)
    private String product;

    public AddQuestionRequest(@NotBlank String title, @NotBlank String product) {
        this.title = title;
        this.product = product;
    }

    /**
     * @param userEntity User que sera associado a pergunta
     * @return Um objeto do tipo Question com todos os atributos convertido, com User e Product
     */
    public Question toModel(User userEntity) {
        Product productEntity = new Product(this.product);
        return new Question(this.title, productEntity, userEntity);
    }
}

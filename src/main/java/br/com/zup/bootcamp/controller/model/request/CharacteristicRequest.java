package br.com.zup.bootcamp.controller.model.request;

import br.com.zup.bootcamp.domain.entity.Characteristic;
import br.com.zup.bootcamp.domain.entity.Product;

import javax.validation.constraints.NotBlank;

// Intrinsic charge = 1
public class CharacteristicRequest {

    @NotBlank(message = "{mandatory}")
    private String title;

    @NotBlank(message = "{mandatory}")
    private String value;

    public CharacteristicRequest(@NotBlank String title, @NotBlank String value) {
        this.title = title;
        this.value = value;
    }

    /**
     * @param productEntity Produto que a característica pertence
     * @return Characteristic com os atributos da request convertidos
     */
    public Characteristic toModel(Product productEntity) {
        return new Characteristic(this.title, this.value, productEntity);
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }
}

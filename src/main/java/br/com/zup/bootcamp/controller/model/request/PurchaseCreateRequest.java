package br.com.zup.bootcamp.controller.model.request;

import br.com.zup.bootcamp.enumerated.GatewayPayment;
import br.com.zup.bootcamp.controller.validator.annotation.ExistInTable;
import br.com.zup.bootcamp.domain.entity.Product;
import br.com.zup.bootcamp.domain.entity.Purchase;
import br.com.zup.bootcamp.domain.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

// Intrinsic charge = 3
public class PurchaseCreateRequest {

    @ExistInTable(domain = Product.class)
    @NotBlank(message = "{mandatory}")
    private String product;

    @Positive(message = "{positive}")
    @NotNull(message = "{mandatory}")
    private Integer quantity;

    @NotNull(message = "{mandatory}")
    @Enumerated
    private GatewayPayment gateway;

    public PurchaseCreateRequest(@NotBlank(message = "{mandatory}") String product, @Positive(message = "{positive}") @NotNull(message = "{mandatory}") Integer quantity, @NotNull GatewayPayment gateway) {
        this.product = product;
        this.quantity = quantity;
        this.gateway = gateway;
    }

    public Purchase toModel(User userEntity, EntityManager manager) {
        Product productEntity = manager.find(Product.class, this.product);
        return new Purchase(this.quantity, this.gateway, productEntity, userEntity);
    }
}

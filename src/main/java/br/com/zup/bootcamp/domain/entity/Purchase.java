package br.com.zup.bootcamp.domain.entity;

import br.com.zup.bootcamp.enumerated.GatewayPayment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

// Intrinsic charge = 3
@Entity
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Positive
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Enumerated
    @Column(nullable = false)
    private GatewayPayment gatewayPayment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private User user;

    @Deprecated
    public Purchase() {}

    public Purchase(@Positive Integer quantity, @NotNull GatewayPayment gatewayPayment, @NotNull Product product, @NotNull User user) {
        this.quantity = quantity;
        this.gatewayPayment = gatewayPayment;
        this.product = product;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    public GatewayPayment getGatewayPayment() {
        return gatewayPayment;
    }

    public boolean reduceStock() {
        return product.reduceStock(this.quantity);
    }
}

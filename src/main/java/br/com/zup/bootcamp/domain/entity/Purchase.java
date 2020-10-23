package br.com.zup.bootcamp.domain.entity;

import br.com.zup.bootcamp.enumerated.GatewayPayment;
import br.com.zup.bootcamp.enumerated.PurchaseStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Transaction> transactions = new ArrayList<>();

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

    public Integer getQuantity() {
        return quantity;
    }

    public GatewayPayment getGatewayPayment() {
        return gatewayPayment;
    }

    public boolean reduceStock() {
        return product.reduceStock(this.quantity);
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }

    public boolean allReadySuccessful(){
        return this.isSuccessful();
    }

    public boolean isSuccessful() {
        for(Transaction transaction : this.transactions){
            if(transaction.getStatus().equals(PurchaseStatus.success)) return  true;
        }
        return false;
    }

}

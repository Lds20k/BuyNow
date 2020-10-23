package br.com.zup.bootcamp.domain.entity;

import br.com.zup.bootcamp.enumerated.PurchaseStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

// Intrinsic charge = 2
@Entity
public class Transaction {

    @Id
    private String id;

    @NotNull
    @Enumerated
    @Column(nullable = false)
    private PurchaseStatus status;

    @Column(nullable = false)
    private LocalDateTime processDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Purchase purchase;

    @Deprecated
    public Transaction() {}

    public Transaction(String id, @NotNull PurchaseStatus status, LocalDateTime processDate, Purchase purchase) {
        this.id = id;
        this.status = status;
        this.processDate = processDate;
        this.purchase = purchase;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

}

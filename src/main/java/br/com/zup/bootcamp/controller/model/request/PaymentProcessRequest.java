package br.com.zup.bootcamp.controller.model.request;

import br.com.zup.bootcamp.domain.entity.Purchase;
import br.com.zup.bootcamp.domain.entity.Transaction;
import br.com.zup.bootcamp.enumerated.PurchaseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

// Intrinsic charge = 2
public class PaymentProcessRequest {

    @NotBlank(message = "{mandatory}")
    private String transaction;

    @NotBlank(message = "{mandatory}")
    @Pattern(regexp = "SUCESSO|ERRO|1|0")
    private String status;

    @NotNull(message = "{mandatory}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processDate;

    public PaymentProcessRequest(@NotBlank String transaction, @NotBlank @Pattern(regexp = "SUCESSO|ERROR|1|0") String status) {
        this.transaction = transaction;
        this.status = status;
    }

    /**
     * @param purchase Purchase que a transação sera adicionada
     * @return Um Transaction com os dados da request convertidos
     */
    public Transaction toModel(Purchase purchase) {
        if(status.equals("1") || status.equals("SUCESSO"))
            return new Transaction(this.transaction ,PurchaseStatus.success, this.processDate, purchase);

        return new Transaction(this.transaction, PurchaseStatus.error, this.processDate, purchase);
    }
}

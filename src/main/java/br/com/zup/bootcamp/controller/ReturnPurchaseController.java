package br.com.zup.bootcamp.controller;

import br.com.zup.bootcamp.component.Email;
import br.com.zup.bootcamp.controller.model.request.PaymentProcessRequest;
import br.com.zup.bootcamp.domain.entity.Purchase;
import br.com.zup.bootcamp.domain.entity.Transaction;
import br.com.zup.bootcamp.services.InvoiceClient;
import br.com.zup.bootcamp.services.RankingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// Intrinsic charge = 8
@RestController
public class ReturnPurchaseController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Email email;

    @Autowired
    private InvoiceClient invoiceClient;

    @Autowired
    private RankingClient rankingClient;

    @PostMapping("/return-pagseguro/{id}")
    public ResponseEntity<?> pagseguroPaymentProcess(@PathVariable("id") String idPurchase, @Valid @RequestBody PaymentProcessRequest request){
        return this.PaymentProcess(idPurchase, request);
    }

    @PostMapping("/return-paypal/{id}")
    public ResponseEntity<?> paypalPaymentProcess(@PathVariable("id") String idPurchase, @Valid @RequestBody PaymentProcessRequest request){
        return this.PaymentProcess(idPurchase, request);
    }

    /**
     * @param idPurchase id da compra
     * @param request a body com os dados da transação
     * @return Caso bem sucedido retorna HttpStatus 200, e com o status da transferência
     */
    @Transactional
    private ResponseEntity<?> PaymentProcess(String idPurchase, PaymentProcessRequest request){
        Purchase purchaseEntity = manager.find(Purchase.class, idPurchase);
        Assert.isTrue(!purchaseEntity.allReadySuccessful(), "Transaction all ready finished");

        Transaction newTransactionEntity = request.toModel(purchaseEntity);
        purchaseEntity.addTransaction(newTransactionEntity);

        if(purchaseEntity.isSuccessful()) {
            invoiceClient.execute(purchaseEntity.getId(), purchaseEntity.getUser().getId());
            rankingClient.execute(purchaseEntity.getId(), purchaseEntity.getProduct().getUser().getId());
            email.send(purchaseEntity.getUser().getEmail(), "Your transaction was approved, transaction status: "+ newTransactionEntity.getStatus());
        }else
            email.sendTransactionFail(purchaseEntity);


        manager.merge(purchaseEntity);

        return ResponseEntity.ok(newTransactionEntity.getStatus());
    }
}

package br.com.zup.bootcamp.controller;

import br.com.zup.bootcamp.controller.model.Email;
import br.com.zup.bootcamp.controller.model.request.PurchaseCreateRequest;
import br.com.zup.bootcamp.domain.entity.Purchase;
import br.com.zup.bootcamp.domain.entity.User;
import br.com.zup.bootcamp.enumerated.GatewayPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

// Intrinsic charge = 7
@RestController
@RequestMapping(CheckoutController.path)
public class CheckoutController {

    public static final String path = "/checkout";

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Email email;

    /**
     * @param request Uma body com os dados da compra
     * @param userId Id do usuário que será associado a compra
     * @param builder Um UriComponentsBuilder para criar a URI de retorno
     * @return Caso bem sucedido retorna HttpStatus 302, o link de redirecionamento e o link de retorno na body
     * @throws BindException Caso não seja possível abater o estoque é lançada a exceção
     */
    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@Valid @RequestBody PurchaseCreateRequest request, @RequestHeader("user") String userId, UriComponentsBuilder builder) throws BindException {
        User userEntity = manager.find(User.class, userId);
        if(userEntity == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Purchase newPurchaseEntity = request.toModel(userEntity, manager);

        if(newPurchaseEntity.reduceStock()){
            email.send(newPurchaseEntity.getProduct().getUser().getEmail(), "A user has purchase your product!");
            manager.persist(newPurchaseEntity);
            if(newPurchaseEntity.getGatewayPayment().equals(GatewayPayment.paypal)){
                String returnURI = builder
                        .path("/return-paypal/{id}")
                        .buildAndExpand(newPurchaseEntity.getId())
                        .toString();
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .body("paypal.com/" + newPurchaseEntity.getId() + "?redirectUrl=" + returnURI);
            }

            String returnURI = builder
                    .path("/return-pagseguro/{id}")
                    .buildAndExpand(newPurchaseEntity.getId())
                    .toString();
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body("pagseguro.com?returnId=" + newPurchaseEntity.getId() + "&redirectUrl=" + returnURI);

        }

        BindException stockProblem = new BindException(request, "purchaseCreateRequest");
        stockProblem.reject(null, "insufficient stock to make the purchase.");
        throw stockProblem;
    }
}

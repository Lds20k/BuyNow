package br.com.zup.bootcamp.component;

import br.com.zup.bootcamp.controller.QuestionController;
import br.com.zup.bootcamp.domain.entity.Purchase;
import br.com.zup.bootcamp.enumerated.GatewayPayment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// Intrinsic charge = 1
@Component
public class Email {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Value("${dev_mode}")
    private Boolean fake;

    public void send(String email, String message){
        if(fake) {
            logger.info("(Dev) Email send to ".concat(email).concat(", message: ").concat(message));
            return;
        }

        // Código para enviar o email em produção omitido...
        logger.info("Email send to ".concat(email).concat(email).concat(", message: ").concat(message));
    }

    public void sendTransactionFail(Purchase purchaseEntity) {
        if(purchaseEntity.getGatewayPayment().equals(GatewayPayment.paypal)){
            this.send(
                    purchaseEntity.getUser().getEmail(),
                    "paypal.com/" + purchaseEntity.getId() +
                            "?redirectUrl=" + ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
            );
        }else{
            this.send(
                    purchaseEntity.getUser().getEmail(),
                    "pagseguro.com?returnId=" + purchaseEntity.getId() +
                            "&redirectUrl=" + ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
            );
        }
    }
}

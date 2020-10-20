package br.com.zup.bootcamp.controller.model;

import br.com.zup.bootcamp.controller.QuestionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// Intrinsic charge = 1
@Component
public class Email {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Value("${dev_mode}")
    private Boolean fake;

    public void send(String email, String message){
        if(fake) logger.info("(Dev) Email send to ".concat(email).concat(", message: ").concat(message));

        // Código para enviar o email em produção omitido...
        logger.info("Email send to ".concat(email).concat(email).concat(", message: ").concat(message));
    }
}

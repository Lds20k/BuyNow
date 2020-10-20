package br.com.zup.bootcamp.controller;

import br.com.zup.bootcamp.controller.model.Email;
import br.com.zup.bootcamp.controller.model.request.AddQuestionRequest;
import br.com.zup.bootcamp.domain.entity.Question;
import br.com.zup.bootcamp.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// Intrinsic charge = 5
@RestController
@RequestMapping(QuestionController.path)
public class QuestionController {

    public static final String path = "/question";

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Email email;

    /**
     * @param request Body da request com a pergunta e o produto que sera feito a pergunta
     * @param userId Id do usuário "logado" que estará associado a pergunta
     * @param builder Um UriComponentsBuilder para criar a URI de retorno
     * @return Caso bem sucedido retorna HttpStatus 201, o URI com o local onde foi criado e uma body vazia
     */
    @PostMapping
    @Transactional
    public ResponseEntity<?> add(@Validated @RequestBody AddQuestionRequest request, @RequestHeader("user") String userId, UriComponentsBuilder builder){
        User userEntity = manager.find(User.class, userId);
        if(userEntity == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Question newQuestionEntity = request.toModel(userEntity);
        manager.persist(newQuestionEntity);

        email.send(userEntity.getEmail(), "You have a new question to your product");

        return ResponseEntity.created(
                builder
                        .path(path.concat("/{id}"))
                        .buildAndExpand(newQuestionEntity.getId())
                        .toUri()
        ).build();
    }
}

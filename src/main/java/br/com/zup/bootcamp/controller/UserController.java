package br.com.zup.bootcamp.controller;

import br.com.zup.bootcamp.controller.model.UserRegisterRequest;
import br.com.zup.bootcamp.controller.validator.DuplicateLoginEntryValidator;
import br.com.zup.bootcamp.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// Intrinsic charge = 3
@RestController
@RequestMapping(UserController.path)
public class UserController {

    public static final String path = "/user";

    @PersistenceContext
    EntityManager manager;

    @Autowired
    private DuplicateLoginEntryValidator duplicateLoginEntryValidator;

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(duplicateLoginEntryValidator);
    }

    /**
     * @param request Body da request com login e password
     * @param builder Um UriComponentsBuilder para criar a URI de retorno
     * @return Caso bem sucedido retorna HttpStatus 201, o URI com o local onde foi criado e uma body vazia
     */
    @PostMapping
    @Transactional
    public ResponseEntity register(@Validated @RequestBody UserRegisterRequest request, UriComponentsBuilder builder){
        User newUser = request.toModel(manager);
        manager.persist(newUser);
        return ResponseEntity.created(builder.path(path.concat("/{id}")).buildAndExpand(newUser.getId()).toUri()).build();
    }
}

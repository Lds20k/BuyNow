package br.com.zup.bootcamp.controller;

import br.com.zup.bootcamp.controller.model.request.AddOpinionRequest;
import br.com.zup.bootcamp.controller.validator.annotation.ExistInTable;
import br.com.zup.bootcamp.domain.entity.Opinion;
import br.com.zup.bootcamp.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// Intrinsic charge = 4
@RestController
@RequestMapping(OpinionController.path)
public class OpinionController {

    public static final String path = "/opinion";

    @PersistenceContext
    private EntityManager manager;

    /**
     * @param request Body da request com os dados da opinião dada pelo usuário
     * @param userId Id do usuário que está dando a opnião
     * @param builder Um UriComponentsBuilder para criar a URI de retorno
     * @return Caso bem sucedido retorna HttpStatus 201, o URI com o local onde foi criado e um body vazia
     */
    @PostMapping
    @Transactional
    public ResponseEntity<?> add(@Validated @RequestBody AddOpinionRequest request, @RequestHeader("user") String userId, UriComponentsBuilder builder){
        User userEntity = manager.find(User.class, userId);
        if(userEntity == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Opinion newOpinionEntity = request.toModel(userEntity);
        manager.persist(newOpinionEntity);

        return ResponseEntity.created(
                builder
                        .path(path.concat("/{id}"))
                        .buildAndExpand(newOpinionEntity.getId())
                        .toUri()
        ).build();
    }
}

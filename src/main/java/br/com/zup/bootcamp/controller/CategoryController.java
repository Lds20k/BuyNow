package br.com.zup.bootcamp.controller;

import br.com.zup.bootcamp.controller.model.request.CategoryCreationRequest;
import br.com.zup.bootcamp.controller.validator.DuplicateCategoryEntryValidator;
import br.com.zup.bootcamp.controller.validator.PredecessorCategoryExistValidator;
import br.com.zup.bootcamp.domain.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// Intrinsic charge = 4
@RestController
@RequestMapping(CategoryController.path)
public class CategoryController {

    public static final String path = "/category";

    @PersistenceContext
    EntityManager manager;

    @Autowired
    DuplicateCategoryEntryValidator duplicateCategoryEntryValidator;

    @Autowired
    PredecessorCategoryExistValidator predecessorCategoryExistValidator;

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(duplicateCategoryEntryValidator, predecessorCategoryExistValidator);
    }

    /**
     * @param request Body da request com o nome e, se possuir, a categoria antecessora
     * @param builder Um UriComponentsBuilder para criar a URI de retorno
     * @return Caso bem sucedido retorna HttpStatus 201, o URI com o local onde foi criado e um body vazia
     */
    @PostMapping
    @Transactional
    public ResponseEntity create(@Validated @RequestBody CategoryCreationRequest request, UriComponentsBuilder builder){
        Category newCategory = request.toModel();
        manager.persist(newCategory);
        return ResponseEntity.created(builder.path(path.concat("/{id}")).buildAndExpand(newCategory.getId()).toUri()).build();
    }
}

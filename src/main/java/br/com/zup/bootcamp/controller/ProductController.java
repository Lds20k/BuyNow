package br.com.zup.bootcamp.controller;

import br.com.zup.bootcamp.controller.model.request.ProductRegisterRequest;
import br.com.zup.bootcamp.controller.validator.ProductCategoryExistValidator;
import br.com.zup.bootcamp.domain.entity.Product;
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
@RequestMapping(ProductController.path)
public class ProductController {

    public static final String path = "/product";

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    ProductCategoryExistValidator productCategoryExistValidator;

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(productCategoryExistValidator);
    }

    /**
     * @param request Body da request com os dados do produto
     * @param builder Um UriComponentsBuilder para criar a URI de retorno
     * @return Caso bem sucedido retorna HttpStatus 201, o URI com o local onde foi criado e uma body vazia
     */
    @PostMapping
    @Transactional
    public ResponseEntity register(@Validated @RequestBody ProductRegisterRequest request, UriComponentsBuilder builder){
        Product newProduct = request.toModel();
        manager.persist(newProduct);
        return ResponseEntity.created(builder.path(path.concat("/{id}")).buildAndExpand(newProduct.getId()).toUri()).build();
    }
}

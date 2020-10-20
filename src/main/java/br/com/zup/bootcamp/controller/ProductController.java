package br.com.zup.bootcamp.controller;

import br.com.zup.bootcamp.controller.model.Uploader;
import br.com.zup.bootcamp.controller.model.request.AddImagesRequest;
import br.com.zup.bootcamp.controller.model.request.ProductRegisterRequest;
import br.com.zup.bootcamp.controller.model.response.ProductConsultResponse;
import br.com.zup.bootcamp.controller.validator.ProductCategoryExistValidator;
import br.com.zup.bootcamp.domain.entity.Image;
import br.com.zup.bootcamp.domain.entity.Product;
import br.com.zup.bootcamp.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

// Intrinsic charge = 9
@RestController
@RequestMapping(ProductController.path)
public class ProductController {

    public static final String path = "/product";

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    Uploader uploader;

    @InitBinder(value = path)
    public void init(WebDataBinder binder){
        binder.addValidators(new ProductCategoryExistValidator());
    }

    /**
     * @param request Body da request com os dados do produto
     * @param builder Um UriComponentsBuilder para criar a URI de retorno
     * @return Caso bem sucedido retorna HttpStatus 201, o URI com o local onde foi criado e uma body vazia
     */
    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@Validated @RequestBody ProductRegisterRequest request, UriComponentsBuilder builder, @RequestHeader("user") String userId){
        User user = manager.find(User.class, userId);
        Product newProduct = request.toModel(user);

        manager.persist(newProduct);
        return ResponseEntity.created(builder.path(path.concat("/{id}")).buildAndExpand(newProduct.getId()).toUri()).build();
    }

    /**
     * @param request Body da request com as imagens
     * @param id Id do produto que sera adicionada a imagem
     * @param userId Id do usuário
     * @return Caso bem sucedido retorna HttpStatus 200
     */
    @PostMapping("/{id}/image")
    @Transactional
    public ResponseEntity<?> addImage(@Validated AddImagesRequest request, @PathVariable("id") String id, @RequestHeader("user") String userId){
        User userEntity = manager.find(User.class, userId);

        Product productEntity = manager.find(Product.class, id);

        if(!productEntity.isUserOwner(userEntity)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Collection<Image> images = request.toModel(productEntity, uploader);
        productEntity.setImages(images);

        manager.merge(productEntity);

        return ResponseEntity.ok().build();
    }

    /**
     * @param id Id do produto que sera consultado
     * @return Caso bem sucedido retorna HttpStatus 302 e uma body com as informações do produto
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductConsultResponse> consult(@PathVariable String id){
        Product productEntity = manager.find(Product.class, id);
        if(productEntity == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ProductConsultResponse response = ProductConsultResponse.toResponse(productEntity);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
}

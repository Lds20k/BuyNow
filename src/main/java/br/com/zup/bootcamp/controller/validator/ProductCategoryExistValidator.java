package br.com.zup.bootcamp.controller.validator;

import br.com.zup.bootcamp.controller.model.request.ProductRegisterRequest;
import br.com.zup.bootcamp.domain.entity.Category;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ProductCategoryExistValidator implements Validator {

    @PersistenceContext
    private EntityManager manager;

    /**
     * @return Caso a classe seja a mesma que sera validada, retorna true
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return ProductRegisterRequest.class.isAssignableFrom(aClass);
    }

    /**
     * @apiNote Verifica se a categoria existe no banco de dados
     */
    @Override
    public void validate(Object o, Errors errors) {
        ProductRegisterRequest request = (ProductRegisterRequest) o;

        Category category = manager.find(Category.class, request.getCategory());

        if(category == null){
            errors.rejectValue("category", null, "category not exist.");
        }
    }
}

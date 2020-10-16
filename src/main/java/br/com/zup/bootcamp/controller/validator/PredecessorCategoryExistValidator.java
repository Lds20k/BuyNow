package br.com.zup.bootcamp.controller.validator;

import br.com.zup.bootcamp.controller.model.request.CategoryCreationRequest;
import br.com.zup.bootcamp.domain.entity.Category;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// Intrinsic charge = 3
@Component
public class PredecessorCategoryExistValidator implements Validator {

    @PersistenceContext
    private EntityManager manager;

    /**
     * @return Caso a classe seja a mesma que sera validada, retorna true
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return CategoryCreationRequest.class.isAssignableFrom(aClass);
    }

    /**
     * @apiNote Verifica se a categoria antecessora existe no banco de dados
     */
    @Override
    @Transactional
    public void validate(Object o, Errors errors) {
        CategoryCreationRequest request = (CategoryCreationRequest) o;
        if(request.getPredecessor() == null) return;

        String predecessorCategory = request.getPredecessor();

        Category category = manager.find(Category.class, request.getPredecessor());

        if(category == null){
            errors.rejectValue("predecessor", null, "predecessor category not exist.");
        }
    }
}

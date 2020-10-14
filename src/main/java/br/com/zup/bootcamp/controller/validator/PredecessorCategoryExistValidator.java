package br.com.zup.bootcamp.controller.validator;

import br.com.zup.bootcamp.controller.model.CategoryCreationRequest;
import br.com.zup.bootcamp.domain.entity.Category;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

// Intrinsic charge = 3
@Component
public class PredecessorCategoryExistValidator implements Validator {

    @PersistenceContext
    EntityManager manager;

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
        if(request.getPredecessorCategory() == null) return;

        String predecessorCategory = request.getPredecessorCategory();

        Query query = manager.createQuery(
                "select category.id from " + Category.class.getName() + " as category where category.id = :id"
        );
        query.setParameter("id", predecessorCategory);
        List category = query.getResultList();

        if(category.isEmpty()){
            errors.rejectValue("predecessorCategory", null, "Predecessor Category not exist.");
        }
    }
}

package br.com.zup.bootcamp.controller.validator;

import br.com.zup.bootcamp.controller.model.CategoryCreationRequest;
import br.com.zup.bootcamp.domain.entity.Category;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

// Intrinsic charge = 2
@Component
public class DuplicateCategoryEntryValidator implements Validator {

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
     * @apiNote Verifica o nome da categoria já foi registrada no banco de dados
     */
    @Override
    public void validate(Object o, Errors errors) {
        CategoryCreationRequest request = (CategoryCreationRequest) o;

        String name = request.getName();

        Query query = manager.createQuery(
                "select category.name from " + Category.class.getName() + " as category where category.name = :name"
        );
        query.setParameter("name", name);
        List category = query.getResultList();

        if(!category.isEmpty()){
            errors.rejectValue("name", null, "Category name already registered");
        }
    }
}

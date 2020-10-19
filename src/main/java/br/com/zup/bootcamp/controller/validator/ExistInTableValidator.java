package br.com.zup.bootcamp.controller.validator;

import br.com.zup.bootcamp.controller.validator.annotation.ExistInTable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistInTableValidator implements ConstraintValidator<ExistInTable, Object> {

    @PersistenceContext
    private EntityManager manager;

    private String attribute;
    private Class<?> domain;

    @Override
    public void initialize(ExistInTable constraintAnnotation) {
        this.attribute = constraintAnnotation.attribute();
        this.domain = constraintAnnotation.domain();
    }

    /**
     * Verifica se o objeto existe no banco de dados
     * @return true caso o objeto exista, caso contrario, false
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null) return true;

        Query query = manager.createQuery(
                "select 1 from " + this.domain.getName() + " as domain where domain." + attribute + " = :value"
        );
        query.setParameter("value", value);
        List<?> result = query.getResultList();

        return !result.isEmpty();
    }
}

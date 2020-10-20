package br.com.zup.bootcamp.controller.validator.annotation;

import br.com.zup.bootcamp.controller.validator.ExistInTableValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * Valida se um objeto existe no banco de dados se o objeto de entrada não for nulo
 */
@Documented
@Constraint(validatedBy = ExistInTableValidator.class)
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ExistInTable.List.class)
public @interface ExistInTable {

    String message() default "{validation.ExistInTable.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * Classe de domínio
     */
    Class<?> domain();

    /**
     * Atributo/Coluna do banco de dados
     */
    String attribute() default "id";

    /**
     * Usado para que possa ter mais de uma anotação no mesmo objeto
     */
    @Documented
    @Target({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ExistInTable[] value();
    }
}

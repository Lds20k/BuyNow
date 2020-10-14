package br.com.zup.bootcamp.controller.validator;

import br.com.zup.bootcamp.controller.model.UserRegisterRequest;
import br.com.zup.bootcamp.domain.entity.User;
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
public class DuplicateLoginEntryValidator implements Validator {

    @PersistenceContext
    EntityManager manager;

    /**
     * @return Caso a classe seja a mesma que sera validada, retorna true
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterRequest.class.isAssignableFrom(aClass);
    }

    /**
     * @apiNote Verifica se o email já foi cadastrado no banco de dados
     */
    @Override
    @Transactional
    public void validate(Object o, Errors errors) {
        UserRegisterRequest request = (UserRegisterRequest) o;

        String login = request.getLogin();

        Query query = manager.createQuery(
                "select user.login from " + User.class.getName() + " as user where user.login = :login"
        );
        query.setParameter("login", login);
        List user = query.getResultList();

        if (!user.isEmpty()){
            errors.rejectValue("login", null, "This login already exist.");
        }
    }
}

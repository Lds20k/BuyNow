package br.com.zup.bootcamp.controller.model;

import br.com.zup.bootcamp.domain.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// Intrinsic charge = 1
public class UserRegisterRequest {

    @Email
    @NotBlank
    private String login;

    @NotBlank
    @Size(min = 6)
    private String password;

    /**
     * @param login String em formato de email
     * @param password String para senha com o tamanho mínimo de seis caracteres e em formato plain text
     **/
    public UserRegisterRequest(@Email @NotBlank String login, @NotBlank @Size(min = 6) String password){
        super();
        this.login = login;
        this.password = password;
    }

    /**
     * @apiNote Criptografa a senha
     */
    private void encode(){
        this.password = new BCryptPasswordEncoder().encode(this.password);
    }

    /**
     * @return User com os atributos convertidos da request e com a password criptografada
     * @param manager
     */
    public User toModel(EntityManager manager){
        this.encode();
        return new User(this.login, this.password, manager);
    }

    public String getLogin() {
        return this.login;
    }
}

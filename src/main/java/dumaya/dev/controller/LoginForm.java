package dumaya.dev.controller;

import java.io.Serializable;

//import com.sun.istack.internal.NotNull;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;

public class LoginForm implements Serializable{

    @Size(min = 2, max = 30,
            message = "Le nom d'utilisateur doit comporter au minimum 3 caractères et au maiximum 30")
    private String username;

    @Size(min = 3, message = "Le mot de passe doit comporter au moins 3 caractères")
	@NotNull
    private String password;

    public String getUsername() {
        return username;
    }
    

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



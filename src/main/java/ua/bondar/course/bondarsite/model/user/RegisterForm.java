package ua.bondar.course.bondarsite.model.user;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Data
public class RegisterForm {
    private String username;
    private String password;

    public UserOfShop toUser(PasswordEncoder passwordEncoder){
        UserOfShop user = new UserOfShop();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.USER));
        return user;
    }
}

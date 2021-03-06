package ua.bondar.course.bondarsite.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

public enum Role implements GrantedAuthority {
    USER,ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

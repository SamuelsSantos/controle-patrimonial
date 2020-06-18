package br.com.navita.controlepatrimonial.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 * The type User details custom.
 */
@Data
public class UserDetailsCustom implements UserDetails {

    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Instantiates a new User details custom.
     *
     * @param id       the id
     * @param username the username
     * @param email    the email
     * @param password the password
     */
    public UserDetailsCustom(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Build user details custom.
     *
     * @param user the user
     * @return the user details custom
     */
    public static UserDetailsCustom build(User user) {
        return new UserDetailsCustom(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsCustom user = (UserDetailsCustom) o;
        return Objects.equals(id, user.id);
    }
}

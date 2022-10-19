package gg.stove.domain.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import gg.stove.domain.user.entity.Authority;
import gg.stove.domain.user.entity.UserEntity;
import lombok.Getter;

@Getter
public class AuthUser implements UserDetails {
    private final UserEntity user;

    public AuthUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (user.getAuthority() == Authority.ROLE_ADMIN) {
            authorities.add(new SimpleGrantedAuthority(user.getAuthority().name()));
        }

        return authorities;
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
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
}

package us.devoxx.dev.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import us.devoxx.dev.Entity.RoleEntity;
import us.devoxx.dev.Entity.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDaitls implements UserDetails {

     UserEntity user ;
     public  MyUserDaitls (UserEntity user){
         this.user =  user;
     }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return fromRolesToSpringAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

    public List<GrantedAuthority> fromRolesToSpringAuthorities(){
         List<GrantedAuthority> authorities =  new ArrayList<>();
        for (RoleEntity role :  this.user.getRoles()) {
            authorities.add( new SimpleGrantedAuthority(role.getRolename()));
        }
        return authorities ;
    }
}

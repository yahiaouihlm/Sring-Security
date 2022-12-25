package us.devoxx.dev.Entity;


import jakarta.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false , unique = true)
    private  Integer id;
    @Column(nullable = false ,  length = 40)
    private  String name  ;
    @Column(nullable = false ,  length = 40)
    private  String fname ;
    @Column(nullable = false ,  length = 40)
    private  String email ;

    @Column(nullable = false ,  length = 400)
    private String password;

    @Column(name = "birthday")
    private LocalDate birthday  ;



    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_has_role", joinColumns = {@JoinColumn(name = "userid", nullable = false)
    },inverseJoinColumns = {@JoinColumn(name = "roleid", nullable = false)})
    List<RoleEntity> roles ;

   public  UserEntity (){}
    public UserEntity(String name, String fname, String email, String password, LocalDate birthday, List<RoleEntity> roles) {
        this.name = name;
        this.fname = fname;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.roles = roles;
    }




    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

}

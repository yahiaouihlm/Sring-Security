package us.devoxx.dev.Entity;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false , unique = false)
    private  Integer roleid;
    @Column(nullable = false ,length = 100)
    private String  rolename;
    private String  roledescription ;


    public RoleEntity(String rolename, String roledescription) {
        this.rolename = rolename;
        this.roledescription = roledescription;
    }

    public  RoleEntity (){}

    //  Les getters  and  Setters
    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoledescription() {
        return roledescription;
    }

    public void setRoledescription(String roledescription) {
        this.roledescription = roledescription;
    }
}

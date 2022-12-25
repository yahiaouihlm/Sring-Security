package us.devoxx.dev.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import us.devoxx.dev.Dao.IuserDao;
import us.devoxx.dev.Entity.RoleEntity;
import us.devoxx.dev.Entity.UserEntity;

import java.time.LocalDate;
import java.time.Year;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServices {

 @Autowired
 private IuserDao iuserDao ;
// @Autowired
 private   BCryptPasswordEncoder encoder = null  ;
    public UserEntity addUser (){
    /* String password  =  "test33";
     var  cryptedpassword = encoder.encode(password);
     UserEntity userEntity =  new UserEntity();
     LocalDate birthday = LocalDate.of(2000 , 07, 18);

     userEntity.setBirthday(birthday);
     userEntity.setEmail("yahiaouihlm@gmail.com");
     userEntity.setName("halim");
     userEntity.setFname("yahiaoui");
     userEntity.setPassword(cryptedpassword);

     List<RoleEntity> roles = new LinkedList<>();
     roles.add(new RoleEntity("Admin" , "Can do  Evrithing"));
     userEntity.setRoles(roles);

      return  iuserDao.save(userEntity);
      */
     return   null ;
 }

}

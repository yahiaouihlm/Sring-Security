package us.devoxx.dev.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import us.devoxx.dev.Dao.IuserDao;


@Component
public class AppUserService implements UserDetailsService {
     @Autowired
    IuserDao iuserDao;

    public  AppUserService  (){}
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       var userOptional = iuserDao.findByEmail(username);
        if (!userOptional.isPresent()) {
             throw   new UsernameNotFoundException("No  user has been found with this  email ");
        }

        var user =  userOptional.get();
        var result  =  new MyUserDaitls(user);
        System.out.println(result.getUsername()  +"  " + result.getAuthorities());
        return result;
    }
}

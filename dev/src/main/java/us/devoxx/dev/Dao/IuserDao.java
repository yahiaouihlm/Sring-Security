package us.devoxx.dev.Dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.devoxx.dev.Entity.UserEntity;

import java.util.Optional;

@Repository
public interface IuserDao extends  JpaRepository<UserEntity ,Integer> {
    public Optional <UserEntity> findByEmail  (String email) ;

}

package com.kirigwi.springarchive.authorisation.userManagement.repository;

import com.kirigwi.springarchive.authorisation.userManagement.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserRepository {
    @Select("SELECT * FROM users WHERE username = #{username}")
    Optional<Users> findByUsername(String username);  // Change return type to Optional<Users>

    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<Users> findByEmail(String email);  // Change return type to Optional<Users>

    @Insert("INSERT INTO users(username, password, email, roleid, createdat)" +
            " VALUES(#{username}, #{password}, #{email}, #{roleid}, GETDATE())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(Users user);
}

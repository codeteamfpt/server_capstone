package com.example.server_capstone.repository;

import com.example.server_capstone.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity,Long> {
    AccountEntity findByUserName(String userName);

    @Modifying
    @Query( value = "UPDATE account SET user_name = :userName,pass_word = :passWord," +
            "role = :role,user_image = :userImage WHERE account_id = :accountId",
            nativeQuery = true)
    void updateAccount(@Param("userName") String userName,
                       @Param("passWord") String passWord,
                       @Param("role") Long role,
                       @Param("userImage") String userImage,
                    @Param("accountId") Long accountId);

    @Query( value = "INSERT INTO account (user_name, pass_word, role , user_image)" +
            "VALUES (:userName, :passWord, :role, :userImage);",
            nativeQuery = true)

    void addAccount(  @Param("userName") String userName,
                   @Param("passWord") String passWord,
                   @Param("role") Long role,
                   @Param("userImage") String userImage);
}

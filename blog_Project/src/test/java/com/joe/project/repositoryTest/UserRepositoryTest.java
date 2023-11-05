package com.joe.project.repositoryTest;

import com.joe.project.entity.User;
import com.joe.project.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepositoryTest;

    @AfterEach
    void tearDown(){
        userRepositoryTest.deleteAll();
    }

    @Test
    void ifUserExists(){
        //Given
        User user = new User("joe", "joe@email", "123");
        userRepositoryTest.save(user);
        //When
        boolean expected = userRepositoryTest.existsById(user.getId());
        //Then
        assertThat(expected).isTrue();
    }

}

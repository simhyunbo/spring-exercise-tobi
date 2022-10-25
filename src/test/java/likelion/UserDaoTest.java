package likelion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)

class UserDaoTest {
    @Autowired
    ApplicationContext context;



    @Test
    void addANDSelect() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserFactory().awsUserDao();
        String id = "11";
        userDao.add(new User(id,"sim","1234"));
        User user = userDao.findById(id);
        Assertions.assertEquals("sim",user.getName());
    }
}
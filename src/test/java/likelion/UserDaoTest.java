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
@ContextConfiguration(classes = UserFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    @Test
    void addANDSelect() throws SQLException, ClassNotFoundException {
        //매개변수로 class+BeanName을 던져준다.
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        String id = "12";
        userDao.add(new User(id,"kim","1234"));
        User user = userDao.findById(id);
        Assertions.assertEquals("kim",user.getName());
    }
}
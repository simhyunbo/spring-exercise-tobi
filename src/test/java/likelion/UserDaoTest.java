package likelion;

import org.junit.jupiter.api.*;
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
    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        this.user1 = new User("1","sim","1234");
        this.user2 = new User("2","kim","5678");
        this.user3 = new User("3","park","qwer");
    }

    @Test
    @DisplayName("add select 테스트")
    void addANDSelect() throws SQLException, ClassNotFoundException {
        //매개변수로 class+BeanName을 던져준다.
        String id = "12";
        userDao.add(new User(id,"kim","1234"));
        User user = userDao.findById(id);
        Assertions.assertEquals("kim",user.getName());
    }

    @Test
    @DisplayName("delete count 테스트")
    void deleteAndCount() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.deleteAll();

        userDao.add(user1);
        assertEquals(1,userDao.getCount());
        userDao.add(user2);
        assertEquals(2,userDao.getCount());
        userDao.add(user3);
        assertEquals(3,userDao.getCount());
    }



}
package likelion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserFactory {
    @Bean
    public UserDao awsUserDao(){
        return new UserDao(new ConnectionMakerImpl());
    }
}

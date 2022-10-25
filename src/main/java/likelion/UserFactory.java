package likelion;

public class UserFactory {
    public UserDao awsUserDao(){
        return new UserDao(new ConnectionMakerImpl());
    }
}

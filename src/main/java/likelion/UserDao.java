package likelion;

import java.sql.*;
import java.util.Map;

public class UserDao {
    //ConnectionMaker 생성
    private ConntectionMaker conntectionMaker;

    public UserDao(){
        this.conntectionMaker = new ConnectionMakerImpl();
    }
    public UserDao(ConntectionMaker conntectionMaker){
        this.conntectionMaker = conntectionMaker;
    }


    //Connection 분리
//    private Connection getConnection() throws ClassNotFoundException, SQLException {
//        Map<String, String> env = System.getenv();
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection conn = DriverManager.getConnection(
//                env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
//        return conn;
//    }

    public void add(User user) throws SQLException, ClassNotFoundException {

            Connection conn = conntectionMaker.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());


            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

    }

    public User findById(String id) throws SQLException, ClassNotFoundException {
       
        Connection conn = conntectionMaker.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);


            ResultSet rs = pstmt.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            pstmt.close();
            conn.close();

            return user;

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        User user = userDao.findById("11");
        System.out.println(user.getName());
    }
}
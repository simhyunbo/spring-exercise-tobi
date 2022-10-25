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
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws ClassNotFoundException{
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = conntectionMaker.getConnection();
            pstmt = stmt.makeStatement(conn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(pstmt != null){
                try {
                    pstmt.close();
                }catch (SQLException e){
                }
            }
            if(conn != null){
                try{
                    conn.close();
                }catch (SQLException e){
                }
            }
        }
    }

    public void add(User user) throws SQLException, ClassNotFoundException {
            jdbcContextWithStatementStrategy(new AddStrategy(user));
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        try {
//            conn = conntectionMaker.getConnection();
//
//
//            pstmt = conn.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
//            pstmt.setString(1, user.getId());
//            pstmt.setString(2, user.getName());
//            pstmt.setString(3, user.getPassword());
//
//
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw e;
//        }finally {
//            if(pstmt != null){
//                try {
//                    pstmt.close();
//                }catch (SQLException e){
//                }
//            }
//            if(conn != null){
//                try{
//                    conn.close();;
//                }catch (SQLException e){
//                }
//            }
//        }
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

    public void deleteAll() throws SQLException, ClassNotFoundException {
        jdbcContextWithStatementStrategy(new DeleteAllStrategy());
//        Connection conn = conntectionMaker.getConnection();
//        PreparedStatement ps = conn.prepareStatement("delete from users");
//        ps.executeUpdate();
//        ps.close();
//        conn.close();
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection conn = conntectionMaker.getConnection();

        PreparedStatement ps = conn.prepareStatement("SELECT count(*) from users");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        conn.close();

        return count;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        User user = userDao.findById("11");
        System.out.println(user.getName());
    }
}
package likelion;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    //ConnectionMaker 생성
    private ConntectionMaker conntectionMaker;
    JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public UserDao(){
        this.conntectionMaker = new ConnectionMakerImpl();
    }
    public UserDao(ConntectionMaker conntectionMaker){
        this.conntectionMaker = conntectionMaker;
    }
    public UserDao(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }


    RowMapper<User> rowMapper = (rs, rowNum) ->
            new User(rs.getString("id"),
                    rs.getString("name"), rs.getString("password"));

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

    public void executeSql(String sql) throws SQLException, ClassNotFoundException {
        jdbcContextWithStatementStrategy(c->c.prepareStatement(sql));
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
            //this.jdbcTemplate.update("delete from users");
        //jdbcContextWithStatementStrategy(new DeleteAllStrategy());
        //executeSql("delete from users");
        this.jdbcContext.executeSql("delete from users");

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
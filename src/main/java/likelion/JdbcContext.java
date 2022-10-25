package likelion;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class JdbcContext {
    private DataSource dataSource;

    public JdbcContext(DataSource dataSource){
        this.dataSource = dataSource;
    }
    public void executeSql(String sql) throws SQLException {
        this.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                return conn.prepareStatement(sql);
            }
        });
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = stmt.makeStatement(conn);
            ps.executeUpdate();
        }catch (SQLException e){
            throw e;
        }finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e){
                }
            }
            if(conn != null){
                try {
                    conn.close();
                }catch (SQLException e){
                }
            }
        }
    }
}

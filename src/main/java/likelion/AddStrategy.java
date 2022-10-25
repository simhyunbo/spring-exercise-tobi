package likelion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStrategy implements StatementStrategy{
    User user;
    public AddStrategy(User user){
        this.user = user;
    }
    @Override
    public PreparedStatement makeStatement(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3,user.getPassword());
        return ps;
    }
}

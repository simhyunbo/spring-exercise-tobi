package likelion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStrategy implements StatementStrategy{
    @Override
    public PreparedStatement makeStatement(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("delete from users");
        return ps;
    }
}

package likelion;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConntectionMaker {
    public Connection getConnection() throws ClassNotFoundException, SQLException;
}

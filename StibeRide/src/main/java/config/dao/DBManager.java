package config.dao;


import config.ConfigManager;
import config.exception.RepositoryException;
import java.sql.*;

/**
 *
 * @author Marwa
 */
class DBManager {

    private Connection connection;

    private DBManager() {}

    Connection getConnection() throws RepositoryException {
        String jdbcUrl = "jdbc:sqlite:" + ConfigManager.getInstance()
                .getProperties("db.url");
        if (connection == null ) {
            try {
                connection = DriverManager.getConnection(jdbcUrl);
            } catch (SQLException ex) {
                throw new RepositoryException("Connexion impossible: " 
                        + ex.getMessage());
            }
        }
        return connection;
    }

    static DBManager getInstance() {
        return DBManagerHolder.INSTANCE;
    }

    private static class DBManagerHolder {

        private static final DBManager INSTANCE = new DBManager();
    }
}

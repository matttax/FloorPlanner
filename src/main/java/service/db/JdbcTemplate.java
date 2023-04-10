package service.db;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.springframework.expression.ParseException;

import javax.sql.PooledConnection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class JdbcTemplate {
    private final MysqlConnectionPoolDataSource connectionPool;

    private static final String URL = "jdbc:mysql://localhost:3306/floorplanner";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    public JdbcTemplate(MysqlConnectionPoolDataSource connectionPool) throws SQLException {
        this.connectionPool = connectionPool;
        this.connectionPool.setURL(URL);
        this.connectionPool.setUser(USERNAME);
        this.connectionPool.setPassword(PASSWORD);
    }

    @FunctionalInterface
    public interface SQLFunction<T, R> {
        R apply(T object) throws SQLException, ParseException, IOException;
    }

    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T object) throws SQLException, IOException;
    }

    public void connection(SQLConsumer<? super PooledConnection> consumer) throws SQLException, IOException {
        Objects.requireNonNull(consumer);
        PooledConnection conn = connectionPool.getPooledConnection(USERNAME, PASSWORD);
        consumer.accept(conn);
    }

    public <R> R connection(SQLFunction<? super PooledConnection, ? extends R> function)
            throws SQLException, ParseException, IOException {
        Objects.requireNonNull(function);
        PooledConnection conn = connectionPool.getPooledConnection(USERNAME, PASSWORD);
        return function.apply(conn);
    }

    public <R> R statement(SQLFunction<? super Statement, ? extends R> function) throws SQLException, ParseException, IOException {
        Objects.requireNonNull(function);
        return connection(conn -> {
            try (Statement stmt = conn.getConnection().createStatement()) {
                return function.apply(stmt);
            }
        });
    }

    public void statement(SQLConsumer<? super Statement> consumer) throws SQLException, IOException {
        Objects.requireNonNull(consumer);
        connection(conn -> {
            try (Statement stmt = conn.getConnection().createStatement()) {
                consumer.accept(stmt);
            }
        });
    }

    public <R> R preparedStatement(String sql, SQLFunction<? super PreparedStatement, ? extends R> function)
            throws SQLException, ParseException, IOException {
        Objects.requireNonNull(function);
        return connection(conn -> {
            try (PreparedStatement stmt = conn.getConnection().prepareStatement(sql)) {
                return function.apply(stmt);
            }
        });
    }

    public void preparedStatement(String sql, SQLConsumer<? super PreparedStatement> consumer)
            throws SQLException, IOException {
        Objects.requireNonNull(consumer);
        connection(conn -> {
            try (PreparedStatement stmt = conn.getConnection().prepareStatement(sql)) {
                consumer.accept(stmt);
            }
        });
    }
}


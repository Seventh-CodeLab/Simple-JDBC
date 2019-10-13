package no.codelab.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> {
  protected DataSource dataSource;

  public AbstractDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void insert(T object, String sql) throws SQLException {
    try (Connection connection = dataSource.getConnection()){
      try(PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
        insertObject(object, statement);
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
      }
    }
  }

  protected abstract void insertObject(T objectName, PreparedStatement statement) throws SQLException;

  public List<T> listAll(String sql) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        try (ResultSet resultSet = statement.executeQuery()) {
          List<T> result = new ArrayList<>();

          while (resultSet.next()){
            result.add(readObject(resultSet));
          }

          return result;
        }
      }
    }
  }

  protected abstract T readObject(ResultSet resultSet) throws SQLException;
}
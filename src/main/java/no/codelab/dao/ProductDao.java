package no.codelab.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
  private DataSource dataSource;

  public ProductDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void insertProduct(String productName) {
    try (Connection connection = dataSource.getConnection()){
      PreparedStatement statement = connection.prepareStatement(
              "INSERT INTO products (name) values (?)"
      );
      statement.setString(1, productName);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public void insertProducts(String ...productNames) {
    try (Connection connection = dataSource.getConnection()){
      PreparedStatement statement = connection.prepareStatement(
              "INSERT INTO products (name) values (?)"
      );
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<String> listAll() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement
              ("SELECT * FROM products"
              )) {
        try (ResultSet resultSet = statement.executeQuery()) {
          List<String> result = new ArrayList<>();

          while (resultSet.next()){
            result.add(resultSet.getString("name"));
          }

          return result;
        }
      }
    }
  }
}

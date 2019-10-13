package no.codelab.dao;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ProductDao {
  private DataSource dataSource;

  public ProductDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void insertProduct(String productName) {
    try (Connection connection = dataSource.getConnection()){
      PreparedStatement statement = connection.prepareStatement(
              "INSERT INTO colors (name) values (?)"
      );
      statement.setString(1, productName);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<String> listAll() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement
              ("SELECT * FROM colors"
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

  public static void main(String[] args) throws SQLException, IOException {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();

    Properties properties = new Properties();
    properties.load(new FileReader("SimpleJDBC.properties"));

    dataSource.setUrl("jdbc:postgresql://localhost:5432/colors");
    dataSource.setUser("colors");
    dataSource.setPassword(properties.getProperty("datasource.password"));
    ProductDao productDao = new ProductDao(dataSource);

    System.out.println(" - Type a product to insert: - ");
    String terminalInput = new Scanner(System.in).nextLine();

    productDao.insertProduct(terminalInput);

    System.out.println(productDao.listAll());
  }
}

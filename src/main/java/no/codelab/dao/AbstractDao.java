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

  public long insert(T color, String sql) throws SQLException {
    try (Connection connection = dataSource.getConnection()){
      try(PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
        insertObject(color, statement);
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getLong(1);
      }
    }
  }

  protected abstract void insertObject(T colorName, PreparedStatement statement) throws SQLException;

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

/*
  public static void main(String[] args) throws SQLException, IOException {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();

    Properties properties = new Properties();
    properties.load(new FileReader("SimpleJDBC.properties"));

    dataSource.setUrl("jdbc:postgresql://localhost:5432/colors");
    dataSource.setUser("colors");
    dataSource.setPassword(properties.getProperty("datasource.password"));
    ColorDao colorDao = new ColorDao(dataSource);

    System.out.println(" - Type a product to insert: - ");
    String terminalInput = new Scanner(System.in).nextLine();

    colorDao.insert(terminalInput, "INSERT INTO colors (name) values (?)");

    System.out.println(colorDao.listAll("SELECT * FROM colors"));
  }
*/
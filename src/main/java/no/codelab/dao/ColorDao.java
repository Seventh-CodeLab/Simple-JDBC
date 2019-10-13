package no.codelab.dao;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ColorDao extends AbstractDao<String> {

  public ColorDao(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  protected void insertObject(String colorName, PreparedStatement statement) throws SQLException {
    statement.setString(1, colorName);
  }

  @Override
  protected String readObject(ResultSet resultSet) throws SQLException {
    return resultSet.getString("name");
  }

  public void insert(String color) throws SQLException{
    insert(color, "INSERT INTO colors (name) VALUES (?)");
  }

  public List<String> listAll() throws SQLException {
    return listAll("SELECT * FROM colors");
  }
}

package no.codelab.dao;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDao extends AbstractDao<Favorite>{
  private List<Favorite> favorites = new ArrayList<>();

  public FavoriteDao(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  protected void insertObject(Favorite favorite, PreparedStatement statement) throws SQLException {
    statement.setString(1,favorite.getName());
  }

  @Override
  protected Favorite readObject(ResultSet resultSet) throws SQLException {
    Favorite favorite = new Favorite();
    favorite.setName(resultSet.getString("name"));
    return favorite;
  }

  public void insert(Favorite favorite) throws SQLException{
    insert(favorite, "INSERT INTO favorites (name) VALUES (?)");
  }

  public List<Favorite> listAll() throws SQLException {
    return listAll("SELECT * FROM favorites");
  }
}

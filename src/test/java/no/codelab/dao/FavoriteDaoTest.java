package no.codelab.dao;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class FavoriteDaoTest {

  private DataSource dataSource;

  @Test
  void shouldFindStoredFavorites() throws SQLException {
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setUrl("jdbc:h2:mem:testBase");

    dataSource.getConnection().createStatement().executeUpdate(
            "CREATE TABLE favorites (id SERIAL PRIMARY KEY, name varchar(1000) not null)"
    );

    Favorite favorite = new Favorite();
    favorite.setName("Test");
    FavoriteDao dao = new FavoriteDao(dataSource);

    dao.insert(favorite);
    assertThat(dao.listAll()).contains(favorite);
  }
}

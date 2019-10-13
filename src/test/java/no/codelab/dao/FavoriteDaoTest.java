package no.codelab.dao;

import org.flywaydb.core.Flyway;
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
    dataSource.setUrl("jdbc:h2:mem:testBase;DB_CLOSE_DELAY=-1");

    Flyway.configure().dataSource(dataSource).load().migrate();

    Favorite favorite = new Favorite();
    favorite.setName("Test");
    FavoriteDao dao = new FavoriteDao(dataSource);

    dao.insert(favorite);
    assertThat(dao.listAll()).contains(favorite);
  }
}

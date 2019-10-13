package no.codelab.dao;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ColorDaoTest {
  @Test
  void shouldRetrieveInsertedProduct() throws SQLException {
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setUrl("jdbc:h2:mem:testBase;DB_CLOSE_DELAY=-1"); //Type of Connection : DatabaseType : StorageLocation : DB Name

    Flyway.configure().dataSource(dataSource).load().migrate();

    ColorDao dao = new ColorDao(dataSource);
    String colorName = pickOne(new String[]{"White","Black","Gray","Light Gray", "Dark Gray"});
    dao.insert(colorName, "INSERT INTO colors (name) values (?)");
    assertThat(dao.listAll()).contains(colorName);
  }

  private String pickOne(String[] products) {
    return products[new Random().nextInt(products.length)];
  }

}

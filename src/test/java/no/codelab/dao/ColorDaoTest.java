package no.codelab.dao;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ColorDaoTest {
  @Test
  void shouldRetrieveInsertedProduct() throws SQLException {
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setUrl("jdbc:h2:mem:testBase"); //Type of Connection : DatabaseType : StorageLocation : DB Name
    dataSource.getConnection().createStatement().executeUpdate(
            "CREATE TABLE colors (id SERIAL PRIMARY KEY, name varchar(50) not null )"
    );
    ColorDao dao = new ColorDao(dataSource);
    String colorName = pickOne(new String[]{"White","Black","Gray","Light Gray", "Dark Gray"});
    dao.insert(colorName, "INSERT INTO colors (name) values (?)");
    assertThat(dao.listAll("SELECT * FROM colors"))
            .contains(colorName);
  }

  private String pickOne(String[] products) {
    return products[new Random().nextInt(products.length)];
  }

}

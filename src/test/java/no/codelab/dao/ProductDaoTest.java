package no.codelab.dao;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {
  @Test
  void shouldRetrieveInsertedProduct() throws SQLException {
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setUrl("jdbc:h2:mem:testBase"); //Type of Connection : DatabaseType : StorageLocation : DB Name
    dataSource.getConnection().createStatement().executeUpdate(
            "CREATE TABLE colors (name varchar(50))"
    );
    ProductDao dao = new ProductDao(dataSource);
    String productName = pickOne(new String[]{"White","Black","Gray","Light Gray", "Dark Gray"});
    dao.insertProduct(productName);
    assertThat(dao.listAll())
            .contains(productName);
  }

  private String pickOne(String[] products) {
    return products[new Random().nextInt(products.length)];
  }

}

package no.codelab.dao;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {
  @Test
  void shouldRetrieveInsertedProduct(){
    ProductDao dao = new ProductDao();
    dao.insertProduct("White");
    assertThat(dao.listAll())
            .contains(pickOne(new String[]{"White","Black","Gray","Light Gray","Dark Gray"}));
  }

  private String pickOne(String[] products) {
    return products[new Random().nextInt(products.length)];
  }

}

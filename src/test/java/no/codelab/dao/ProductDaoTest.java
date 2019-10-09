package no.codelab.dao;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {
  @Test
  void shouldRetrieveInsertedProduct(){
    ProductDao dao = new ProductDao();
    dao.insertProduct("White");
    assertThat(dao.listAll()).contains("White");
  }

}

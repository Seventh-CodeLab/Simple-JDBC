package no.codelab.dao;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
  private List<String> products = new ArrayList<>();

  public void insertProduct(String product) {
    this.products.add(product);
  }

  public List<String> listAll() {
    return products;
  }
}

package no.codelab.dao;

import java.util.Objects;

public class Favorite {
  private String name;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Favorite{" +
            "name='" + name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Favorite favorite = (Favorite) o;
    return Objects.equals(name, favorite.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}

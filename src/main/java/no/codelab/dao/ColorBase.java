package no.codelab.dao;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Properties;

public class ColorBase {

  private PGSimpleDataSource dataSource;
  private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

  public ColorBase() throws IOException{
    Properties properties = new Properties();
    properties.load(new FileReader("SimpleJDBC.properties"));

    dataSource = new PGSimpleDataSource();

    dataSource.setURL("jdbc:postgresql://localhost:5432/colors");
    dataSource.setUser("colors");
    dataSource.setPassword(properties.getProperty("datasource.password"));

    Flyway.configure().dataSource(dataSource).load().migrate();
  }

  public static void main(String[] args) throws SQLException, IOException {
    new ColorBase().run();
  }


  private void run() throws SQLException, IOException {
    System.out.println("== Choose your action: Create a [color] | Set a [favorite]");
    String action = input.readLine();

    switch (action){
      case "color":
        insertColor();
        break;
      case "favorite":
        insertFavorite();
        break;
      default:
        System.out.println("Process has been cancelled");
    }
    dataSource = new PGSimpleDataSource();

  }

  private void insertFavorite() throws IOException, SQLException {
    System.out.println(" - Type a color to set as your favorite - ");
    Favorite inputFavorite = new Favorite();
    String favoriteName = input.readLine();
    inputFavorite.setName(favoriteName);

    FavoriteDao favoriteDao = new FavoriteDao(dataSource);
    favoriteDao.insert(inputFavorite);


  }

  private void insertColor() throws IOException, SQLException {
    System.out.println(" - Type a color to insert: - ");
    String inputColor = input.readLine();

    ColorDao colorDao = new ColorDao(dataSource);
    colorDao.insert(inputColor, "INSERT INTO colors (name) values (?)");

    System.out.println("Current Colors: " + colorDao.listAll());
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.vertx.blog.first;

/**
 *
 * @author Pratyush
 */
import com.jayway.restassured.RestAssured;
import static io.netty.util.internal.SystemPropertyUtil.get;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyRestIT {

  @BeforeClass
  public static void configureRestAssured() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = Integer.getInteger("http.port", 8080);
  }

  @AfterClass
  public static void unconfigureRestAssured() {
    RestAssured.reset();
  }
  
  @Test
public void checkThatWeCanRetrieveIndividualProduct() {
  // Get the list of bottles, ensure it's a success and extract the first id.
  final int id = get("/api/whiskies").then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath().getInt("find { it.name=='Bowmore 15 Years Laimrig' }.id");
  // Now get the individual resource and check the content
  get("/api/whiskies/" + id).then()
      .assertThat()
      .statusCode(200)
      .body("name", equalTo("Bowmore 15 Years Laimrig"))
      .body("origin", equalTo("Scotland, Islay"))
      .body("id", equalTo(id));
}
}
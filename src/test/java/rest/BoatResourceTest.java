package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import dtos.HarbourDTO;
import dtos.OwnerDTO;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class BoatResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    BoatDTO bdto1;
    HarbourDTO hdto1;
    Owner o1, o2;
    CityInfo c1, c2;
    Address a1, a2;
    Harbour h1;
    Boat b1, b2;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        o1 = new Owner();
        o2 = new Owner();
        c1 = new CityInfo(2750, "Ballerup");
        c2 = new CityInfo(2800, "Lyngby");
        a1 = new Address("sankt jacobsvej", c1);
        a2 = new Address("nørgardsvej", c2);
        h1 = new Harbour();
        b1 = new Boat();
        b2 = new Boat();
        o1.setOwnerName("Oscar");
        o1.setOwnerPhone(12345678);
        o1.setAddress(a1);
        o2.setOwnerName("Mark");
        o2.setOwnerPhone(23456789);
        o2.setAddress(a2);
        b1.setBoatBrand("saab");
        b1.setBoatName("test123");
        b1.setBoatMake("test");
        b1.setBoatImage("test");
        b1.setHarbourHarbour(h1);
        b1.addOwner(o1);
        h1.setHarbourName("test harbour");
        h1.setHarbourCapacity(2);
        h1.setAddressAddress(a1);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
            em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
            em.createNamedQuery("Harbour.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.persist(a1);
            em.persist(a2);
            em.persist(o1);
            em.persist(o2);
            em.persist(h1);
            em.persist(b1);
            em.getTransaction().commit();
        } finally {
            hdto1 = new HarbourDTO(h1);
            bdto1 = new BoatDTO(b1);
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/boats").then().statusCode(200);
    }

    @Test
    public void testLogRequest() {
        System.out.println("Testing logging request details");
        given().log().all()
                .when().get("/boats")
                .then().statusCode(200);
    }

    @Test
    public void testLogResponse() {
        System.out.println("Testing logging response details");
        given()
                .when().get("/boats")
                .then().log().body().statusCode(200);
    }

    //    @Test
//    public void testPrintResponse() {
//        Response response = given().when().get("/owners/" + odto1.getOwnerName());
//        ResponseBody body = response.getBody();
//        System.out.println(body.prettyPrint());
//
//        response
//                .then()
//                .assertThat()
//                .body("ownerName", equalTo("Oscar"));
//    }
    @Test
    void allBoats() {
        List<BoatDTO> harbourDTOList;
        harbourDTOList = given()
                .contentType("application/json")
                .when()
                .get("/boats")
                .then()
                .extract().body().jsonPath().getList("", BoatDTO.class);
        assertThat(harbourDTOList, containsInAnyOrder(bdto1));
    }

    @Test
    void getBoatsByHarbour() {
        //todo: why is it o2 that is the owner when it is suposed to be o1????
        //fixed by editing query to use inner join
        List<OwnerDTO> ownerDTOList;
        ownerDTOList = given().contentType("application/json")
                .when().get("/boats/" + b1.getId())
                .then().extract().body().jsonPath().getList("", OwnerDTO.class);

        assertThat(ownerDTOList, containsInAnyOrder(new OwnerDTO(o1)));
    }

    @Test
    void creatBoat() {
        Boat boat = b1;
        boat.setId(null);
        BoatDTO bdto = new BoatDTO(boat);
        String requestBody = GSON.toJson(bdto);
        System.out.println(requestBody);

//        test:
//        String requestBody="{\n" +
//                "  \"boatBrand\": \"saab\",\n" +
//                "  \"boatMake\": \"test\",\n" +
//                "  \"boatName\": \"test123\",\n" +
//                "  \"boatImage\": \"test\",\n" +
//                "  \"harbour\": {\n" +
//                "    \"id\": 1,\n" +
//                "    \"harbourName\": \"test harbour\",\n" +
//                "    \"harbourCapacity\": 2,\n" +
//                "    \"addressAddress\": {\n" +
//                "      \"id\": 1,\n" +
//                "      \"streetAddress\": \"sankt jacobsvej\",\n" +
//                "      \"cityInfo\": {\n" +
//                "        \"zipCode\": 2750,\n" +
//                "        \"cityName\": \"Ballerup\"\n" +
//                "      }\n" +
//                "    }\n" +
//                "  },\n" +
//                "  \"owners\": [\n" +
//                "    {\n" +
//                "      \"id\": 1,\n" +
//                "      \"ownerName\": \"Oscar\",\n" +
//                "      \"ownerPhone\": 12345678,\n" +
//                "      \"address\": {\n" +
//                "        \"id\": 1,\n" +
//                "        \"streetAddress\": \"sankt jacobsvej\",\n" +
//                "        \"cityInfo\": {\n" +
//                "          \"zipCode\": 2750,\n" +
//                "          \"cityName\": \"Ballerup\"\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";

        given().header("Content-type", ContentType.JSON)
                .and().body(requestBody).when().post("/boats")
                .then().assertThat().statusCode(200)
                .body("boatName", equalTo("test123"));
    }

    @Test
    void updateBoat() {
        bdto1.setBoatName("updated");
        String requestBody = GSON.toJson(bdto1);
        System.out.println(requestBody);
        given().header("Content-type", ContentType.JSON)
                .and().body(requestBody).when().put("/boats")
                .then().assertThat().statusCode(200)
                .body("boatName", equalTo("updated"));
    }

    }
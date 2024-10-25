package edu.praktikum.sprint7;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierId;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static edu.praktikum.sprint7.generators.CourierGenerator.randomCourier;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class CourierTests {

    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createCourier() {
        Courier courier = randomCourier();
        courierClient = new CourierClient();

        Response response = courierClient.create(courier);

        assertEquals("Неверный статус код", SC_CREATED, response.statusCode());

        Response loginResponse = courierClient.login(courier);
        id = loginResponse.as(CourierId.class).getId();

        assertEquals("Неверный статус код", SC_OK, loginResponse.statusCode());
    }

    @After
    public void tearDown() {
        courierClient.delete(id);
    }
}

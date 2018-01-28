package org.lpro.boundary;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

import org.lpro.config.Config;

public class TestSandwich {

    private Client client;
    private WebTarget target;

    @Before
    public void initClient(){
        this.client = ClientBuilder.newClient();
        this.target = this.client.target(Config.getConfig() + "sandwichs");
    }

    @Test
    public void testSandwich() {
        // POST
        JsonObjectBuilder sandwich = Json.createObjectBuilder();
        JsonObject sandwichJson = sandwich
                .add("nom", "test")
                .add("description","un sandwich test")
                .add("type_pain", "mie")
                .build();
        Response sandwichResponse = this.target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(sandwichJson));
        String location = sandwichResponse.getHeaderString("Location");
        assertThat(sandwichResponse.getStatus(), is(201));

        sandwich = Json.createObjectBuilder();
        sandwichJson = sandwich
                .add("nom", "test2")
                .add("description","une sandwich2 test")
                .add("type_pain", "baguette")
                .build();
        sandwichResponse = this.target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(sandwichJson));
        String location2 = sandwichResponse.getHeaderString("Location");
        assertThat(sandwichResponse.getStatus(), is(201));

        // PUT
        sandwichJson = sandwich
                .add("nom", "test")
                .add("description","un sandwich test modifie")
                .build();
        sandwichResponse = this.client
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(sandwichJson));
        assertThat(sandwichResponse.getStatus(), is(200));

        // GET
        JsonObject jsonRecupere = this.client
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        assertTrue(jsonRecupere.getJsonObject("sandwich").containsKey("id"));
        assertTrue(jsonRecupere.getJsonObject("sandwich").containsKey("nom"));
        assertTrue(jsonRecupere.getJsonObject("sandwich").getString("description").contains("sandwich test"));
        assertTrue(jsonRecupere.getJsonObject("sandwich").getString("type_pain").contains("mie"));

        // DELETE
        Response deleteResponse = this.client
                .target(location)
                .request()
                .delete();
        assertThat(deleteResponse.getStatus(), is(204));

        deleteResponse = this.client
                .target(location2)
                .request()
                .delete();
        assertThat(deleteResponse.getStatus(), is(204));
    }
}

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

public class TestCategorie {

    private Client client;
    private WebTarget target;
    
    @Before
    public void initClient(){
        this.client = ClientBuilder.newClient();
        this.target = this.client.target(Config.getConfig() + "categories");
    }
    
    @Test
    public void testCategorie() {
        // POST
        JsonObjectBuilder categorie = Json.createObjectBuilder();
        JsonObject categorieJson = categorie
                .add("nom", "test")
                .add("description","une categorie test")
                .build();
        Response categorieResponse = this.target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(categorieJson));
        String location = categorieResponse.getHeaderString("Location");
        assertThat(categorieResponse.getStatus(), is(201));

        categorie = Json.createObjectBuilder();
        categorieJson = categorie
                .add("nom", "test2")
                .add("description","une categorie2 test")
                .build();
        categorieResponse = this.target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(categorieJson));
        String location2 = categorieResponse.getHeaderString("Location");
        assertThat(categorieResponse.getStatus(), is(201));

        // PUT
        categorieJson = categorie
                .add("nom", "test")
                .add("description","une categorie test modifie")
                .build();
        categorieResponse = this.client
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(categorieJson));
        assertThat(categorieResponse.getStatus(), is(200));

        // GET
        JsonObject jsonRecupere = this.client
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        assertTrue(jsonRecupere.getJsonObject("categorie").getJsonObject("categorie").containsKey("id"));
        assertTrue(jsonRecupere.getJsonObject("categorie").getJsonObject("categorie").containsKey("nom"));
        assertTrue(jsonRecupere.getJsonObject("categorie").getJsonObject("categorie").getString("description").contains("categorie test modifie"));
        
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

    @Test
    public void testCategorieSandwichs() {
        JsonObjectBuilder categorie = Json.createObjectBuilder();
        JsonObject categorieJson = categorie
                .add("nom", "test")
                .add("description","une categorie test")
                .build();
        Response categorieResponse = this.target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(categorieJson));
        String location = categorieResponse.getHeaderString("Location");

        // POST
        JsonObjectBuilder sandwich = Json.createObjectBuilder();
        JsonObject sandwichJson = sandwich
                .add("nom", "test sandwich")
                .add("description", "sandwich")
                .add("type_pain", "mie")
                .build();

        Response response = this.client
                .target(location + "/sandwichs")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(sandwichJson));
        assertThat(response.getStatus(), is(201));

        // GET
        JsonObject jsonRecupere = this.client
                .target(location + "/sandwichs")
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        assertTrue(jsonRecupere.getJsonArray("sandwichs").getJsonObject(0).getJsonObject("sandwich").containsKey("id"));
        assertTrue(jsonRecupere.getJsonArray("sandwichs").getJsonObject(0).getJsonObject("sandwich").containsKey("nom"));
        assertTrue(jsonRecupere.getJsonArray("sandwichs").getJsonObject(0).getJsonObject("sandwich").getString("description").contains("sandwich"));
        assertTrue(jsonRecupere.getJsonArray("sandwichs").getJsonObject(0).getJsonObject("sandwich").getString("type_pain").contains("mie"));

        // DELETE Categorie
        Response deleteResponse = this.client
                .target(location)
                .request()
                .delete();
        assertThat(deleteResponse.getStatus(), is(204));
    }
}

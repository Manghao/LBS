import org.junit.Before;
import org.junit.Test;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestAPI {

    private Client client;
    private WebTarget target;
    private String token;

    @Before
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.target = this.client.target("http://192.168.99.100:8000/lbsjee/api/authentification");
    }

    @Test
    public void testAuthentification() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        JsonObject jo = job
                .add("password", "lbs")
                .add("mail", "lbs@lbs.lbs")
                .build();

        Response authResponse = this.target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jo.toString()));

        String authorization = authResponse.getHeaderString(HttpHeaders.AUTHORIZATION);

        this.token = authorization.substring("Bearer".length()).trim();

        assertThat(authResponse.getStatus(), is(200));
    }

}

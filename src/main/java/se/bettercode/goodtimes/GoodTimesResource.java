package se.bettercode.goodtimes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.UnknownHostException;
import java.util.UUID;

import static java.net.InetAddress.getLocalHost;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class GoodTimesResource {

  @GET
  public GoodResponse endpoint() throws UnknownHostException {
    return new GoodResponse(UUID.randomUUID().toString(), "Good times!", getLocalHost().toString());
  }
}

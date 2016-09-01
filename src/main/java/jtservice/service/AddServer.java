package jtservice.service;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
/**
 * Created by shouh on 2016/8/31.
 */
public class AddServer implements IAddServer {

    @Path("/add")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String add(@QueryParam("x") int x, @QueryParam("y") int y) {
        return "" + (x + y);
    }
}
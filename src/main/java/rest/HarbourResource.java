package rest;

import businessfacades.HarbourDTOFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.HarbourFacade;
import errorhandling.API_Exception;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@Path("harbours")
public class HarbourResource {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private HarbourDTOFacade harbourDTOFacade = HarbourDTOFacade.getInstance(EMF);
    private HarbourFacade harbourFacade = HarbourFacade.getHarbourFacade(EMF);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers() throws API_Exception {
        return Response.ok().entity(GSON.toJson(harbourDTOFacade.getAllHarbours())).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }
    @GET
    @Path("/{harbourId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBoatsByHarbour(@PathParam("harbourId") Integer id) throws API_Exception {
        return Response.ok().entity(GSON.toJson(harbourDTOFacade.getAllBoatsByHarbour(id))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }
}

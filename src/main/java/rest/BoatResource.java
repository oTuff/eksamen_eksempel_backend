package rest;

import businessfacades.BoatDTOFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.BoatFacade;
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

@Path("boats")
public class BoatResource {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private BoatDTOFacade boatDTOFacade = BoatDTOFacade.getInstance(EMF);
    private BoatFacade boatFacade = BoatFacade.getBoatFacade(EMF);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllBoats() throws API_Exception {
        return Response.ok().entity(GSON.toJson(boatDTOFacade.getAllBoats())).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }
    @GET
    @Path("/{boatId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOwnersByBoat(@PathParam("boatId") Integer id) throws API_Exception {
        return Response.ok().entity(GSON.toJson(boatDTOFacade.getAllOwnersByBoat(id))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }
}

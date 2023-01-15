package rest;

import businessfacades.OwnerDTOFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.OwnerFacade;
import errorhandling.API_Exception;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@Path("owners")
public class OwnerResource {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private OwnerDTOFacade ownerDTOFacade = OwnerDTOFacade.getInstance(EMF);
    private OwnerFacade ownerFacade = OwnerFacade.getOwnerFacade(EMF);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers() throws API_Exception {
        return Response.ok().entity(GSON.toJson(ownerDTOFacade.getAllOwners())).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }
}

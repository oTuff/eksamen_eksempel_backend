package businessfacades;

import datafacades.BoatFacade;
import datafacades.HarbourFacade;
import dtos.BoatDTO;
import dtos.OwnerDTO;
import errorhandling.API_Exception;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class BoatDTOFacade {

    private static BoatDTOFacade instance;
    private static BoatFacade boatFacade;

    private BoatDTOFacade() {}

    public static BoatDTOFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            boatFacade = BoatFacade.getBoatFacade(_emf);
            instance = new BoatDTOFacade();
        }
        return instance;
    }

    public List<BoatDTO> getAllBoats() throws API_Exception {
        return BoatDTO.getBoatDTOs(boatFacade.getAllBoats());
    }

    public List<OwnerDTO> getAllOwnersByBoat(Integer id) throws API_Exception {
        return OwnerDTO.getOwnerDTOs(boatFacade.getAllOwnersByBoat(id));
    }

}

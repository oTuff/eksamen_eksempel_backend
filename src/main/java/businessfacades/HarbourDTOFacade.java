package businessfacades;

import datafacades.HarbourFacade;
import dtos.BoatDTO;
import dtos.HarbourDTO;
import errorhandling.API_Exception;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class HarbourDTOFacade {

    private static HarbourDTOFacade instance;
    private static HarbourFacade harbourFacade;

    private HarbourDTOFacade() {}

    public static HarbourDTOFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            harbourFacade = HarbourFacade.getHarbourFacade(_emf);
            instance = new HarbourDTOFacade();
        }
        return instance;
    }

    public List<HarbourDTO> getAllHarbours() throws API_Exception {
        return HarbourDTO.getHarbourDTOs(harbourFacade.getAllHarbours());
    }
    public List<BoatDTO> getAllBoatsByHarbour(Integer id) throws API_Exception {
        return BoatDTO.getBoatDTOs(harbourFacade.getAllBoatsByHarbour(id));
    }

}

package datafacades;

import entities.Boat;
import entities.Harbour;
import errorhandling.API_Exception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class HarbourFacade {
    private static EntityManagerFactory emf;
    private static HarbourFacade instance;

    private HarbourFacade() {
    }

    public static HarbourFacade getHarbourFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HarbourFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Harbour> getAllHarbours() throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Harbour> query = em.createQuery("SELECT h FROM Harbour h", Harbour.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any harbours in the system", 404, e);
        }
    }

    public List<Boat> getAllBoatsByHarbour(Integer harbourId) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b WHERE b.harbourHarbour.id=:harbourId", Boat.class);
            query.setParameter("harbourId", harbourId);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any boats in the harbour", 404, e);
        }
    }

}

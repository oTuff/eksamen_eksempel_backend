package datafacades;

import entities.Boat;
import entities.Harbour;
import entities.Owner;
import errorhandling.API_Exception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class BoatFacade {
    private static EntityManagerFactory emf;
    private static BoatFacade instance;

    private BoatFacade() {
    }

    public static BoatFacade getBoatFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BoatFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Boat> getAllBoats() throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b", Boat.class);
            return query.getResultList();
        }catch (Exception e){
            throw new API_Exception("cant find any boats in the system",404,e);
        }
    }

    public List<Owner> getAllOwnersByBoat(Integer boatId) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o JOIN Boat b WHERE b.id=:boatId", Owner.class);
            query.setParameter("boatId",boatId);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any owners of that boat in the system", 404, e);
        }
    }
}

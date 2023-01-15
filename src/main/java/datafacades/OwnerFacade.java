package datafacades;

import entities.Owner;
import errorhandling.API_Exception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class OwnerFacade {
    private static EntityManagerFactory emf;
    private static OwnerFacade instance;

    private OwnerFacade() {
    }

    public static OwnerFacade getOwnerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new OwnerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Owner createOwner(Owner owner) throws API_Exception {
        return null;

    }

    public List<Owner> getAllOwners() throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o", Owner.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any owners in the system", 404, e);
        }
    }
}
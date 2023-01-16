package datafacades;

import com.google.gson.stream.MalformedJsonException;
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
        } catch (Exception e) {
            throw new API_Exception("cant find any boats in the system", 404, e);
        }
    }

    public List<Owner> getAllOwnersByBoat(Integer boatId) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o INNER JOIN o.boats b WHERE b.id=:boatId", Owner.class);
            query.setParameter("boatId", boatId);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any owners of that boat in the system", 404, e);
        }
    }

    public Boat createBoat(Boat boat) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            for (Owner o : boat.getOwners()) {
                em.find(Owner.class, o.getId());
            }
            em.find(Harbour.class, boat.getHarbourHarbour().getId());
            em.persist(boat);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new API_Exception("error creating boat");
        } finally {
            em.close();
        }
        return boat;
    }

    public Boat updateBoat(Boat boat) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            for (Owner o : boat.getOwners()) {
                em.find(Owner.class, o.getId());
            }
            em.find(Harbour.class, boat.getHarbourHarbour().getId());
            em.merge(boat);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new API_Exception("error updating boat");
        } finally {
            em.close();
        }
        return boat;
    }

    public Boat deleteBoat(Integer boatId) throws API_Exception {
        EntityManager em = getEntityManager();
        Boat boat = em.find(Boat.class, boatId);

        try {
            em.getTransaction().begin();
            em.remove(boat);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (boat == null) {
                throw new API_Exception("no boat with that id");
            }
        } finally {
            em.close();
        }
        return boat;
    }
}

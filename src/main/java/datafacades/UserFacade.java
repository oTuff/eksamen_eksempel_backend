package datafacades;

import entities.Role;
import entities.User;

import javax.persistence.*;

import errorhandling.API_Exception;
import security.errorhandling.AuthenticationException;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName= :username", User.class);
            query.setParameter("username", username);
            user = query.getSingleResult();

            if (/* user == null ||*/ !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid username or password");
            }
        } catch (NoResultException e) {
            throw new AuthenticationException("Invalid username or password");
        } finally {
            em.close();
        }
        return user;
    }

    public User createUser(User user) throws API_Exception {
        EntityManager em = getEntityManager();
        user.addRole(new Role("user")); //Can also be done in frontend
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new API_Exception("There's already a user with the username: " + user.getUserName() + " in the system!");
        } finally {
            em.close();
        }
        return user;
    }

    public User updateUser(User user) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            user.addRole(new Role("user")); //Could make a new method where you can assign new roles, otherwise it's always "user"
            User u = em.merge(user);
            em.getTransaction().commit();
            return u;
        } catch (Exception e) {
            throw new API_Exception("Can't find a user with the username: " + user.getUserName(), 400, e);
        } finally {
            em.close();
        }
    }

    public User getUserByUserName(String userName) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            User u = em.find(User.class, userName);
            return u;
        } catch (Exception e) {
            throw new API_Exception("Can't find a user with the username: " + userName, 404, e);
        }

    }

    public List<User> getAllUsers() throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any users in the system", 404, e);
        }
    }

    public User deleteUser(String userName) throws API_Exception {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, userName);

        try {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (user == null) {
                throw new API_Exception("Can't find a user with the username: " + userName, 400, e);
            }
        } finally {
            em.close();
        }
        return user;
    }
}



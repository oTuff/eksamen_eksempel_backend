package datafacades;

import entities.*;
import errorhandling.API_Exception;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BoatFacadeTest {

    private static EntityManagerFactory emf;
    private static BoatFacade facade;

    Owner o1, o2;
    CityInfo c1,c2;
    Address a1,a2;
    Harbour h1;
    Boat b1, b2;

    public BoatFacadeTest() throws ParseException {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = BoatFacade.getBoatFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        o1 = new Owner();
        o2 = new Owner();
        c1 = new CityInfo(2750,"Ballerup");
        c2 = new CityInfo(2800,"Lyngby");
        a1 = new Address("sankt jacobsvej",c1);
        a2 = new Address("nørgardsvej",c2);
        h1 = new Harbour();
        b1 = new Boat();
        b2 = new Boat();
        o1.setOwnerName("Oscar");
        o1.setOwnerPhone(12345678);
        o1.setAddress(a1);
        o2.setOwnerName("Mark");
        o2.setOwnerPhone(23456789);
        o2.setAddress(a2);
        b1.setBoatBrand("saab");
        b1.setBoatName("test123");
        b1.setBoatMake("test");
        b1.setBoatImage("test");
        b1.setHarbourHarbour(h1);
        b1.addOwner(o1);
        h1.setHarbourName("test harbour");
        h1.setHarbourCapacity(2);
        h1.setAddressAddress(a1);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
            em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
            em.createNamedQuery("Harbour.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.persist(a1);
            em.persist(a2);
            em.persist(o1);
            em.persist(o2);
            em.persist(h1);
            em.persist(b1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getAllBoats() throws API_Exception {
        List<Boat> actual = facade.getAllBoats();
        int expected = 1;
        assertEquals(expected, actual.size());
    }
    @Test
    public void getAllOwnersByBoat() throws API_Exception {
        List<Owner> actual = facade.getAllOwnersByBoat(b1.getId());
        int expected = 1;
        assertEquals(expected, actual.size());
    }

    @Test
    public void createBoat() throws API_Exception {
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(o1);
        Boat boat = new Boat("saab","test", "dengodebad","img",h1, ownerSet);
        System.out.println(facade.createBoat(boat));
        assertNotNull(boat.getId());
        int actualSize = facade.getAllBoats().size();
        assertEquals(2,actualSize);
    }

    @Test
    public void updateBoat() throws API_Exception{
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(o2);
        Boat boat = b1;
        b1.setOwners(ownerSet);
        facade.updateBoat(boat);
        assertNotNull(boat.getId());
        int actualSize = facade.getAllBoats().size();
        assertEquals(1,actualSize);
        assertEquals(o2,b1.getOwners().toArray()[0]);
    }
    @Test
    public void updateBoatKeepOwner() throws API_Exception{
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(o2);
        ownerSet.add(o1);
        b1.setOwners(ownerSet);
        facade.updateBoat(b1);
        assertNotNull(b1.getId());
        int actualSize = facade.getAllBoats().size();
        assertEquals(1,actualSize);
        assertEquals(2,b1.getOwners().size());
    }
}
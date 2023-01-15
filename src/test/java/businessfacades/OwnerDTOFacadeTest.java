package businessfacades;

import dtos.OwnerDTO;
import entities.Address;
import entities.CityInfo;
import entities.Owner;
import errorhandling.API_Exception;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OwnerDTOFacadeTest {
    private static EntityManagerFactory emf;
    private static OwnerDTOFacade facade;

    OwnerDTO odto1, odto2;
    //todo: change to dtos
    CityInfo c1,c2;
    Address a1,a2;

    public OwnerDTOFacadeTest() throws ParseException {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = OwnerDTOFacade.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Owner o1 = new Owner();
        Owner o2 = new Owner();
        c1 = new CityInfo(2750,"Ballerup");
        c2 = new CityInfo(2800,"Lyngby");
        a1 = new Address("sankt jacobsvej",c1);
        a2 = new Address("nørgardsvej",c2);
        o1.setOwnerName("Oscar");
        o1.setOwnerPhone(12345678);
        o1.setAddress(a1);
        o2.setOwnerName("Mark");
        o2.setOwnerPhone(23456789);
        o2.setAddress(a2);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
            em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
            em.createNamedQuery("Harbour.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.persist(a1);
            em.persist(a2);
            em.persist(o1);
            em.persist(o2);
            em.getTransaction().commit();
        } finally {
            odto1 = new OwnerDTO(o1);
            odto2 = new OwnerDTO(o2);
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void findAllOwnerDTOs() throws API_Exception {
        List<OwnerDTO> actual = facade.getAllOwners();
        int expected = 2;
        assertEquals(expected, actual.size());
    }

}
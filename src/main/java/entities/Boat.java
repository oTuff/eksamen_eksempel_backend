package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "Boat.deleteAllRows", query = "DELETE from Boat")
@Table(name = "boat")
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boat_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "boat_brand", length = 45)
    private String boatBrand;

    @Size(max = 45)
    @Column(name = "boat_make", length = 45)
    private String boatMake;

    @Size(max = 45)
    @Column(name = "boat_name", length = 45)
    private String boatName;

    @Size(max = 45)
    @Column(name = "boat_image", length = 45)
    private String boatImage;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "harbour_harbour_id", nullable = false)
    private Harbour harbourHarbour;

    @ManyToMany
    @JoinTable(name = "boat_registation",
            joinColumns = @JoinColumn(name = "boat_boat_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_owner_id"))
    private Set<Owner> owners = new LinkedHashSet<>();

    public Boat() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoatBrand() {
        return boatBrand;
    }

    public void setBoatBrand(String boatBrand) {
        this.boatBrand = boatBrand;
    }

    public String getBoatMake() {
        return boatMake;
    }

    public void setBoatMake(String boatMake) {
        this.boatMake = boatMake;
    }

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public String getBoatImage() {
        return boatImage;
    }

    public void setBoatImage(String boatImage) {
        this.boatImage = boatImage;
    }

    public Harbour getHarbourHarbour() {
        return harbourHarbour;
    }

    public void setHarbourHarbour(Harbour harbourHarbour) {
        this.harbourHarbour = harbourHarbour;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }

    public void addOwner(Owner owner){
        this.owners.add(owner);
    }

}
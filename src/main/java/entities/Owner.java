package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "Owner.deleteAllRows", query = "DELETE from Owner")
@Table(name = "owner")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "owner_name", nullable = false, length = 45)
    private String ownerName;

    @NotNull
    @Column(name = "owner_phone", nullable = false)
    private Integer ownerPhone;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToMany
    @JoinTable(name = "boat_registation",
            joinColumns = @JoinColumn(name = "owner_owner_id"),
            inverseJoinColumns = @JoinColumn(name = "boat_boat_id"))
    private Set<Boat> boats = new LinkedHashSet<>();

    public Owner() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(Integer ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Set<Boat> boats) {
        this.boats = boats;
    }

}
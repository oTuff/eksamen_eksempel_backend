package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "Harbour.deleteAllRows", query = "DELETE from Harbour")
@Table(name = "harbour")
public class Harbour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "harbour_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "harbour_name", length = 45)
    private String harbourName;

    @Column(name = "harbour_capacity")
    private Integer harbourCapacity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_address_id", nullable = false)
    private Address addressAddress;

    @OneToMany(mappedBy = "harbourHarbour")
    private Set<Boat> boats = new LinkedHashSet<>();

    public Harbour() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHarbourName() {
        return harbourName;
    }

    public void setHarbourName(String harbourName) {
        this.harbourName = harbourName;
    }

    public Integer getHarbourCapacity() {
        return harbourCapacity;
    }

    public void setHarbourCapacity(Integer harbourCapacity) {
        this.harbourCapacity = harbourCapacity;
    }

    public Address getAddressAddress() {
        return addressAddress;
    }

    public void setAddressAddress(Address addressAddress) {
        this.addressAddress = addressAddress;
    }

    public Set<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Set<Boat> boats) {
        this.boats = boats;
    }

    public void addBoat(Boat boat){
        this.boats.add(boat);
    }

    @Override
    public String toString() {
        return "Harbour{" +
                "id=" + id +
                ", harbourName='" + harbourName + '\'' +
                ", harbourCapacity=" + harbourCapacity +
                ", addressAddress=" + addressAddress +
                ", boats=" + boats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Harbour)) return false;
        Harbour harbour = (Harbour) o;
        return getId().equals(harbour.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
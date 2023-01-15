package dtos;

import entities.Boat;
import entities.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OwnerDTO {
    private Integer id;
    private String ownerName;
    private Integer ownerPhone;
    private AddressDTO address;
    private List<BoatInnerDTO> boats = new ArrayList<>();

    public OwnerDTO(Owner o) {
        if (o.getId() != null) {
            this.id = o.getId();
        }
        this.ownerName = o.getOwnerName();
        this.ownerPhone = o.getOwnerPhone();
        this.address = new AddressDTO(o.getAddress());
        for (Boat b : o.getBoats()) {
            this.boats.add(new BoatInnerDTO(b));
        }
    }

    public static List<OwnerDTO> getOwnerDTOs(List<Owner> allOwners) {
        List<OwnerDTO> ownerDTOList = new ArrayList<>();
        for (Owner o : allOwners) {
            ownerDTOList.add(new OwnerDTO(o));
        }
        return ownerDTOList;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

//    public List<BoatDTO> getBoats() {
//        return boats;
//    }
//
//    public void setBoats(List<BoatDTO> boats) {
//        this.boats = boats;
//    }

    @Override
    public String toString() {
        return "OwnerDTO{" +
                "id=" + id +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone=" + ownerPhone +
                ", address=" + address +
                ", boats=" + boats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnerDTO)) return false;
        OwnerDTO ownerDTO = (OwnerDTO) o;
        return getId().equals(ownerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    class BoatInnerDTO {
        private Integer id;
        private String boatBrand;
        private String boatMake;
        private String boatName;
        private String boatImage;
        private HarbourDTO harbour;
        //should make harbour inner dto, so it doesnt display boats twice

        public BoatInnerDTO(Boat b) {
            this.id = b.getId();
            this.boatBrand = b.getBoatBrand();
            this.boatMake = b.getBoatMake();
            this.boatName = b.getBoatName();
            this.boatImage = b.getBoatImage();
            this.harbour = new HarbourDTO(b.getHarbourHarbour());
        }

        public List<BoatDTO> getBoatDTOs(List<Boat> allBoatsByHarbour) {
            List<BoatDTO> boatDTOList = new ArrayList<>();
            for (Boat b : allBoatsByHarbour) {
                boatDTOList.add(new BoatDTO(b));
            }
            return boatDTOList;

        }
    }
}

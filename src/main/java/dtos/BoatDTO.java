package dtos;

import entities.Boat;
import entities.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoatDTO {

    private Integer id;
    private String boatBrand;

    private String boatMake;

    private String boatName;
    private String boatImage;
    private HarbourDTO harbourHarbour;
    private List<OwnerInnerDTO> owners = new ArrayList<OwnerInnerDTO>();

    public BoatDTO(Boat b) {
        this.id = b.getId();
        this.boatBrand = b.getBoatBrand();
        this.boatMake = b.getBoatMake();
        this.boatName = b.getBoatName();
        this.boatImage = b.getBoatImage();
//        this.harbourHarbour = new HarbourDTO(b.getHarbourHarbour());
        for (Owner o : b.getOwners()) {
            this.owners.add(new OwnerInnerDTO(o));
        }
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

    public HarbourDTO getHarbourHarbour() {
        return harbourHarbour;
    }

    public void setHarbourHarbour(HarbourDTO harbourHarbour) {
        this.harbourHarbour = harbourHarbour;
    }

    public List<OwnerInnerDTO> getOwners() {
        return owners;
    }

    public void setOwners(List<OwnerInnerDTO> owners) {
        this.owners = owners;
    }

    public static List<BoatDTO> getBoatDTOs(List<Boat> allBoatsByHarbour) {
        List<BoatDTO> boatDTOList = new ArrayList<>();
        for (Boat b : allBoatsByHarbour) {
            boatDTOList.add(new BoatDTO(b));
        }
        return boatDTOList;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoatDTO)) return false;
        BoatDTO boatDTO = (BoatDTO) o;
        return id.equals(boatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    class OwnerInnerDTO {
        private Integer id;
        private String ownerName;
        private Integer ownerPhone;
        private AddressDTO address;
        //private List<BoatDTO> boats = new ArrayList<>();

        public OwnerInnerDTO(Owner o) {
            if (o.getId() != null) {
                this.id = o.getId();
            }
            this.ownerName = o.getOwnerName();
            this.ownerPhone = o.getOwnerPhone();
            //this.address = new AddressDTO(o.getAddress());
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

//        public List<BoatDTO> getBoats() {
//            return boats;
//        }
//
//        public void setBoats(List<BoatDTO> boats) {
//            this.boats = boats;
//        }
    }
}

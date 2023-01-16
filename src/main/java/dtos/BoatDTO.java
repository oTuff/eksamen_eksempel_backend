package dtos;

import entities.Boat;
import entities.Harbour;
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
    private HarbourInnerDTO harbour;
    private List<OwnerInnerDTO> owners = new ArrayList<OwnerInnerDTO>();

    public BoatDTO(Boat b) {
        this.id = b.getId();
        this.boatBrand = b.getBoatBrand();
        this.boatMake = b.getBoatMake();
        this.boatName = b.getBoatName();
        this.boatImage = b.getBoatImage();
        this.harbour = new HarbourInnerDTO(b.getHarbourHarbour());
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

    public HarbourInnerDTO getHarbour() {
        return harbour;
    }

    public void setHarbour(HarbourInnerDTO harbour) {
        this.harbour = harbour;
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

    public Boat getEntity(){
        Boat boat = new Boat();
        if(this.id != null){
            boat.setId(this.getId());
        }
        boat.setBoatBrand(this.boatBrand);
        boat.setBoatMake(this.boatMake);
        boat.setBoatName(this.boatName);
        boat.setBoatImage(this.boatImage);
        boat.setHarbourHarbour(this.harbour.getEntity());
        for(OwnerInnerDTO odt : this.owners) {
            boat.getOwners().add(odt.getEntity());
        }
        return boat;
    }

    class OwnerInnerDTO {
        private Integer id;
        private String ownerName;
        private Integer ownerPhone;
        private AddressDTO address;

        public OwnerInnerDTO(Owner o) {
            if (o.getId() != null) {
                this.id = o.getId();
            }
            this.ownerName = o.getOwnerName();
            this.ownerPhone = o.getOwnerPhone();
            this.address = new AddressDTO(o.getAddress());
        }

        public Owner getEntity(){
            Owner owner = new Owner();
            if(this.id>0) {
                owner.setId(this.id);
            }
            owner.setOwnerPhone(this.ownerPhone);
            owner.setAddress(this.address.getEntity());
            owner.setOwnerName(this.ownerName);
            return owner;
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
    }
    public class HarbourInnerDTO {

        private Integer id;
        private String harbourName;
        private Integer harbourCapacity;
        private AddressDTO addressAddress;

        public HarbourInnerDTO() {
        }

        public HarbourInnerDTO(Harbour h){
            if (h.getId() != null) {
                this.id = h.getId();
            }
            this.harbourName = h.getHarbourName();
            this.harbourCapacity = h.getHarbourCapacity();
            this.addressAddress = new AddressDTO(h.getAddressAddress());
        }

        public List<HarbourInnerDTO> getHarbourDTOs(List<Harbour> allHarbours) {
            List<HarbourInnerDTO> harbourDTOList = new ArrayList<>();
            for (Harbour h : allHarbours) {
                harbourDTOList.add(new HarbourInnerDTO(h));
            }
            return harbourDTOList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof HarbourInnerDTO)) return false;
            HarbourInnerDTO that = (HarbourInnerDTO) o;
            return id.equals(that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

//    public Harbour getEntity(){
//        Harbour harbour = new Harbour();
//        if(this.id >0){
//            harbour.setId(this.id);
//        }
//        harbour.setHarbourName(this.harbourName);
//        harbour.setHarbourCapacity(this.harbourCapacity);
//        harbour.setAddressAddress(this.addressAddress.getEntity());
//        for(BoatDTO bdto :this.boats) {
//            harbour.getBoats().add(bdto.getEntity());
//        }
//        return harbour;
//    }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "HarbourDTO{" +
                    "id=" + id +
                    ", harbourName='" + harbourName + '\'' +
                    ", harbourCapacity=" + harbourCapacity +
                    ", addressAddress=" + addressAddress +
                    '}';
        }

    public Harbour getEntity(){
        Harbour harbour = new Harbour();
        if(this.id >0){
            harbour.setId(this.id);
        }
        harbour.setHarbourName(this.harbourName);
        harbour.setHarbourCapacity(this.harbourCapacity);
        harbour.setAddressAddress(this.addressAddress.getEntity());
        return harbour;
    }
    }

}

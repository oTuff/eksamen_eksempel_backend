package dtos;

import entities.Boat;
import entities.Harbour;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HarbourDTO {

    private Integer id;

    private String harbourName;
    private Integer harbourCapacity;
    private AddressDTO addressAddress;
    private List<BoatDTO> boats = new ArrayList<>();

    public HarbourDTO() {
    }

    public HarbourDTO(Harbour h){
        if (h.getId() != null) {
            this.id = h.getId();
        }
        this.harbourName = h.getHarbourName();
        this.harbourCapacity = h.getHarbourCapacity();
        this.addressAddress = new AddressDTO(h.getAddressAddress());
        for(Boat b : h.getBoats()) {
            this.boats.add(new BoatDTO(b));
        }
    }

    public static List<HarbourDTO> getHarbourDTOs(List<Harbour> allHarbours) {
            List<HarbourDTO> harbourDTOList = new ArrayList<>();
            for (Harbour h : allHarbours) {
                harbourDTOList.add(new HarbourDTO(h));
            }
            return harbourDTOList;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HarbourDTO)) return false;
        HarbourDTO that = (HarbourDTO) o;
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
                ", boats=" + boats +
                '}';
    }
}


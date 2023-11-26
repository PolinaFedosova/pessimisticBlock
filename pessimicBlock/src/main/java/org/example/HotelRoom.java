package org.example;


import javax.persistence.*;


@Entity
@Table(name = "hotel_room")
public class HotelRoom {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long Id;
    @Column(name="numberRoom")
    private String numberRoom;
    @Column(name="available")
    private Boolean available;

    public HotelRoom() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(String numberRoom) {
        this.numberRoom = numberRoom;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public HotelRoom( String numberRoom, Boolean available) {

        this.numberRoom = numberRoom;
        this.available = available;
    }

}

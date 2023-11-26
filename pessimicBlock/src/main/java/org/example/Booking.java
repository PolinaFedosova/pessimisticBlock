package org.example;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name="booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_book")
    private Long id;
    @Column(name="customer")
    private String customer;
    @Temporal(TemporalType.DATE)
    @Column(name="checkInDate")
    private Date checkInDate;
    @Temporal(TemporalType.DATE)
    @Column(name="checkOutDate")
    private Date checkOutDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public HotelRoom getHotelRoom() {
        return hotelRoom;
    }

    public void setHotelRoom(HotelRoom hotelRoom) {
        this.hotelRoom = hotelRoom;
    }

    public Booking( String customer, Date checkInDate, Date checkOutDate, HotelRoom hotelRoom) {

        this.customer = customer;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.hotelRoom = hotelRoom;
    }

    public Booking() {
    }
    @ManyToOne(optional = false)
    @JoinColumn(name="room",referencedColumnName="numberRoom")
    HotelRoom hotelRoom;
}

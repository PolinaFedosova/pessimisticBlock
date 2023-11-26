package org.example;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;



public class BookingCreate {
   public  void createBooking(SessionFactory sessionFactory) {
       Session session = null;
       try {

           session = sessionFactory.getCurrentSession();
           HotelRoom hotelRoom = new HotelRoom("985", true);
           HotelRoom hotelRoom2 = new HotelRoom("198", false);
           HotelRoom hotelRoom3 = new HotelRoom("214", true);
           HotelRoom hotelRoom4 = new HotelRoom("111", false);
           session.beginTransaction();
           session.save(hotelRoom);
           session.save(hotelRoom2);
           session.save(hotelRoom3);
           session.save(hotelRoom4);
           session.getTransaction().commit();

           session = sessionFactory.getCurrentSession();
           Booking booking = new Booking("Андрей",
                   Date.from((LocalDate.of(2023, 10, 10)).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                   Date.from((LocalDate.of(2023, 11, 10)).atStartOfDay(ZoneId.systemDefault()).toInstant()), hotelRoom);
           Booking booking2 = new Booking("Алесандр",
                   Date.from((LocalDate.of(2023, 1, 1)).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                   Date.from((LocalDate.of(2023, 1, 14)).atStartOfDay(ZoneId.systemDefault()).toInstant()), hotelRoom3);
           Booking booking3 = new Booking("Генадий",
                   Date.from((LocalDate.of(2023, 9, 3)).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                   Date.from((LocalDate.of(2023, 10, 3)).atStartOfDay(ZoneId.systemDefault()).toInstant()), hotelRoom4);
           Booking booking4 = new Booking("Полина",
                   Date.from((LocalDate.of(2023, 11, 1)).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                   Date.from((LocalDate.of(2023, 12, 1)).atStartOfDay(ZoneId.systemDefault()).toInstant()), hotelRoom2);
           Booking booking5 = new Booking("Ангелина",
                   Date.from((LocalDate.of(2023, 3, 14)).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                   Date.from((LocalDate.of(2023, 3, 30)).atStartOfDay(ZoneId.systemDefault()).toInstant()), hotelRoom);
           session.beginTransaction();
           session.save(booking);
           session.save(booking2);
           session.save(booking3);
           session.save(booking4);
           session.save(booking5);
           session.getTransaction().commit();
       }finally{
           session.close();
           sessionFactory.close();
       }
   }
    }

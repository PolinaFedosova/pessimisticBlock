package org.example;


import javax.persistence.LockModeType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;



/*
Задача: Создание системы бронирования гостиничных номеров с использованием пессимистической блокировки.

Описание задачи:
Вам необходимо разработать систему бронирования гостиничных номеров, где клиенты могут забронировать доступные номера. Чтобы избежать конфликтов при одновременном бронировании одного номера несколькими клиентами, вы решаете использовать пессимистическую блокировку.

Требования:

    Создайте класс "HotelRoom" для представления гостиничного номера со следующими полями:
        id (тип данных: Long, первичный ключ)
        roomNumber (тип данных: String, номер комнаты)
        available (тип данных: Boolean, доступность номера)

    Создайте класс "Booking" для представления бронирования со следующими полями:
        id (тип данных: Long, первичный ключ)
        room (тип данных: HotelRoom, ссылка на номер)
        customer (тип данных: String, имя клиента)
        checkInDate (тип данных: Date, дата заезда)
        checkOutDate (тип данных: Date, дата выезда)

    Создайте метод "bookRoom" в классе "Main",
     который принимает номер комнаты, имя клиента, дату заезда и дату выезда.
     Метод должен выполнять следующие действия:
        Получить объект номера комнаты по номеру из базы данных с пессимистической блокировкой.
        Проверить доступность номера на указанные даты.
        Если номер доступен, создать объект бронирования,
         сохранить его в базе данных и пометить номер как забронированный.
        Если номер недоступен, выбросить исключение с информацией о том, что бронирование невозможно.
 */
public class Main {

    public static void main(String[] args) {

        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(HotelRoom.class)
                .addAnnotatedClass(Booking.class);
        SessionFactory sessionFactory= configuration.buildSessionFactory();
        SessionFactory sessionFactory1 = configuration.buildSessionFactory();
        SessionFactory sessionFactory2 = configuration.buildSessionFactory();
        BookingCreate bookingCreate = new BookingCreate();
        bookingCreate.createBooking(sessionFactory);

            Main main = new Main();

                new Thread(() -> {
                    Session session1 = sessionFactory1.openSession();
                    try {
                        main.bookRoom(session1, "214", "Андрей", new Date(2023, 10, 10),
                                new Date(2023, 11, 10));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        session1.close();
                        sessionFactory1.close();
                    }
                }).start();

                new Thread(() -> {
                    Session session2 = sessionFactory2.openSession();
                    try {
                        main.bookRoom(session2, "214", "Ангелина", new Date(2023, 3, 4),
                                new Date(2023, 4, 4));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        session2.close();
                        sessionFactory2.close();
                    }
                }).start();
    }
    private synchronized void  bookRoom(Session session,String roomNumber, String customer, Date checkInDate, Date checkOutDate) {
        try {
            session.beginTransaction();
            // Получение объекта HotelRoom с пессимистической блокировкой
            Query<HotelRoom> list = session.createQuery("FROM  org.example.HotelRoom as hr WHERE hr.numberRoom = :numberRoom", HotelRoom.class).
                    setParameter("numberRoom", roomNumber)
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE);
            HotelRoom room = list.uniqueResult();

            if (room != null && room.getAvailable()) {
                // Проверка доступности номера на указанные даты
                Query<Booking> availabilityQuery = session.createQuery(
                        "FROM org.example.Booking as book WHERE book.checkOutDate >= :checkInDate AND book.checkInDate <= :checkOutDate AND book.hotelRoom = :room", Booking.class);
                availabilityQuery.setParameter("room", room);
                availabilityQuery.setParameter("checkInDate", checkInDate);
                availabilityQuery.setParameter("checkOutDate", checkOutDate);
                List<Booking> conflictingBookings = availabilityQuery.list();

                if (conflictingBookings.isEmpty()) {
                    System.out.println(customer + ": Номер забронирован");
                    room.setAvailable(false);
                    session.update(room);
                    session.getTransaction().commit();
                } else {
                    throw new RuntimeException(customer + ": Бронирование невозможно. Номер недоступен на указанные даты.");
                }
            } else {
                throw new RuntimeException(customer + ": Бронирование невозможно. Номер не существует или недоступен.");
            }
        } finally{
            if (session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }
}





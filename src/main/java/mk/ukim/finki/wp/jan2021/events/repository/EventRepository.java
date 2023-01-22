package mk.ukim.finki.wp.jan2021.events.repository;

import mk.ukim.finki.wp.jan2021.events.model.Event;
import mk.ukim.finki.wp.jan2021.events.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {

        @Query("select u from Event u where u.price < ?1 and u.type= ?2 ")
        List<Event> find(Double price, EventType type);

        @Query("select u from Event u where u.price < ?1")
        List<Event> findByCena(Double price);

        List<Event> findByType(EventType cena);

        List<Event> findAllByPriceLessThanAndType(Double price, EventType type);
        List<Event> findAllByPriceLessThan(Double price);
        List<Event> findAllByType(EventType type);

}

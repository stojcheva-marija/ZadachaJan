package mk.ukim.finki.wp.jan2021.events.service.impl;

import mk.ukim.finki.wp.jan2021.events.model.Event;
import mk.ukim.finki.wp.jan2021.events.model.EventLocation;
import mk.ukim.finki.wp.jan2021.events.model.EventType;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventIdException;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventLocationIdException;
import mk.ukim.finki.wp.jan2021.events.repository.EventLocationRepository;
import mk.ukim.finki.wp.jan2021.events.repository.EventRepository;
import mk.ukim.finki.wp.jan2021.events.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl  implements EventService {

    private final EventRepository eventRepository;
    private final EventLocationRepository eventLocationRepository;

    public EventServiceImpl(EventRepository eventRepository, EventLocationRepository eventLocationRepository) {
        this.eventRepository = eventRepository;
        this.eventLocationRepository = eventLocationRepository;
    }

    @Override
    public List<Event> listAllEvents() {
        return this.eventRepository.findAll();
    }

    @Override
    public Event findById(Long id) {
        return this.eventRepository.findById(id).orElseThrow(InvalidEventIdException::new);

    }

    @Override
    public Event create(String name, String description, Double price, EventType type, Long location) {
        EventLocation eventLocation=eventLocationRepository.findById(location).orElseThrow(InvalidEventLocationIdException::new);
        return eventRepository.save(new Event(name,description,price,type,eventLocation));
    }

    @Override
    public Event update(Long id, String name, String description, Double price, EventType type, Long location) {
        EventLocation eventLocation=eventLocationRepository.findById(location).orElseThrow(InvalidEventLocationIdException::new);
            Event e= findById(id);
           e.setDescription(description);
           e.setName(name);
           e.setLocation(eventLocation);
           e.setType(type);
           e.setPrice(price);

          return eventRepository.save(e);
    }

    @Override
    public Event delete(Long id) {
        Event event =eventRepository.findById(id).orElseThrow(InvalidEventIdException::new);
        this.eventRepository.deleteById(id);
        return event;
    }

    @Override
    public Event like(Long id) {

        Event e= findById(id);

        int likes=e.getLikes();
        likes++;
        e.setLikes(likes);
       return eventRepository.save(e);

    }

    @Override
    public List<Event> listEventsWithPriceLessThanAndType(Double price, EventType type) {

        if(price==null && type==null)
            return this.eventRepository.findAll();
        else if(price==null)
            return this.eventRepository.findAllByType(type);
        else if (type==null)
            return this.eventRepository.findAllByPriceLessThan(price);
        else
            return this.eventRepository.findAllByPriceLessThanAndType(price,type);
    }
}

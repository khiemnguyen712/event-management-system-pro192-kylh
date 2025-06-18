package Service;

import Model.Event;

import java.util.List;

public interface EventOperations {

    void createEvent(Event event);

    void updateEvent(int eventId, Event event);

    boolean deleteEvent(int eventId);

    List<Event> findEventsByName(String name);

    List<Event> listAllEvents();

    void saveToFile(String fileName);
}

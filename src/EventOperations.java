import java.util.List;

public interface EventOperations {

    public void createEvent(Event event);

    public void updateEvent(int eventId, Event event);

    public boolean deleteEvent(int eventId);

    public List<Event> findEventsByName(String name);

    public List<Event> listAllEvents();

    public void saveToFile(String fileName);
}

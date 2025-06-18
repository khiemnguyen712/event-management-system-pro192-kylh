package App;

import Model.Event;
import Model.Organizer;
import Model.Venue;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventManagement implements EventOperations {

    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Organizer> organizers = new ArrayList<>();
    private ArrayList<Venue> venues = new ArrayList<>();
    private final Scanner superScanner = new Scanner(System.in);

    public static void main(String[] args) {

    }

    @Override
    public void createEvent(Event event) {
        events.add(event);
    }

    @Override
    public void updateEvent(int eventId, Event event) {

    }

    @Override
    public boolean deleteEvent(int eventId) {
        return events.removeIf(event -> event.getEventId() == eventId);
    }

    @Override
    public List<Event> findEventsByName(String name) {
        List<Event> result = new ArrayList<>();
        for (Event event : events) {
            if (event.getEventName().equalsIgnoreCase(name)) {
                result.add(event);
            }
        }
        return result;
    }

    @Override
    public List<Event> listAllEvents() {
        return new ArrayList<>(events);
    }

    @Override
    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Event event : events) {
                writer.println(event.getEventId() + "," +
                        event.getEventName() + "," +
                        event.getOrganizerId() + "," +
                        event.getVenueId() + "," +
                        event.getStartDate() + "," +
                        event.getEndDate() + "," +
                        event.getExpectedAttendees());
            }
            System.out.println("✅ Events saved to " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Error saving to CSV: " + e.getMessage());
        }
    }
}

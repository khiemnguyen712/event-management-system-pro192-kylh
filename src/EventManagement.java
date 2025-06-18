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
        EventManagement em = new EventManagement();
        em.dummyDataInit();
        em.createEvent(null);
        listEventDetails(em.events);
    }

    @Override
    public void createEvent(Event event) {
        int eventId;
        while (true) {
            System.out.println("Enter the event ID:");
            try {
                eventId = Integer.parseInt(superScanner.nextLine());
                if (isInvalidEventId(eventId)) {
                    System.out.println("❌ Event ID already exists or invalid. Please enter a unique valid ID.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid entry. Please enter a valid number.");
            }
        }

        System.out.println("Enter the event name:");
        String eventName = superScanner.nextLine();

        System.out.println("Pick an organizer from ID:");
        listOrganizerId(organizers);
        int organizerId;
        while (true) {
            System.out.println("Enter the organizer ID:");
            try {
                organizerId = Integer.parseInt(superScanner.nextLine());
                if (!isValidOrganizerId(organizerId)) {
                    System.out.println("❌ This organizer ID does not exist.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid entry. Please enter a valid number.");
            }
        }

        System.out.println("Pick a venue from ID:");
        listVenueId(venues);
        int venueId;
        while (true) {
            System.out.println("Enter the venue ID:");
            try {
                venueId = Integer.parseInt(superScanner.nextLine());
                if (!isValidVenueId(venueId)) {
                    System.out.println("❌ This venue ID does not exist.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid entry. Please enter a valid number.");
            }
        }

        String startDate;
        String endDate;
        while (true) {
            while (true) {
                System.out.println("Enter the start date (YYYY-MM-DD):");
                startDate = superScanner.nextLine();
                if (isInvalidDateFormat(startDate)) {
                    System.out.println("❌ Invalid start date. Please try again.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.println("Enter the end date (YYYY-MM-DD):");
                endDate = superScanner.nextLine();
                if (isInvalidDateFormat(endDate)) {
                    System.out.println("❌ Invalid end date. Please try again.");
                } else {
                    break;
                }
            }

            LocalDate parsedStartDate = LocalDate.parse(startDate);
            LocalDate parsedEndDate = LocalDate.parse(endDate);

            if (parsedEndDate.isBefore(parsedStartDate)) {
                System.out.println("❌ End date cannot be before start date. Please enter both dates again.");
                continue;
            }
            break;
        }

        int expectedAttendees;
        while (true) {
            System.out.println("Enter the number of expected attendees:");
            try {
                expectedAttendees = Integer.parseInt(superScanner.nextLine());
                if (expectedAttendees < 0) {
                    System.out.println("❌ Invalid entry. Must be non-negative.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid entry. Please enter a valid number.");
            }
        }

        events.add(new Event(eventId, eventName, organizerId, venueId, startDate, endDate, expectedAttendees));
    }

    @Override
    public void updateEvent(int eventId, Event event) {
        // Implementation goes here
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

    public void dummyDataInit() {
        organizers.add(new Organizer(1, "React Wreckers"));
        organizers.add(new Organizer(2, "Node Knitters"));
        organizers.add(new Organizer(3, "Express Sprout"));
        organizers.add(new Organizer(4, "Spring Spire"));
        organizers.add(new Organizer(5, "Mongo Mambas"));

        venues.add(new Venue(1, "Gumball Gutter G Strings"));
        venues.add(new Venue(2, "Buttercup Bombers H"));
        venues.add(new Venue(3, "Penny Fiztgerald Foo"));
        venues.add(new Venue(4, "Banana Joe Beamer X"));
        venues.add(new Venue(5, "Rob Wombat"));
    }

    public static void listOrganizerId(List<Organizer> organizers) {
        for (Organizer organizer : organizers) {
            System.out.println(organizer.getOrganizerId() + " for " + organizer.getOrganizerName());
        }
    }

    public static void listVenueId(List<Venue> venues) {
        for (Venue venue : venues) {
            System.out.println(venue.getVenueId() + " for " + venue.getVenueName());
        }
    }

    public boolean isInvalidEventId(int id) {
        if (String.valueOf(id).length() < 3) return true;
        for (Event event : events) {
            if (event.getEventId() == id) return true;
        }
        return false;
    }

    public boolean isValidOrganizerId(int organizerId) {
        for (Organizer organizer : organizers) {
            if (organizer.getOrganizerId() == organizerId) return true;
        }
        return false;
    }

    public boolean isValidVenueId(int venueId) {
        for (Venue venue : venues) {
            if (venue.getVenueId() == venueId) return true;
        }
        return false;
    }

    public boolean isInvalidDateFormat(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(dateStr, formatter);
            return false;
        } catch (DateTimeParseException e) {
            return true;
        }
    }

    public static void listEventDetails(ArrayList<Event> events) {
        for (Event event : events) {
            System.out.println(event);
        }
    }
}

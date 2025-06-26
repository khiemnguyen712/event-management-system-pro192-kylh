package Service;

import Model.Event;
import Model.Organizer;
import Model.Venue;
import Utility.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ListFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EventManagement implements EventOperations {
    private ArrayList<Event> events;
    private ArrayList<Organizer> organizers;
    private ArrayList<Venue> venues;

    public EventManagement() {
        this.events = new ArrayList<>();
        this.organizers = new ArrayList<>();
        this.venues = new ArrayList<>();
    }

    // TODO--------------------------------------Program Operations--------------------------------------

    @Override
    public void createEvent(Event event) {
        events.add(event);
    }

    @Override
    public void updateEvent(int idToUpdate, Event updatedEvent) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventId() == idToUpdate) {
                events.set(i, updatedEvent);
                break;
            }
        }
    }

    @Override
    public boolean deleteEvent(int eventId) {
        return events.removeIf((event) -> event.getEventId() == eventId);
    }

    @Override
    public List<Event> findEventsByName(String name) {
        return events.stream()
                .filter((event) -> event.getEventName().toLowerCase().contains(name))
                .sorted(Comparator.comparing(Event::getEventName))
                .toList();
    }

    public Event findEventById(int id) {
        return events.stream()
                .filter((event) -> event.getEventId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Event> listAllEvents() {
        return events.stream()
                .sorted(Comparator.comparing(Event::getEventName).thenComparing(Event::getEventId))
                .toList();
    }

@Override
public boolean saveToFile(String fileName) {
    // TODO--------------------------------------Snykai Security Patch/*--------------------------------------
    Path baseDirectory = Paths.get("data").toAbsolutePath().normalize();
    Path targetPath = baseDirectory.resolve(fileName).normalize();

    if (!targetPath.startsWith(baseDirectory)) {
        return false;
    }
    // TODO--------------------------------------*/Snykai Security Patch--------------------------------------
    try {
        Files.createDirectories(baseDirectory);
        try (PrintWriter writer = new PrintWriter(targetPath.toFile())) {
            for (Event event : events) {
                writer.println(event.getEventId() + "," +
                        event.getEventName() + "," +
                        event.getOrganizerId() + "," +
                        event.getVenueId() + "," +
                        event.getStartDate() + "," +
                        event.getEndDate() + "," +
                        event.getExpectedAttendees());
            }
        }
    } catch (IOException e) {
        return false;
    }

    return true;
}


    // TODO--------------------------------------Program Interface--------------------------------------

    public void runProgram() {
        Validator validator = new Validator();
        Scanner scanner = new Scanner(System.in);
        initializeData();

        program:
        while (true) {
            System.out.println("✨ Enter your option ✨");
            System.out.println("1) Create an event");
            System.out.println("2) Display all events");
            System.out.println("3) Update an event");
            System.out.println("4) Delete an event");
            System.out.println("5) Find events by name");
            System.out.println("6) Save events to file");
            System.out.println("7) Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    Event newEvent = validator.inputNewEvent(events, organizers, venues);
                    createEvent(newEvent);
                }
                case "2" -> {
                    if (listAllEvents().isEmpty()) {
                        System.out.println("\uD83D\uDCDB No event to display!");
                        break;
                    }
                    for (Event event : listAllEvents()) {
                        System.out.println(event);
                    }
                }
                case "3" -> {
                    if (listAllEvents().isEmpty()) {
                        System.out.println("\uD83D\uDCDB No event to update!");
                        break;
                    }
                    int idToUpdate = validator.inputLooseEventId("update");
                    Event existingEvent = findEventById(idToUpdate);
                    if (existingEvent != null) {
                        System.out.println("\uD83D\uDEA9 Leave blank to keep current info.");
                        Event updatedEvent = validator.inputUpdatedEvent(events, organizers, venues, existingEvent);
                        updateEvent(idToUpdate, updatedEvent);
                        System.out.println("✅ Event updated successfully.");
                    } else {
                        System.out.println("❌ Event not found.");
                    }
                }
                case "4" -> {
                    if (listAllEvents().isEmpty()) {
                        System.out.println("\uD83D\uDCDB No event to delete!");
                        break;
                    }
                    int idToDelete = validator.inputLooseEventId("delete");
                    Event eventToDelete = findEventById(idToDelete);
                    if (eventToDelete == null) {
                        System.out.println("❌ Event not found.");
                        break;
                    }
                    System.out.println("❓ Are you sure you want to delete: " + eventToDelete.getEventName() + " [" + eventToDelete.getEventId() + "]"+ "? [y/n]");
                    String confirmation = scanner.nextLine().toLowerCase();
                    if (confirmation.equals("y")) {
                        boolean isSuccessful = deleteEvent(idToDelete);
                        System.out.println(isSuccessful ? "✅ Delete successful." : "❌ Delete failed.");
                    } else if (confirmation.equals("n")) {
                        System.out.println("❌ Deletion cancelled.");
                    } else {
                        System.out.println("❌ Invalid option.");
                    }
                }
                case "5" -> {
                    if (listAllEvents().isEmpty()) {
                        System.out.println("\uD83D\uDCDB No event to search for!");
                        break;
                    }
                    System.out.println("✋ Enter event name to search:");
                    String nameToSearch = scanner.nextLine().toLowerCase();
                    List<Event> foundEvents = findEventsByName(nameToSearch);
                    if (!foundEvents.isEmpty()) {
                        foundEvents.forEach(System.out::println);
                    } else {
                        System.out.println("❌ Event not found.");
                    }
                }
                case "6" -> {
                    if (listAllEvents().isEmpty()) {
                        System.out.println("\uD83D\uDCDB No event to save!");
                        break;
                    }
                    System.out.println("✋ Enter the file name to save to:");
                    String fileName = scanner.nextLine() + ".csv";
                    if (saveToFile(fileName)) {
                        System.out.println("✅ Events saved to " + fileName);
                    } else {
                        System.out.println("❌ Error saving file.");
                    }
                }
                case "7" -> {
                    System.out.println("\uD83D\uDC96 Exited!");
                    break program;
                }
                default -> {
                    System.out.println("❌ Invalid option.");
                }
            }
        }
    }

    private void initializeData() {
        organizers = new ArrayList<>();
        organizers.add(new Organizer(1, "Svelte Syphon"));
        organizers.add(new Organizer(2, "React Ravenous Rues"));
        organizers.add(new Organizer(3, "Express Etchers"));
        organizers.add(new Organizer(4, "Axios Avengers"));
        organizers.add(new Organizer(5, "Spring Semaphores"));
        organizers.add(new Organizer(6, "Mongo Muppet Co."));
        organizers.add(new Organizer(7, "Socket Soup Bowls"));

        venues = new ArrayList<>();
        venues.add(new Venue(1, "Pablo Escobar"));
        venues.add(new Venue(2, "Patient Saint Pim"));
        venues.add(new Venue(3, "Simon Petrikov"));
        venues.add(new Venue(4, "Marceline Albadeer"));
        venues.add(new Venue(5, "Finn Mertens"));
        venues.add(new Venue(6, "Fiona Campbell"));
        venues.add(new Venue(7, "Lady Rainicorn"));
    }

    // TODO--------------------------------------Program Entry--------------------------------------

    public static void main(String[] args) {
        EventManagement eventManagement = new EventManagement();
        eventManagement.initializeData();
        eventManagement.runProgram();
    }
}


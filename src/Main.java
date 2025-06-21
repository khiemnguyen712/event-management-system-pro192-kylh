import Model.Event;
import Model.Organizer;
import Model.Venue;
import Service.EventManagement;
import Utility.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Venue> venues = new ArrayList<>();
        venues.add(new Venue(0, "Jamal Hill"));
        venues.add(new Venue(1, "Damian Mia"));
        venues.add(new Venue(2, "Khalil Rountree Jr."));
        venues.add(new Venue(3, "Alexander Volkanovski"));
        venues.add(new Venue(4, "Islam Makhachev"));

        ArrayList<Organizer> organizers = new ArrayList<>();
        organizers.add(new Organizer(0, "React Rummage"));
        organizers.add(new Organizer(1, "Spring Sewage"));
        organizers.add(new Organizer(2, "Express Emphatic"));
        organizers.add(new Organizer(3, "Torch Ticklers"));
        organizers.add(new Organizer(4, "Socket Semaphores"));

        ArrayList<Event> events = new ArrayList<>();
        EventManagement eventManagement = new EventManagement(events);

        Validator validator = new Validator();
        Scanner scanner = new Scanner(System.in);

        program:
        while (true) {
            System.out.println("Enter your option");
            System.out.println("(1) Create an event");
            System.out.println("(2) Display all events");
            System.out.println("(3) Update an event");
            System.out.println("(4) Delete an event");
            System.out.println("(5) Find an event by name");
            System.out.println("(6) Save an event to file");
            System.out.println("(7) Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    Event newEvent = validator.inputNewEvent(events, organizers, venues);
                    eventManagement.createEvent(newEvent);
                    System.out.println("✅ Event created successfully.");
                }
                case "2" -> {
                    System.out.println(eventManagement.listAllEvents());
                }
                case "3" -> {
                    int idToUpdate = validator.inputId("event", events);
                    Event existingEvent = eventManagement.findEventById(idToUpdate);

                    if (existingEvent == null) {
                        System.out.println("❌ Event not found.");
                    } else {
                        Event updatedEvent = validator.inputUpdatedEvent(events, organizers, venues, existingEvent);
                        eventManagement.updateEvent(idToUpdate, updatedEvent);
                        System.out.println("✅ Event updated successfully.");
                    }
                }
                case "4" -> {
                    System.out.println("✋ Enter event ID to delete:");
                    try {
                        int idToDelete = Integer.parseInt(scanner.nextLine());
                        Event eventToDelete = eventManagement.findEventById(idToDelete);
                        if (eventToDelete != null) {
                            eventManagement.deleteEvent(idToDelete);
                            System.out.println("✅ Event deleted successfully.");
                        } else {
                            System.out.println("❌ Event not found.");
                        }
                    } catch (NumberFormatException _) {
                        System.out.println("❌ Not a valid number.");
                    }
                }
                case "5" -> {
                    System.out.println("✋ Enter event name to search:");
                    String nameToFind = scanner.nextLine();
                    List<Event> foundEvents = eventManagement.findEventsByName(nameToFind);
                    if (foundEvents != null) {
                        System.out.println(foundEvents);
                    } else {
                        System.out.println("❌ Event not found.");
                    }
                }
                case "6" -> {
                    System.out.println("✋ Enter the file name to save to.");
                    String fileName = scanner.nextLine();
                    eventManagement.saveToFile(fileName);
                    System.out.println("✅ Events saved to file.");
                }
                case "7" -> {
                    System.out.println("Exited!");
                    break program;
                }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }
}

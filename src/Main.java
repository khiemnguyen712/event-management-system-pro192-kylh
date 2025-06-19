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
        EventManagement eventManagement = new EventManagement(events, organizers, venues);

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
                case "1":
                    Event newEvent = validator.inputNewEvent(events, organizers, venues);
                    eventManagement.createEvent(newEvent);
                    break;
                case "2":
                    System.out.println(eventManagement.listAllEvents());
                    break;
                case "3":

                    break;
                case "4":

                    break;
                case "5":

                    break;
                case "6":

                    break;
                case "7":
                    System.out.println("Exited!");
                    break program;
            }
        }
    }
}
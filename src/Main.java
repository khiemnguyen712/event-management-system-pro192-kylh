import Model.Event;
import Model.Organizer;
import Model.Venue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        List<Organizer> organizers = new ArrayList<>();
        organizers.add(new Organizer(1, "Ample ENT"));
        organizers.add(new Organizer(2, "Voltage VSX"));
        organizers.add(new Organizer(3, "Coil Cartwheels Solutions"));
        organizers.add(new Organizer(4, "Brick Bane Boobies Boppers"));
        organizers.add(new Organizer(5, "Kaneki Kupplers"));

        List<Venue> venues = new ArrayList<>();
        venues.add(new Venue(1, "Vue Vendettas"));
        venues.add(new Venue(2, "Node Nipples"));
        venues.add(new Venue(3, "Spring Sailor"));
        venues.add(new Venue(4, "React Runaway"));
        venues.add(new Venue(5, "Ajax Ample"));

        ArrayList<Event> events = new ArrayList<>();

        while (true) {
            System.out.println("------Menu------");
            System.out.println("(1) To create event.");
            System.out.println("(2) To display all events.");
            System.out.println("(3) To update an event.");
            System.out.println("(4) To delete an event.");
            System.out.println("(5) To find an event by name.");
            System.out.println("(6) To save to file (CSV).");
            System.out.println("(7) To exit.");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {

            }
            else if (choice.equals("2")) {
                // TODO logic for displaying
            }
            else if (choice.equals("3")) {
                // TODO logic for updating an event
            }
            else if (choice.equals("4")) {
                // TODO logic for deleting an event
            }
            else if (choice.equals("5")) {
                // TODO logic for finding an event by its ID
            }
            else if (choice.equals("6")) {
                // TODO logic for Saving an event to file (CSV)
            }
            else if (choice.equals("7")) {
                System.out.println("Exited!");
                break;
            }
            else {
                System.out.println("Invalid choice");
            }
        }
    }

    // -----List event details


    public static void handleEventCreation() {
        // TODO logic for handling event creation

    }
}
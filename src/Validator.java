import Model.Event;
import Model.Organizer;
import Model.Venue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Validator {

    private final Scanner scanner = new Scanner(System.in);

    // Number format, length, duplicate
    public int inputEventId(ArrayList<Event> events) {

        int eventId;

        while (true) {
            System.out.println("Enter the event ID (3 or more digits):");
            try {
                eventId = Integer.parseInt(scanner.nextLine());
                if (String.valueOf(eventId).length() < 3) {
                    System.out.println("❌ Event ID length is invalid. Please enter an ID with at least 3 digits");
                    continue;
                }
                if (hasId(eventId, events)) {
                    System.out.println("❌ Event ID already exist. Please enter a unique ID.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid entry. Please enter a valid number.");
            }
        }

        return eventId;
    }

    // No need for validation
    public String inputEventName() {
        return scanner.nextLine();
    }

    // Number format and instance
    public int inputId(String promptedType, ArrayList<?> promptedList) {

        int supposedId;

        while (true) {
            System.out.println("Enter the " + promptedType + " ID:");
            listAllId(promptedList);
            try {
                supposedId = Integer.parseInt(scanner.nextLine()); // throws exception
                if (!hasId(supposedId, promptedList)) {
                    System.out.println("❌ " + promptedType + " ID does not exist. Please select a valid ID.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid entry. Please enter a valid number");
            }
        }

        return supposedId;
    }

    public String inputDate(String promptedType) {

        String dateString;

        while (true) {
            System.out.println("Enter the " + promptedType + " date:");
            dateString = scanner.nextLine();
            if (!isValidDateFormat(dateString)) {
                System.out.println("❌ Invalid date format. Please enter a valid date (YYYY-MM-DD)");
            } else {
                break;
            }
        }

        return dateString;
    }

    public int inputExpectedAttendees() {

        int expectedAttendees;

        while (true) {
            System.out.println("Enter the number of expected attendees:");
            try {
                expectedAttendees = Integer.parseInt(scanner.nextLine());
                if (expectedAttendees < 0) {
                    System.out.println("❌ Attendee count cannot be negative. Please enter a valid count.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid entry. Please enter a valid number.");
            }
        }

        return expectedAttendees;
    }

    // --------------------------------------No Prompt Helpers--------------------------------------

    public boolean isInValidOrder(String startDate, String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);

        return parsedStartDate.isBefore(parsedEndDate) || parsedStartDate.isEqual(parsedEndDate);
    }

    // --------------------------------------Static Helpers--------------------------------------

    private static boolean hasId(int id, ArrayList<?> genericList) {
        for (Object item : genericList) {
            if (item instanceof Event && ((Event) item).getEventId() == id) { return false; }
            else if (item instanceof Organizer && ((Organizer) item).getOrganizerId() == id) { return false; }
            else if (item instanceof Venue && ((Venue) item).getVenueId() == id) { return false; }
        }
        return true;
    }

    private static void listAllId(ArrayList<?> genericList) {
        for (Object item : genericList) {
            if (item instanceof Event) {
                System.out.println(((Event) item).getEventId() + " for " + ((Event) item).getEventName());
            } else if (item instanceof Organizer){
                System.out.println(((Organizer) item).getOrganizerId() + " for " + ((Organizer) item).getOrganizerId());
            } else if (item instanceof Venue) {
                System.out.println(((Venue) item).getVenueId() + " for " + ((Venue) item).getVenueName());
            }
        }
    }

    private static boolean isValidDateFormat(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}

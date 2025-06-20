package Utility;

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

    public Event inputNewEvent(ArrayList<Event> existingEvents, ArrayList<Organizer> existingOrganizer, ArrayList<Venue> existingVenues) {
        String eventName = inputEventName();
        int eventId = inputEventId(existingEvents);
        int organizerId = inputId("organizer", existingOrganizer);
        int venueId = inputId("venue", existingVenues);
        String startDate;
        String endDate;
        while (true) {
            startDate = inputDate("start");
            endDate = inputDate("end");
            if (!isInValidOrder(startDate, endDate)) {
                System.out.println("❌ End date cannot be before start date. Please enter valid dates.");
                continue;
            }
            break;
        }
        int expectedAttendees = inputExpectedAttendees();

        return new Event(eventId, eventName, organizerId, venueId, startDate, endDate, expectedAttendees);
    }

    // --------------------------------------Update modules--------------------------------------

    public String inputUpdatedEventName(String currentName) {
        System.out.println("Enter new event name (leave blank to keep):");
        String input = scanner.nextLine();
        return input.isBlank() ? currentName : input;
    }

    // --------------------------------------Input modules residues--------------------------------------

    public int inputUpdatedExpectedAttendees(int currentAttendees) {
        System.out.println("Enter new expected attendees (leave blank to keep '" + currentAttendees + "'):");
        String input = scanner.nextLine();
        if (input.isBlank()) return currentAttendees;
        try {
            int value = Integer.parseInt(input);
            if (value < 0) {
                System.out.println("❌ Must be positive. Keeping original.");
                return currentAttendees;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number. Keeping original.");
            return currentAttendees;
        }
    }

    public String inputUpdatedDate(String prompt, String currentDate) {
        System.out.println("Enter new " + prompt + " date (YYYY-MM-DD) (leave blank to keep '" + currentDate + "'):");
        String input = scanner.nextLine();
        if (input.isBlank()) return currentDate;
        if (isValidDateFormat(input)) return input;
        System.out.println("❌ Invalid date format. Keeping original.");
        return currentDate;
    }

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

    public String inputEventName() {
        System.out.println("Enter the event name:");
        return scanner.nextLine();
    }

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

    // --------------------------------------No Prompt Helper--------------------------------------

    public boolean isInValidOrder(String startDate, String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);

        return parsedStartDate.isBefore(parsedEndDate) || parsedStartDate.isEqual(parsedEndDate);
    }

    // --------------------------------------Static Helpers--------------------------------------

    private static void listAllId(ArrayList<?> typedList) {
        for (Object item : typedList) {
            if (item instanceof Event) {
                System.out.println(((Event) item).getEventId() + " for " + ((Event) item).getEventName());
            } else if (item instanceof Organizer){
                System.out.println(((Organizer) item).getOrganizerId() + " for " + ((Organizer) item).getOrganizerName());
            } else if (item instanceof Venue) {
                System.out.println(((Venue) item).getVenueId() + " for " + ((Venue) item).getVenueName());
            }
        }
    }

    private static boolean hasId(int id, ArrayList<?> typedList) {
        for (Object item : typedList) {
            if (item instanceof Event && ((Event) item).getEventId() == id) { return true; }
            else if (item instanceof Organizer && ((Organizer) item).getOrganizerId() == id) { return true; }
            else if (item instanceof Venue && ((Venue) item).getVenueId() == id) { return true; }
        }
        return false;
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

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

    // TODO--------------------------------------Input Modules--------------------------------------

    // No need for validation
    public String inputEventName() {
        System.out.println("Enter event name:");
        return scanner.nextLine();
    }

    // Wonky
    public int inputEventId(ArrayList<Event> events) {
        while (true) {
            System.out.println("Enter event ID (3 digits minimum):");
            String input = scanner.nextLine();
            try {
                int id = Integer.parseInt(input);
                if (!isValidNewEventId(id, events)) {
                    System.out.println("❌ Invalid or duplicate Event ID.");
                    continue;
                }
                return id;
            } catch (NumberFormatException _) {
                System.out.println("❌ Not a valid number.");
            }
        }
    }

    public int inputId(String type, ArrayList<?> list) {
        while (true) {
            System.out.println("Enter " + type + " ID:");
            listAllId(list);
            String input = scanner.nextLine();
            try {
                int id = Integer.parseInt(input);
                if (!isExistingId(id, list)) {
                    System.out.println("❌ ID not found.");
                    continue;
                }
                return id;
            } catch (NumberFormatException _) {
                System.out.println("❌ Not a valid number.");
            }
        }
    }

    public String inputDate(String prompt) {
        while (true) {
            System.out.println("Enter " + prompt + " date (YYYY-MM-DD):");
            String input = scanner.nextLine();
            if (isValidDateFormat(input)) return input;
            System.out.println("❌ Invalid date format.");
        }
    }

    public int inputExpectedAttendees() {
        while (true) {
            System.out.println("Enter expected attendees:");
            String input = scanner.nextLine();
            try {
                int num = Integer.parseInt(input);
                if (num >= 0) return num;
                System.out.println("❌ Must be zero or positive.");
            } catch (NumberFormatException _) {
                System.out.println("❌ Not a valid number.");
            }
        }
    }

    // TODO--------------------------------------Update Modules--------------------------------------

    public String inputUpdatedEventName(String current) {
        System.out.println("Enter new event name (leave blank to keep '" + current + "'):");
        String input = scanner.nextLine();
        return input.isBlank() ? current : input;
    }

    public int inputUpdatedEventId(int currentId, ArrayList<Event> events) {
        System.out.println("Enter new event ID (leave blank to keep '" + currentId + "'):");
        String input = scanner.nextLine();
        if (input.isBlank()) return currentId;
        try {
            int id = Integer.parseInt(input);
            if (isValidNewEventId(id, events)) return id;
            System.out.println("❌ Invalid or duplicate ID. Keeping original.");
        } catch (NumberFormatException _) {
            System.out.println("❌ Not a valid number. Keeping original.");
        }
        return currentId;
    }

    public int inputUpdatedId(String type, int currentId, ArrayList<?> list) {
        System.out.println("Enter new " + type + " ID (leave blank to keep '" + currentId + "'):");
        listAllId(list);
        String input = scanner.nextLine();
        if (input.isBlank()) return currentId;
        try {
            int id = Integer.parseInt(input);
            if (isExistingId(id, list)) return id;
            System.out.println("❌ ID not found. Keeping original.");
        } catch (NumberFormatException _) {
            System.out.println("❌ Not a valid number. Keeping original.");
        }
        return currentId;
    }

    public String inputUpdatedDate(String prompt, String current) {
        System.out.println("Enter new " + prompt + " date (leave blank to keep '" + current + "'):");
        String input = scanner.nextLine();
        if (input.isBlank()) return current;
        if (isValidDateFormat(input)) return input;
        System.out.println("❌ Invalid date format. Keeping original.");
        return current;
    }

    public int inputUpdatedExpectedAttendees(int current) {
        System.out.println("Enter new expected attendees (leave blank to keep '" + current + "'):");
        String input = scanner.nextLine();
        if (input.isBlank()) return current;
        try {
            int num = Integer.parseInt(input);
            if (num >= 0) return num;
            System.out.println("❌ Must be zero or positive. Keeping original.");
        } catch (NumberFormatException e) {
            System.out.println("❌ Not a valid number. Keeping original.");
        }
        return current;
    }

    // TODO--------------------------------------Validators--------------------------------------

    private boolean isValidNewEventId(int id, ArrayList<Event> events) {
        return String.valueOf(id).length() >= 3 && !isExistingId(id, events);
    }

    private boolean isValidDateFormat(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isInValidOrder(String start, String end) {
        LocalDate s = LocalDate.parse(start);
        LocalDate e = LocalDate.parse(end);
        return !e.isBefore(s);
    }

    // TODO--------------------------------------Static helpers--------------------------------------

    private void listAllId(ArrayList<?> list) {
        for (Object item : list) {
            if (item instanceof Event) {
                System.out.println(((Event) item).getEventId() + " for " + ((Event) item).getEventName());
            } else if (item instanceof Organizer) {
                System.out.println(((Organizer) item).getOrganizerId() + " for " + ((Organizer) item).getOrganizerName());
            } else if (item instanceof Venue) {
                System.out.println(((Venue) item).getVenueId() + " for " + ((Venue) item).getVenueName());
            }
        }
    }

    private boolean isExistingId(int id, ArrayList<?> list) {
        for (Object item : list) {
            if (item instanceof Event&& ((Event) item).getEventId() == id) return true;
            if (item instanceof Organizer && ((Organizer) item).getOrganizerId() == id) return true;
            if (item instanceof Venue && ((Venue) item).getVenueId() == id) return true;
        }
        return false;
    }
}

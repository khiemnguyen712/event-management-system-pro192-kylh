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

    public Event inputNewEvent(ArrayList<Event> events, ArrayList<Organizer> organizers, ArrayList<Venue> venues) {
        int eventId = inputEventId(events);
        String eventName = inputEventName();
        int organizerId = inputId("organizer", organizers);
        int venueId = inputId("venue", venues);
        String startDate;
        String endDate;
        while (true) {
            startDate = inputDate("start");
            endDate = inputDate("end");
            if (!isValidDateOrder(startDate, endDate)) {
                System.out.println("❌ Invalid date order.");
            } else {
                break;
            }
        }
        int expectedAttendees = inputExpectedAttendees();
        return new Event(eventId, eventName, organizerId, venueId, startDate, endDate, expectedAttendees);
    }

    public String inputEventName() {
        System.out.println("✋ Enter the event name:");
        return scanner.nextLine();
    }

    public int inputEventId(ArrayList<Event> events) {
        while (true) {
            System.out.println("✋ Enter the event ID (3 digits minimum):");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if (isExistingId(id, events)) {
                    System.out.println("❌ Duplicated event ID.");
                    continue;
                }
                if (String.valueOf(id).length() < 3) {
                    System.out.println("❌ ID must be at least 3 digits.");
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
            System.out.println("✋ Enter the " + type + " ID:");
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
            System.out.println("✋ Enter the " + prompt + " date (YYYY-MM-DD):");
            String input = scanner.nextLine();
            if (isValidDateFormat(input)) {
                return input;
            }
            System.out.println("❌ Invalid date format.");
        }
    }

    public int inputExpectedAttendees() {
        while (true) {
            System.out.println("✋ Enter the number of expected attendees:");
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

    public Event inputUpdatedEvent(ArrayList<Event> events, ArrayList<Organizer> organizers, ArrayList<Venue> venues, Event existingEvent) {
        int updatedId = inputUpdatedEventId(existingEvent.getEventId(), events);
        String updatedName = inputUpdatedEventName(existingEvent.getEventName());
        int updatedOrganizerId = inputUpdatedId("organizer", existingEvent.getOrganizerId(), organizers);
        int updatedVenueId = inputUpdatedId("venue", existingEvent.getVenueId(), venues);
        String updatedStartDate;
        String updatedEndDate;
        while (true) {
            updatedStartDate = inputUpdatedDate("start", existingEvent.getStartDate());
            updatedEndDate = inputUpdatedDate("end", existingEvent.getEndDate());
            if (isValidDateOrder(updatedStartDate, updatedEndDate)) {
                break;
            } else {
                System.out.println("❌ Invalid date order.");
            }
        }
        int updatedExpectedAttendees = inputUpdatedExpectedAttendees(existingEvent.getExpectedAttendees());
        return new Event(updatedId, updatedName, updatedOrganizerId, updatedVenueId, updatedStartDate, updatedEndDate, updatedExpectedAttendees);
    }

    public String inputUpdatedEventName(String currentName) {
        System.out.println("✋ Enter new event name:");
        String input = scanner.nextLine();
        return input.isBlank() ? currentName : input;
    }

    public int inputUpdatedEventId(int currentId, ArrayList<Event> events) {
        while (true) {
            System.out.println("✋ Enter new event ID:");
            String input = scanner.nextLine();
            if (input.isBlank()) { return currentId; }
            try {
                int updatedId = Integer.parseInt(input);
                if (isExistingId(updatedId, events)) {
                    System.out.println("❌ Duplicated event ID");
                    continue;
                }
                if (String.valueOf(updatedId).length() < 3) {
                    System.out.println("❌ ID must be at least 3 digits.");
                    continue;
                }
                return updatedId;
            } catch (NumberFormatException _) {
                System.out.println("❌ Not a valid number.");
            }
        }
    }

    public int inputUpdatedId(String type, int currentId, ArrayList<?> list) {
        while (true) {
            System.out.println("✋ Enter new " + type + " ID:");
            listAllId(list);
            String input = scanner.nextLine();
            if (input.isBlank()) {
                return currentId;
            }
            try {
                int updatedId = Integer.parseInt(input);
                if (!isExistingId(updatedId, list)) {
                    System.out.println("❌ ID not found");
                    continue;
                }
                return updatedId;
            } catch (NumberFormatException _) {
                System.out.println("❌ Not a valid number. Keeping original.");
            }
        }
    }

    public String inputUpdatedDate(String prompt, String current) {
        while (true) {
            System.out.println("Enter new " + prompt + " date:");
            String input = scanner.nextLine();
            if (input.isBlank()) {
                return current;
            }
            if (isValidDateFormat(input)) {
                return input;
            }
            System.out.println("❌ Invalid date format");
        }
    }

    public int inputUpdatedExpectedAttendees(int currentExpectedAttendees) {
        while (true) {
            System.out.println("Enter new expected attendees (leave blank to keep '" + currentExpectedAttendees + "'):");
            String input = scanner.nextLine();
            if (input.isBlank()) {
                return currentExpectedAttendees;
            }
            try {
                int newExpectedAttendees = Integer.parseInt(input);
                if (newExpectedAttendees >= 0) {
                    return newExpectedAttendees;
                }
                System.out.println("❌ Must not be negative.");
            } catch (NumberFormatException _) {
                System.out.println("❌ Not a valid number.");
            }
        }
    }

    // TODO--------------------------------------Validators--------------------------------------

    private boolean isValidNewEventId(int id, ArrayList<Event> events) {
        return String.valueOf(id).length() >= 3 && !isExistingId(id, events);
    }

    private boolean isValidDateFormat(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException _) {
            return false;
        }
    }

    public boolean isValidDateOrder(String startDate, String endDate) {
        LocalDate tempStart = LocalDate.parse(startDate);
        LocalDate tempEnd = LocalDate.parse(endDate);
        return tempStart.isBefore(tempEnd) || tempStart.isEqual(tempEnd);
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

public class Event {

    private int eventId;
    private String eventName;
    private int organizerId;
    private int venueId;
    private String startDate;
    private String endDate;
    private int expectedAttendees;

    public Event(int eventId, String eventName, int organizerId, int venueId, String startDate, String endDate, int expectedAttendees) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.organizerId = organizerId;
        this.venueId = venueId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedAttendees = expectedAttendees;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getExpectedAttendees() {
        return expectedAttendees;
    }

    public void setExpectedAttendees(int expectedAttendees) {
        this.expectedAttendees = expectedAttendees;
    }

    @Override
    public String toString() {
        return "";
    }
}

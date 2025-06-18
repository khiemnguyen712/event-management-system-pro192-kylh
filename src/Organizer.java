public class Organizer {

    private int organizerId;
    private String organizerName;

    public Organizer(int organizerId, String organizerName) {
        this.organizerId = organizerId;
        this.organizerName = organizerName;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    @Override
    public String toString() {
        return "Organizer{" +
                "organizerId=" + organizerId +
                ", organizerName='" + organizerName + '\'' +
                '}';
    }
}

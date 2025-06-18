package Model;

public class Venue {

    private int venueId;
    private String venueName;

    public Venue(int venueId, String venueName) {
        this.venueId = venueId;
        this.venueName = venueName;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    @Override
    public String toString() {
        return "Model.Venue{" +
                "venueId=" + venueId +
                ", venueName='" + venueName + '\'' +
                '}';
    }
}

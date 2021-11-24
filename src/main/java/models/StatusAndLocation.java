package models;

public class StatusAndLocation {
    private Long userId;
    private Long longitude;
    private Long latitude;
    private Status status;

    public StatusAndLocation(Long userId, Long longitude, Long latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "StatusAndLocation{" +
                "userId=" + userId +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}

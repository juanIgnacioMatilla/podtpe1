
public enum Availability {
  AVAILABLE,
  UNAVAILABLE,
  ATTENDING;

  public static Availability fromString(String status) {
    switch (status.toUpperCase()) {
      case "AVAILABLE":
        return AVAILABLE;
      case "UNAVAILABLE":
        return UNAVAILABLE;
      case "ATTENDING":
        return ATTENDING;
      default:
        throw new IllegalArgumentException("Unknown status: " + status);
    }
  }

  @Override
  public String toString() {
    return name();
  }
}

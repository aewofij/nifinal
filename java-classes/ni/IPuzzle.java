package ni;

public abstract class IPuzzle {

  // starts - send video trigger events
  public abstract void start ();

  // Receives real-time user events - button presses, video markers, other runner data...
  public abstract void receiveInput (IUserInputEvent event);

  public void successful () {
    // TODO
  }

  public void failure () {
    // TODO
  }
}
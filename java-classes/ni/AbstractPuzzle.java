package ni;

public abstract class AbstractPuzzle {

  // Runner who is running this puzzle.
  public Runner runner;

  // External object calls this to begin puzzle operation.
  public abstract void start ();

  // Receives real-time user events - button presses, video markers, other runner data...
  public abstract void receiveInput (String event);

  // Does this puzzle restart on failure?
  public boolean isRepeatable;

  // Called when puzzle is succesfully completed.
  public void successful () {
    runner.finishedPuzzle(true);
  }

  // Called when puzzle is succesfully failed.
  public void failure () {
    runner.finishedPuzzle(false);
  }
}
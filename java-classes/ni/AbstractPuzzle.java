package ni;

public abstract class AbstractPuzzle {

  // Runner who is running this puzzle.
  public Runner runner;

  // Called at start of puzzle.
  public abstract void start ();

  // Called at end of puzzle. Any teardown goes here.
  public abstract void end ();

  // Receives real-time user events - button presses, video markers, other runner data...
  public abstract void receiveInput (String event);

  // Does this puzzle restart on failure?
  public boolean isRepeatable;

  // Called when puzzle is succesfully completed.
  public void successful () {
    runner.finishedPuzzle(true);
    this.end();
  }

  // Called when puzzle is succesfully failed.
  public void failure () {
    runner.finishedPuzzle(false);
    this.end();
  }
  
}
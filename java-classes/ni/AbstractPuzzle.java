package ni;

public abstract class AbstractPuzzle {

  // Runner who is running this puzzle.
  public Runner runner;

  // Called at start of puzzle.
  public abstract void start ();

  // Called when user submits a response. (Any teardown goes here.)
  public abstract void end ();

  // Receives real-time user events - button presses, video markers, other runner data...
  public abstract void receiveInput (String event);

  public String successTransition;
  public String failureTransition;

  // Does this puzzle restart on failure?
  public boolean isRepeatable;

  // Called when puzzle is succesfully completed.
  public void successful () {
    if (this.runner != null) {
      // System.out.println("Succeeded on " + (this.runner.isLeft ? "left" : "right") + " runner");
    }
    this.end();
    runner.finishedPuzzle(true);
  }

  // Called when puzzle is succesfully failed.
  public void failure () {
    if (this.runner != null) {
      // System.out.println("Failed on " + (this.runner.isLeft ? "left" : "right") + " runner");
    }
    this.end();
    runner.finishedPuzzle(false);
  }
  
}
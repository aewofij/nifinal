package ni.puzzles;

public class RhythmPuzzle extends AbstractPuzzle {

  private boolean isPrimed = false;

  // External object calls this to begin puzzle operation.
  public void start () {

  }

  // Receives real-time user events - button presses, video markers, other runner data...
  public void receiveInput (String event) {
    if (event.equals("video start")) {
      this.isPrimed = true;
    } else if (event.equals("video start")) {
      super.failure();
    } else if (this.isPrimed) {
      if (event.equals("user button 3")) {
        super.successful();
      } else {
        super.failure();
      }
    } 
  }

  // Does this puzzle restart on failure?
  public boolean isRepeatable = true;
}
package ni;

public class RhythmPuzzle extends AbstractPuzzle {

  // Does this puzzle restart on failure?
  public boolean isRepeatable = true;

  private boolean isPrimed = false;
  private boolean active;
  private String videoFile;

  public RhythmPuzzle (String videoFile) {
    this.videoFile = videoFile;
    this.active = false;
  }

  // External object calls this to begin puzzle operation.
  public void start () {
    if (this.runner != null) {
      this.runner.firePatchControl("loadvideo " + videoFile);
    }
    this.active = true;
  }

  public void end () {
    this.active = false;
  }

  // Receives real-time user events 
  // - button presses, video markers, other runner data...
  public void receiveInput (String event) {
    if (this.active) {
      if (event.equals("video start")) {
        System.out.println("Puzzle primed.");
        this.isPrimed = true;
      } else if (event.equals("video stop")) {
        System.out.println("FAILURE");
        super.failure();
      } else if (this.isPrimed) {
        if (event.equals("ctrl button 3")) {
          super.successful();
          System.out.println("SUCCESS");
        } else {
          System.out.println("ALSO FAILURE");
          super.failure();
        }
      } 
    }
  }
}
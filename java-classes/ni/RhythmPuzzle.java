package ni;

public class RhythmPuzzle extends AbstractPuzzle {

  // Does this puzzle restart on failure?
  public boolean isRepeatable = true;

  private boolean isPrimed = false;
  private String videoFile;

  public RhythmPuzzle (String videoFile) {
    this.videoFile = videoFile;
  }

  // External object calls this to begin puzzle operation.
  public void start () {
    if (this.runner != null) {
      this.runner.firePatchControl("loadvideo " + videoFile);
    }
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
}
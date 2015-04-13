import ni.AbstractPuzzle;

class MultipleChoicePuzzle() extends AbstractPuzzle {
  // Called at start of puzzle.
  def start: Unit = {
    System.out.println("starting");
  }

  // Called at end of puzzle. Any teardown goes here.
  def end: Unit = {
    System.out.println("ending");
  }

  // Receives real-time user events - button presses, video markers, other runner data...
  def receiveInput(event: String): Unit = {
    System.out.println("received event " + event);
  }
}
// TODO: how to send puzzle data to Runners?

public class Referee {

  // singleton
  private static Referee instance;
  public static Referee getInstance() {
    if (instance == null) {
      instance = new Referee();
    }
    return instance;
  }

  private PuzzleSequence puzzles;
  private Puzzle currentPuzzle;
  private boolean player1Complete;
  private boolean player2Complete;

  // called when a player completes a puzzle
  public void completed (who) {

  }

  public void transition () {
    // eventually calls `sendNextPuzzle`
  }

  public void repeatPuzzle () {

  }

  public void sendNextPuzzle () {

  }
}
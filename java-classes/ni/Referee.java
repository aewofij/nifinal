package ni;

import java.util.*;

public class Referee {

  enum Player {
    left, right
  };

  // singleton
  private static Referee instance;
  public static Referee getInstance() {
    if (instance == null) {
      instance = new Referee();
    }
    return instance;
  }

  private PuzzleSequence puzzles;
  private Runner leftRunner, rightRunner;
  private Player completed = null;
  private boolean completedSuccessful;

  public Referee () {
    // TODO
  }

  // called when a player completes a puzzle
  public void completed (boolean isLeftPlayer, boolean success) {
    if (!success) {
      punish();
    }

    if (completed == null) {
      completed = isLeftPlayer ? Player.left : Player.right;
      completedSuccessful = success;
      return;
    } else {
      if (isLeftPlayer && completed == Player.right) {
        transition(success, completedSuccessful);
      }
      if (!isLeftPlayer && completed == Player.left) {
        transition(completedSuccessful, success);
      }
    }
  }

  public void punish () {
    // TODO
  }

  public void transition (boolean leftSuccess, boolean rightSuccess) {
    if (leftSuccess && rightSuccess) {
      sendPuzzle(puzzles.next());
    } else {
      if (currentPuzzle.isRepeatable) {
        sendPuzzle(puzzles.current());
      } else {
        sendPuzzle(puzzles.next());
      }
    }
  }

  public void sendNextPuzzle (IPuzzle puzzle) {
    if (leftRunner != null) {
      if (!leftRunner.receiveNextPuzzle(puzzle)) {
        System.err.println("Left runner did not receive puzzle!");
      }
    }
    if (rightRunner != null) {
      if (!rightRunner.receiveNextPuzzle(puzzle)) {
        System.err.println("Right runner did not receive puzzle!");
      }
    }
  }

  public void registerLeftRunner (Runner runner) {
    leftRunner = runner;
  }

  public void registerRightRunner (Runner runner) {
    rightRunner = runner;
  }
}
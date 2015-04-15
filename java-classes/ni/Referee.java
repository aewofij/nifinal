package ni;

import com.cycling74.max.*;
import java.util.*;
import util.Tuple;

public class Referee extends MaxObject {

  final int kLeftOutlet = 0;
  final int kRightOutlet = 1;
  final int kGeneralOutlet = 2;

  enum Player {
    left, right
  };

  // singleton
  private static Referee instance;
  public static Referee getInstance() {
    if (instance == null) {
      instance = new Referee(new Atom[0]);
    }
    return instance;
  }

  private PuzzleSequence puzzles;
  private Runner leftRunner, rightRunner;
  private RefereeRunner refRunner;
  private Player completed = null;
  private boolean completedSuccessful;

  // `protected` because we don't want anyone to call this except `Referee`, above
  protected Referee (Atom[] args) {
    declareInlets(new int[] { DataTypes.ALL });
    declareOutlets(new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL });

    this.puzzles = new PuzzleSequence();
  }

  public void start () {
    System.out.println("Starting Referee...");

    leftRunner.reset();
    rightRunner.reset();

    puzzles.reset();
    sendPuzzle(puzzles.current());
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
        startTransition(success, completedSuccessful);
      }
      if (!isLeftPlayer && completed == Player.left) {
        startTransition(completedSuccessful, success);
      }
    }
  }

  public void registerLeftRunner (Runner runner) {
  	System.out.println("Registering leftRunner...");
    leftRunner = runner;
  }

  public void registerRightRunner (Runner runner) {
    System.out.println("Registering rightRunner...");
    rightRunner = runner;
  }

  public void registerRunner (RefereeRunner runner) {
    System.out.println("Registering ref runner...");
    refRunner = runner;
  }

  void punish () {
    refRunner.fire(kGeneralOutlet, "punish");
  }

  void startTransition (boolean leftSuccess, boolean rightSuccess) {
    if (leftSuccess && rightSuccess) {
      Tuple<AbstractPuzzle, AbstractPuzzle> oldPuzzles = puzzles.current();
      // Advance the sequence, checking if we're at the end.
      if (puzzles.next() != null) {
        fireTransition(oldPuzzles, true);
      } else {
        win();
      }
    } else {
      // TODO: fix this && business
      if (puzzles.current().fst.isRepeatable && puzzles.current().snd.isRepeatable) {
        // Immediately restart.
        endTransition();
      } else {
        Tuple<AbstractPuzzle, AbstractPuzzle> oldPuzzles = puzzles.current();
        // Advance the sequence, checking if we're at the end.
        if (puzzles.next() != null) {
          fireTransition(oldPuzzles, false);
        } else {
          win();
        }
      }
    }
  }

  void endTransition () {
    Tuple<AbstractPuzzle, AbstractPuzzle> currentPuzzles = puzzles.current();
    if (currentPuzzles != null) {
      sendPuzzle(currentPuzzles);
    } else {
      win();
    }
  }

  void fireTransition (Tuple<AbstractPuzzle, AbstractPuzzle> fromPuzzles, boolean isSuccess) {
    if (fromPuzzles != null) {
      String toLeft = isSuccess ? fromPuzzles.fst.successTransition
                                : fromPuzzles.fst.failureTransition;
      String toRight = isSuccess ? fromPuzzles.snd.successTransition
                                 : fromPuzzles.snd.failureTransition;

      if (toLeft != null) {
        refRunner.fire(kLeftOutlet,  "transition " + toLeft);
      } else {
        refRunner.endTransition(true);
      }

      if (toRight != null) {
        refRunner.fire(kRightOutlet, "transition " + toRight);
      } else {
        refRunner.endTransition(false);
      }
    }
  }

  void sendPuzzle (Tuple<AbstractPuzzle, AbstractPuzzle> puzzle) {
    AbstractPuzzle leftPuzzle = puzzle.fst;
    AbstractPuzzle rightPuzzle = puzzle.snd;

    System.out.println("Sending " + puzzle.fst + " to " + leftRunner + " and " + puzzle.snd+ " to " + rightRunner);

    if (leftRunner != null) {
      leftPuzzle.runner = leftRunner;
      if (!leftRunner.receiveNextPuzzle(leftPuzzle)) {
        System.err.println("Left runner did not receive puzzle!");
      }
    }
    if (rightRunner != null) {
      rightPuzzle.runner = rightRunner;
      if (!rightRunner.receiveNextPuzzle(rightPuzzle)) {
        System.err.println("Right runner did not receive puzzle!");
      }
    }
  }

  void win() {
    leftRunner.won();
    rightRunner.won();
    System.out.println("Everyone wins.");
  }

  public boolean getReadySetG() {
  	if ((leftRunner != null) && (rightRunner != null)) {
  		System.out.println("left and right ready");
  		return true;
  	}
  	else {
  		return false;
  	}
  }

}
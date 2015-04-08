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
        transition(success, completedSuccessful);
      }
      if (!isLeftPlayer && completed == Player.left) {
        transition(completedSuccessful, success);
      }
    }
  }

  public void registerLeftRunner (Runner runner) {
	System.out.println("Registering leftRunner...");
	// System.out.println("leftRunner: " + leftRunner);
	// System.out.println("rightRunner: " + rightRunner);
    leftRunner = runner;
	// System.out.println("leftRunner: " + leftRunner);
	// System.out.println("rightRunner: " + rightRunner);
  }

  public void registerRightRunner (Runner runner) {
	System.out.println("Registering rightRunner...");
	// System.out.println("leftRunner: " + leftRunner);
	// System.out.println("rightRunner: " + rightRunner);
    rightRunner = runner;
	// System.out.println("leftRunner: " + leftRunner);
	// System.out.println("rightRunner: " + rightRunner);
  }

  void punish () {
    outlet(kGeneralOutlet, Atom.newAtom("punish"));
  }

  void transition (boolean leftSuccess, boolean rightSuccess) {
    if (leftSuccess && rightSuccess) {
      System.out.println("1");
      Tuple<AbstractPuzzle, AbstractPuzzle> nextPuzzles = puzzles.next();
      if (nextPuzzles != null) {
        sendPuzzle(nextPuzzles);
      } else {
        win();
      }
    } else {
      // TODO: fix this && business
      if (puzzles.current().fst.isRepeatable && puzzles.current().snd.isRepeatable) {
        System.out.println("2");
        sendPuzzle(puzzles.current());
      } else {
        System.out.println("3");
        Tuple<AbstractPuzzle, AbstractPuzzle> nextPuzzles = puzzles.next();
        if (nextPuzzles != null) {
          sendPuzzle(nextPuzzles);
        } else {
          win();
        }
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
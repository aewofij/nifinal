package ni;

import com.cycling74.max.*;
import java.util.ArrayList;
import util.Tuple;

public class Runner extends MaxObject {
  public final int kRefereeOutlet = 0;
  public final int kPatchControlOutlet = 1;
  public final int kPartnerOutlet = 2;

  public enum State {
    inPuzzle, waiting, won
  };

  private State state;
  // Active puzzle.
  private AbstractPuzzle puzzle;
  private boolean isLeft;

  public Runner (Atom[] args) {
    declareInlets(new int[] { DataTypes.ALL });
    declareOutlets(new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL });

    if (args.length > 0) {
      if (args[0].getString().equals("left")) {
        Referee.getInstance().registerLeftRunner(this);
        isLeft = true;
        System.out.println("Initialized left ni.Runner.");
      } else if (args[0].getString().equals("right")) {
        Referee.getInstance().registerRightRunner(this);
        isLeft = false;
        System.out.println("Initialized right ni.Runner.");
      } else {
        System.err.println("Invalid arguments to ni.Runner.");
      }
    }

    state = State.waiting;
	// if (Referee.getInstance().getReadySetG()) {
		// Referee.getInstance().start();
	// }
  }

  public void finishedPuzzle (boolean success) {
    puzzle = null;
    state = State.waiting;
    Referee.getInstance().completed(isLeft, success);
  }

  /* Receives anything from Max inlet.
   */
  public void anything (String msg, Atom[] args) {
    if (state == State.waiting) {
      // do nothing
    } else if (state == State.inPuzzle) {
      // forward input to puzzle
      puzzle.receiveInput(concatArgs(msg, args));
    }
  }

  // turns args into single string
  String concatArgs (String msg, Atom[] args) {
    return msg + " " + Atom.toOneString(args);
  }

  // Fires a message out patch control outlet.
  public void firePatchControl (String msg) {
    outlet(kPatchControlOutlet, Atom.newAtom(msg));
  }
  
  // Fires a message out runner outlet to partner runner inlet.
  public void fireAtOtherRunner (String msg) {
    outlet(kPartnerOutlet, Atom.newAtom(msg));
  }

  // Returns true if successfully set puzzle, else false.
  public boolean receiveNextPuzzle (AbstractPuzzle nextPuzzle) {
    System.out.println("Received puzzle " + (this.isLeft ? "left" : "right"));

    if (state == State.waiting) {
      puzzle = nextPuzzle;
      puzzle.start();
      state = State.inPuzzle;
      return true;
    }
    // if `state` is not `waiting`, can't set puzzle
    return false;
  }

  public void won () {
    this.state = State.won;
  }

  public void reset () {
    if (this.puzzle != null) {
      this.puzzle.end();
    }
    this.state = State.waiting;
  }
  
  public boolean getIdentity() {
    return isLeft;
  }
}
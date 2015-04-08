package ni;

import com.cycling74.max.*;
import java.util.ArrayList;
import util.Tuple;

public class Runner extends MaxObject {
  public final int kRefereeOutlet = 0;
  public final int kPatchControlOutlet = 1;

  public enum State {
    inPuzzle, finished
  };

  private State state;
  // Active puzzle.
  private AbstractPuzzle puzzle;
  private boolean isLeft;

  public Runner (Atom[] args) {
    declareInlets(new int[] { DataTypes.ALL });
    declareOutlets(new int[] { DataTypes.ALL, DataTypes.ALL });

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

    state = State.finished;
  }

  public void finishedPuzzle (boolean success) {
    puzzle = null;
    state = State.finished;
    fireReferee(success ? "success" : "failure");
  }

  /* Receives anything from Max inlet.
   */
  public void anything (String msg, Atom[] args) {
    if (state == State.finished) {
      // do nothing
    } else if (state == State.inPuzzle) {
      // forward input to puzzle
      puzzle.receiveInput(msg);
    }
  }

  // Fires a message out patch control outlet.
  public void firePatchControl (String msg) {
    outlet(kPatchControlOutlet, Atom.newAtom(msg));
  }

  // Fires a message out referee communication outlet.
  // Always prepends message with 'left'/'right'.
  public void fireReferee (String msg) {
    outlet(kRefereeOutlet, 
           Atom.newAtom((isLeft 
                         ? "left"
                         : "right") + msg));
  }

  // Returns true if successfully set puzzle, else false.
  public boolean receiveNextPuzzle (AbstractPuzzle puzzle) {
    System.out.println("Received puzzle " + (this.isLeft ? "left" : "right"));

    if (state == State.finished) {
      puzzle = puzzle;
      puzzle.start();
      return true;
    }
    // if `state` is not finished, can't set puzzle
    return false;
  }
}
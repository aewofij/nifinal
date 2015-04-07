package ni;

/*
State of interacting with a single puzzle; receives user input pushed 
from `SceneState`, transitioning to new state.
*/

import util.Tuple;
import util.ArrayUtil;

public class PuzzleState {

  public static enum TransitionMode {
    start,      // no user input yet
    step,       // user just entered correct input
    wrong,      // user just entered incorrect input
    completed   // puzzle is completed
  }

  private Puzzle _puzzle;
  private InputSequence _pastUserInput;
  private TransitionMode _mode;

  // Starts a fresh PuzzleState.
  public static PuzzleState start (Puzzle puzzle) {
    return new PuzzleState(puzzle, InputSequence.empty, TransitionMode.start);
  }

  public PuzzleState (Puzzle puzzle, InputSequence pastUserInput, PuzzleState.TransitionMode mode) {
    _puzzle = puzzle;
    _pastUserInput = pastUserInput;
    _mode = mode;
  }

  /* Gets the `Puzzle` which this state is talking about.
   */
  public Puzzle puzzle () {
    return _puzzle;
  }

  /* Gets what input events the user has already input.
   */
  public InputSequence pastUserInput () {
    return _pastUserInput;
  }

  /* Gets the mode of the most recent transition.
   */
  public TransitionMode transitionMode () {
    return _mode;
  } 

  /* Transitions from old state `from` on event `evt`.
   * Returns the new state.
   */
  public static Tuple<PuzzleState, IExternalEvent[]> transition (PuzzleState from, IUserInputEvent evt) {
    InputSequence updatedSeq = InputSequence.push(evt, from.pastUserInput());
    if (from.puzzle().answer().hasPrefix(updatedSeq)) {
      // correct input
      // TODO: actual effects
      // IExternalEvent[] effects = new IExternalEvent[] { new OscExternalEvent("correct!") };
  	  // IExternalEvent[] effects = new IExternalEvent[] { new JitterExternalEvent("correct!") };

      IExternalEvent[] effects = from.puzzle().onStep();

      TransitionMode tMode = TransitionMode.step;
      if (updatedSeq.count() == from.puzzle().answer().count()) {
        tMode = TransitionMode.completed;
        effects = ArrayUtil.concat(effects, from.puzzle().onComplete());
      }

      return new Tuple<PuzzleState, IExternalEvent[]>(new PuzzleState(from.puzzle(), updatedSeq, tMode), effects);
    } else {
      // wrong move, suckah!
      // TODO: actual effects
      // IExternalEvent[] effects = new IExternalEvent[] { new OscExternalEvent("wrong!") };
      IExternalEvent[] effects = from.puzzle().onWrong();

      // returning old state for now
      return new Tuple<PuzzleState, IExternalEvent[]>(new PuzzleState(from.puzzle(), from.pastUserInput(), TransitionMode.wrong), effects);
    }
  }
}
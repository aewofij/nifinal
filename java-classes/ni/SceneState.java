package ni;

import util.Tuple;
import util.ArrayUtil;

/*
State of the piece; receives user input and propagates into puzzles; 
moves from puzzle to puzzle; sends events out to trigger video / other actions.
*/

public class SceneState {
  
  private Puzzle[] _puzzleSequence;
  private PuzzleState _puzzleState;

  /* Constructs a new SceneState with the provided puzzles, 
   *   starting at the beginning of the first puzzle.
   */
  public SceneState (Puzzle[] puzzles) {
    if (puzzles.length > 0) {
      _puzzleSequence = puzzles;
      _puzzleState = PuzzleState.start(puzzles[0]);
    } else {
      System.err.println("Attempted to initialize scene with empty puzzle list.");
      return;
    }
  }

  public SceneState (Puzzle[] puzzles, PuzzleState puzzleState) {
    _puzzleSequence = puzzles;
    _puzzleState = puzzleState;
  }

  public static SceneState finished = new SceneState(new Puzzle[0], null);

  /* Gets the current state of the current puzzle.
   */
  public PuzzleState puzzleState () {
    return _puzzleState;
  }

  /* Gets the scene's puzzle sequence.
   */
  public Puzzle[] puzzleSequence () {
    return _puzzleSequence;
  }

  /* Transitions from old state `from` on event `evt`.
   * Returns a tuple of the new state, and a list of `ExternalEvent` triggered by the transition.
   */
  public static Tuple<SceneState, IExternalEvent[]> transition (SceneState from, IUserInputEvent evt) {
    Tuple<PuzzleState, IExternalEvent[]> puzzleTransition = PuzzleState.transition(from.puzzleState(), evt);

    PuzzleState puzzleState = puzzleTransition.fst;
    IExternalEvent[] events = puzzleTransition.snd;
    PuzzleState.TransitionMode tMode = puzzleState.transitionMode();
    Puzzle[] puzzleSequence = from.puzzleSequence();
    if (tMode == PuzzleState.TransitionMode.completed) {
      puzzleState = PuzzleState.start(from.puzzleSequence()[0]);
      events = ArrayUtil.concat(events, puzzleState.puzzle().onStart());
      puzzleSequence = java.util.Arrays.copyOfRange(from.puzzleSequence(), 1, from.puzzleSequence().length);
    }

    if (from.puzzleSequence().length > 0) {
      return new Tuple<SceneState, IExternalEvent[]>(new SceneState(puzzleSequence, puzzleState), events);
    } else {
      return new Tuple<SceneState, IExternalEvent[]>(SceneState.finished, events);
    }

  }

}

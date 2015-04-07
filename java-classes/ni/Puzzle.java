package ni;

/*
Data container for a puzzle, with external events 
that are triggered during events in puzzle's lifecycle.
*/

public class Puzzle {

  private InputSequence _answer;
  private IExternalEvent[] _onStart;
  private IExternalEvent[] _onComplete;
  private IExternalEvent[] _onStep;
  private IExternalEvent[] _onWrong;
  private java.util.Map<Integer, IExternalEvent[]> _onInputElement;

  // creates a Puzzle with no external events
  public Puzzle (InputSequence answer) {
    this(answer, 
         new IExternalEvent[0], 
         new IExternalEvent[0], 
         new IExternalEvent[0],
         new IExternalEvent[0],
         new java.util.HashMap<Integer, IExternalEvent[]>());
  }

  public Puzzle (InputSequence answer, 
                 IExternalEvent[] onStart, IExternalEvent[] onComplete, 
                 IExternalEvent[] onStep, IExternalEvent[] onWrong, 
                 java.util.Map<Integer, IExternalEvent[]> onInputElement) {
    _answer = answer;
    _onStart = onStart;
    _onComplete = onComplete;
    _onStep = onStep;
    _onWrong = onWrong;
    _onInputElement = onInputElement;
  }

  /* Gets the correct sequence of inputs for this puzzle.
   */
  public InputSequence answer () {
    return _answer;
  }

  /* Gets the event queue to be triggered when this puzzle is started.
   */
  public IExternalEvent[] onStart () {
    return _onStart;
  }

  /* Gets the event queue to be triggered when this puzzle is successfully completed.
   */
  public IExternalEvent[] onComplete () {
    return _onComplete;
  }

  /* Gets the event queue to be triggered on a correct input.
   */
  public IExternalEvent[] onStep () {
    return _onStep;
  }

  /* Gets the event queue to be triggered on a wrong input.
   */
  public IExternalEvent[] onWrong () {
    return _onWrong;
  }

  /* Gets the event queue to be triggered on the specified event index.
   */
  public IExternalEvent[] onInputElement (int index) {
    IExternalEvent[] result = _onInputElement.get(index);
    if (result == null) {
      return new IExternalEvent[0];
    } else {
      return result;
    }
  }

}
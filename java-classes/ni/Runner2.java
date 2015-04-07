package ni;

/*
Connects everything together; interfaces with communicator; 
maintains current `SceneState`; effects transition on 
received input in event loop.
*/

import com.cycling74.max.*;
import java.util.ArrayList;
import util.Tuple;

public class Runner2 extends MaxObject {

  // Just so we can keep the `MaxObject` stuff in one class
  public class MaxCommunicator implements ICommunicator {
    private final int BUFFER_LENGTH = 100;
    private ArrayList<String> readBuffer;

    public MaxCommunicator () {
      readBuffer = new ArrayList<String>();
    }

    /* Sends event list out; sending event at top of list first.
     */
    public void send (IExternalEvent[] events) {
      for (int i = 0; i < events.length; i++) {
        outlet(0, Atom.newAtom(events[i].identifier()));
      }
    }

    /* TODO: convert Max messages into symbolic representation
     * Reads latest events; earlier events are at the top of the list.
     */
    public IUserInputEvent[] read () {
      IUserInputEvent[] result = new IUserInputEvent[readBuffer.size()];
      for (int i = 0; i < result.length; i++) {
        // if starts with "text"... etc, make relevant event

        // result[i] = new KeyboardInputEvent(readBuffer.get(i));
      }

      readBuffer.clear();
      return result;
    }

    /* Receives anything from Max inlet; 
     *   pushes into this object's read buffer.
     */
    public void push (String msg) {
      System.out.println("Pushing " + msg);
      readBuffer.add(msg);
    }
  }

  // ----- Interface with Max ----- //

  MaxCommunicator com;
  SceneState state;

  public Runner2 (Atom[] args) {
    System.out.println("Initializing ni.Runner2...");
    com = new MaxCommunicator();
  }

  public void setup() {
    // Generate puzzles; setup state.
    IPuzzleGenerator puzzleGenerator = new SimplePuzzleGenerator();
    state = new SceneState(puzzleGenerator.generate(10));
  }

  void step () {
    if (state != SceneState.finished) {
      IUserInputEvent[] incoming = com.read();
      for (IUserInputEvent evt : incoming) {

        // if we're in a puzzle,
        // forward all events to the puzzle

        // check state of puzzle
        // if puzzle is finished, do something
        // if not, loop and wait for input again

        // Transition state.

        // if we're starting a puzzle,
        // start the puzzle
        // wait for puzzle to finish

        System.out.println("state : " + state);
        System.out.println("event : " + evt);

        Tuple<SceneState, IExternalEvent[]> transition = SceneState.transition(state, evt);
        state = transition.fst;

        // Send out any side effects (external events) that occurred from last transition.
        IExternalEvent[] effects = transition.snd;
        com.send(effects); 
      }
    }
  }

  /* Receives anything from Max inlet; 
   *   pushes by character into this object's read buffer.
   */
  public void anything (String msg, Atom[] args) {
    System.out.println("anything()");
    com.push(msg);
    step();
  }


  public static void main (String[] args) {
    Runner2 runner = new Runner2(new Atom[]{});
    runner.setup();
  }

}
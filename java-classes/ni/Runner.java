package ni;

/*
Connects everything together; interfaces with communicator; 
maintains current `SceneState`; effects transition on 
received input in event loop.
*/

import com.cycling74.max.*;
import java.util.ArrayList;
import util.Tuple;

public class Runner extends MaxObject {

  // Just so we can keep the `MaxObject` stuff in one class
  public class MaxCommunicator implements ICommunicator {
    private final int BUFFER_LENGTH = 100;
    private ArrayList<Character> readBuffer;

    public MaxCommunicator () {
      readBuffer = new ArrayList<Character>();
    }

    /* Sends event list out; sending event at top of list first.
     */
    public void send (IExternalEvent[] events) {
      for (int i = 0; i < events.length; i++) {
        outlet(0, Atom.newAtom(events[i].identifier()));
      }
    }

    /* Reads latest events; earlier events are at the top of the list.
     */
    public IUserInputEvent[] read () {
      IUserInputEvent[] result = new IUserInputEvent[readBuffer.size()];
      for (int i = 0; i < result.length; i++) {
        result[i] = new KeyboardInputEvent(readBuffer.get(i));
      }

      readBuffer.clear();

      return result;
    }

    /* Receives anything from Max inlet; 
     *   pushes by character into this object's read buffer.
     */
    public void push (String msg) {
      System.out.println("Pushing " + msg);
      char[] charArray = msg.toCharArray();
      for (char c : charArray) {
        readBuffer.add(c);
      }
    }
  }

  // ----- Interface with Max ----- //

  MaxCommunicator com;
  SceneState state;

  public Runner (Atom[] args) {
    System.out.println("Initializing ni.Runner...");
    com = new MaxCommunicator();
  }

  public void setup() {
    // Generate puzzles; setup state.
    IPuzzleGenerator puzzleGenerator = new SimplePuzzleGenerator();
    state = new SceneState(puzzleGenerator.generate(10));

    // Setup communication; prime incoming event queue.
    // IUserInputEvent[] incoming = com.read();
    // System.out.println("finished setup");
    // while (state != SceneState.finished) {
    //   System.out.println("in while");

    //   for (IUserInputEvent evt : incoming) {
    //     // Transition state.
    //     Tuple<SceneState, IExternalEvent[]> transition = SceneState.transition(state, evt);
    //     state = transition.fst;

    //     // Send out any side effects (external events) that occurred from last transition.
    //     IExternalEvent[] effects = transition.snd;
    //     com.send(effects); 
    //   }

    //   // Wait for next input events.
    //   do {
    //     System.out.println("loop read");
    //     incoming = com.read();
    //   } while (incoming == null);
    // }
  }

  void step () {
    if (state != SceneState.finished) {
      IUserInputEvent[] incoming = com.read();
      for (IUserInputEvent evt : incoming) {
        // Transition state.

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
    Runner runner = new Runner(new Atom[]{});
    runner.setup();

    // // Generate puzzles; setup state.
    // IPuzzleGenerator puzzleGenerator = new SimplePuzzleGenerator();
    // SceneState state = new SceneState(puzzleGenerator.generate(10));

    // // Setup communication; prime incoming event queue.
    // ICommunicator com = new CommandLineCommunicator();
    // IUserInputEvent[] incoming = com.read();
    // while (state != SceneState.finished) {

    //   for (IUserInputEvent evt : incoming) {
    //     // Transition state.
    //     Tuple<SceneState, IExternalEvent[]> transition = SceneState.transition(state, evt);
    //     state = transition.fst;

    //     // Send out any side effects (external events) that occurred from last transition.
    //     IExternalEvent[] effects = transition.snd;
    //     com.send(effects); 
    //   }

    //   // Wait for next input events.
    //   do {
    //     incoming = com.read();
    //   } while (incoming != null);
    // }
  }

}
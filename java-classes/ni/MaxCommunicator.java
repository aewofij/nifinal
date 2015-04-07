package ni;

/*
Reads from input devices; sends out `ExternalEvent`s to Max.
*/

import com.cycling74.max.*;
import java.util.ArrayList;

public class MaxCommunicator extends MaxObject implements ICommunicator {

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
    if (readBuffer.size() == 0) {
      return null;
    }

    IUserInputEvent[] result = new IUserInputEvent[readBuffer.size()];
    for (int i = 0; i < result.length; i++) {
      result[i] = new KeyboardInputEvent(readBuffer.get(i));
    }

    readBuffer.clear();

    return result;
  }


  // ----- Interface with Max ----- //

  /* Receives anything from Max inlet; 
   *   pushes by character into this object's read buffer.
   */
  public void anything (String msg, Atom[] args) {
    char[] charArray = msg.toCharArray();
    for (char c : charArray) {
      readBuffer.add(c);
    }
  }
  
}
package ni;

/*
Implementation of ICommunicator that reads and writes to command line.
User events are instances of KeyboardInputEvent.
*/

import java.util.Scanner;

public class CommandLineCommunicator implements ICommunicator {

  private Scanner scanner;

  public CommandLineCommunicator () {
    this.scanner = new Scanner(System.in);
  }

  /* Sends event list out; sending event at top of list first.
   */
  public void send (IExternalEvent[] events) {
    for (IExternalEvent evt : events) {
      System.out.println(evt.identifier());
    }
  }

  /* Reads latest events; earlier events are at the top of the list.
   */
  public IUserInputEvent[] read () {
    String str = scanner.nextLine();
    char[] charArray = str.toCharArray();
    KeyboardInputEvent[] evts = new KeyboardInputEvent[charArray.length];
    // god java is annoying
    for (int i = 0; i < charArray.length; i++) {
      evts[i] = new KeyboardInputEvent(charArray[i]);
    }

    return evts;
  }

}
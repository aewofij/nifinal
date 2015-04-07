package ni;

/*
Sequence of `UserInputEvent`s, with some convenience methods.
*/

public class InputSequence {

  private IUserInputEvent[] eventList;

  public static InputSequence empty = new InputSequence (new IUserInputEvent[0]);

  public InputSequence (IUserInputEvent[] seq) {
    this.eventList = seq;
  }

  /* Checks if this `InputSequence` is prefixed by the InputSequence `prefix`.
   */
  public boolean hasPrefix (InputSequence prefix) {
    if (prefix.asEventList().length <= this.asEventList().length) {
      for (int i = 0; i < prefix.asEventList().length; i++) {
        if (!prefix.asEventList()[i].equals(this.asEventList()[i])) {
          return false;
        }
      }

      // we got through, so it's true!
      return true;
    } else {
      // "prefix" is longer than this sequence
      return false;
    }
  }

  /* Returns a view of this InputSequence as an array of `UserInputEvent`.
   */
  public IUserInputEvent[] asEventList () {
    return eventList;
  }

  /* Gets number of events in this sequence.
   */
  public int count () {
    return eventList.length;
  }
 
  /* Pushes `evt` onto the InputSequence `onto`.
   * Returns the new InputSequence.
   */
  public static InputSequence push (IUserInputEvent evt, InputSequence onto) {
    IUserInputEvent[] evtList = new IUserInputEvent[onto.asEventList().length + 1];
    for (int i = 0; i < onto.asEventList().length; i++) {
      evtList[i] = onto.asEventList()[i];
    }
    evtList[onto.asEventList().length] = evt;

    return new InputSequence(evtList);
  }

  @Override
  public boolean equals (Object o) {
    // TODO
    return false;
  }

  @Override
  public String toString () {
    String result = "[ ";
    for (int i = 0; i < eventList.length; i++) {
      result += eventList[i].toString();
      if (i != eventList.length - 1) {
        result += ", ";
      }
    }
    result += " ]";

    return result;
  }

}
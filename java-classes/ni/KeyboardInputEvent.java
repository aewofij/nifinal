package ni;

/*
Keyboard press implemention of `IUserInputEvent`.
*/

public class KeyboardInputEvent implements IUserInputEvent {
  private char myKey;

  public KeyboardInputEvent (char key) {
    this.myKey = key;
  }

  public char key () {
    return myKey;
  }

  @Override
  public String toString () {
    return "press " + myKey;
  }

  @Override 
  public boolean equals (Object o) {
    if (o instanceof KeyboardInputEvent) {
      KeyboardInputEvent kie = (KeyboardInputEvent)o;
      return kie.key() == this.key();
    } else {
      return false;
    }
  }
}
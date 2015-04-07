package ni;

/*
OSC message implemention of `IExternalEvent`.
*/

public class OscExternalEvent implements IExternalEvent {
  private final String myIdentifier;

  public OscExternalEvent (String id) {
    this.myIdentifier = id;
  }

  public String identifier () {
    return myIdentifier;
  }

  @Override
  public String toString () {
    return myIdentifier;
  }
}
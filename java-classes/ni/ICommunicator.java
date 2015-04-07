package ni;

/*
Reads from input devices; sends out `ExternalEvent`s.
*/
public interface ICommunicator {

  /* Sends event list out; sending event at top of list first.
   */
  public void send (IExternalEvent[] events);

  /* Reads latest events; earlier events are at the top of the list.
   */
  public IUserInputEvent[] read ();
  
}
package ni;

import com.cycling74.max.*;

public class RefereeRunner extends MaxObject {

  private Referee ref;

  public RefereeRunner(Atom[] args) {
    ref = Referee.getInstance();
    // TODO: why isn't this working? need it for ref to speak to patch
    ref.registerRunner(this);

    declareInlets(new int[] { DataTypes.ALL });
    declareOutlets(new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL });
  }

  public void start () {
    ref.start();
  }

  
  public void anything (String msg, Atom[] args) {
    String message = concatArgs(msg, args);

    if (message.equals("endtransition left")) {
      endTransition(true);
    } else if (message.equals("endtransition right")) {
      endTransition(false);
    }
  }

  // flags if received endtransition message from either runner
  private boolean gotLeft = false;
  private boolean gotRight = false;
  public void endTransition(boolean isLeft) {
    if (isLeft) {
      gotLeft = true;
    } else {
      gotRight = true;
    }

    if (gotRight && gotLeft) {
      gotRight = gotLeft = false;
      ref.endTransition();
    }
  }

  public void fire(int outletIndex, String message) {
    outlet(outletIndex, Atom.newAtom(message));
  }

  // turns args into single string
  String concatArgs (String msg, Atom[] args) {
    return msg + " " + Atom.toOneString(args);
  }

}
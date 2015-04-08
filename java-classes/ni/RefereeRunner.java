package ni;

import com.cycling74.max.*;

public class RefereeRunner extends MaxObject {

  private Referee ref;

  public RefereeRunner(Atom[] args) {
    ref = Referee.getInstance();
    // TODO: why isn't this working? need it for ref to speak to patch
    // ref.registerRunner(this);

    declareInlets(new int[] { DataTypes.ALL });
    declareOutlets(new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL });
  }

  public void start () {
    ref.start();
  }

  public void fire(int outletIndex, String message) {
    outlet(outletIndex, Atom.newAtom(message));
  }
}
package ni;

public class AnsMayVaryOnewayPuzzle extends AbstractPuzzle {

  // Does this puzzle restart on failure?
  public boolean isRepeatable = false;

  private boolean isPrimed = false;
  // private boolean isReceptive;
  private boolean active;
  private String videoFile;
  private String solution;
  
  public AnsMayVaryOnewayPuzzle (String videoFile) {
    this.videoFile = videoFile;
	this.active = false;
  }

  // External object calls this to begin puzzle operation.
  public void start () {
    this.active = true;
    if (this.runner != null) {
	  if (this.runner.getIdentity()) { //if isLeft
	    // this.runner.anything("/solution/ ");
		// isReceptive = true;
	  }
      // this.runner.firePatchControl("loadvideo " + videoFile);
    }
  }
  
  public void end () {
    this.active = false;
  }

  // Receives real-time user events - button presses, video markers, other runner data...
  public void receiveInput (String event) {
    // if (isReceptive) {
		String eventsub = event.substring(10);
		if (this.runner.getIdentity()) { //if isLeft (person who creates answer key)
			// System.out.println("left receiving input");
			solution = eventsub;
			// System.out.println("solution length: " + solution.length());
			// System.out.println("set left player's sol to: " + solution);
			// System.out.println("event length: " + event);
			String sol = "solution " + solution;
			// System.out.println("sending: " + sol);
			this.runner.fireAtOtherRunner(sol);
		}
		else { //if is right player
			// System.out.println("right receiving input");
			// System.out.println("substr: " + event.substring(0, 8));
			String substr = event.substring(0, 8);
			if (substr.equals("solution")) { //if input from left player
			  String substrsol = event.substring(9, event.length()-1);
			  // System.out.println("event length: " + event);
			  solution = substrsol;
			  // System.out.println("set right player's sol to: " + solution);
			  // System.out.println("right player's sol's length: " + solution.length());
			}
			else { //if input from right player
			  String substrsol2 = event.substring(10);
			  // System.out.println("right's sol: " + solution);
			  // System.out.println("substrsol2: " + substrsol2);
			  // System.out.println("right's sol's length: " + solution.length());
			  // System.out.println("substrsol2's length: " + substrsol2.length());
			  if (eventsub.equals(solution)) {
				super.successful();
				System.out.println("success");
			  }
			  else {
			    super.failure();
				System.out.println("fail");
			  }
			}
		} 
	// }
  }
}
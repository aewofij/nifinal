import ni.AbstractPuzzle;

package ni {
  class TestMultipleChoicePuzzle() extends AbstractPuzzle with MultipleChoicePuzzle {

    override val responses: Map[Button, Some[String]] = Map(RedButton()    -> Some("Wrong!"),
                        BlueButton()   -> Some("Wrong!"),
                        YellowButton() -> Some("Right!"))
    override var correctResponse: Option[Button] = Some(YellowButton())

    // Called at start of puzzle.
    def start(): Unit = {
      isActive = true;
      System.out.println("responses");
      responses foreach System.out.println
    }

    // Called at end of puzzle. Any teardown goes here.
    def end(): Unit = {
      System.out.println("ending");
    }

    // // Receives real-time user events - button presses, video markers, other runner data...
    // def receiveInput(event: String): Unit = {
    //   val strOut = "received event " + event;
    //   System.out.println(strOut);
    // }
  }


  trait MultipleChoicePuzzle extends AbstractPuzzle {
    sealed abstract class Button
    case class RedButton()    extends Button
    case class BlueButton()   extends Button
    case class YellowButton() extends Button

    // response strings to be shown on screen
    val responses: Map[Button, Option[String]]
    // button corresponding to correct answer
    var correctResponse: Option[Button]

    var isActive = false;

    def receiveInput(event: String): Unit = {
      if (this.isActive) {
        (toButtonPress(event), correctResponse) match {
          case (Some(button), Some(answer)) => respondToButton(button, answer)
          case (_, _)                       => () // unknown event or no correct answer
        }
      }
    }

    private def respondToButton(button: Button, answerButton: Button) = button match {
      case `answerButton` => System.out.println("you got it!"); super.successful()
      case _              => super.failure()
    }

    private def toButtonPress(eventString: String): Option[Button] = eventString match {
      case "button red"    => Some(RedButton())
      case "button blue"   => Some(BlueButton())
      case "button yellow" => Some(YellowButton())
      case _               => None
    }
  
  }

}
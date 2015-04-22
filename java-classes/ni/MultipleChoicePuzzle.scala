import ni.AbstractPuzzle;

package ni {
  class GenericMultipleChoicePuzzle(override var mainText: String,
                                    override var choices: Map[Button, Option[String]],
                                    override var correctChoice: Option[Button]) 
      extends AbstractPuzzle with MultipleChoicePuzzle {

    override var isActive = false
    val drawer = new ni.PuzzleDrawer(this)

    // Called at start of puzzle.
    def start(): Unit = {
      isActive = true;

      drawer.reset()
            .setBackgroundColor("white")
            .setMainText(mainText)
            .setChoicesText(this.choices)
    }

    // Called at end of puzzle. Any teardown goes here.
    def end(): Unit = {
      isActive = false;
      // drawer.setBackgroundColor("black");
    }
  }

  object GenericMultipleChoicePuzzle {
    def make(text: String, successTransition: String, failureTransition: String, 
             correctChoice: Button, redChoice: String, blueChoice: String, 
             yellowChoice: String, isRepeatable: Boolean): GenericMultipleChoicePuzzle = {
      var choiceMap = Map[Button, Option[String]](RedButton()    -> None, 
                                                  BlueButton()   -> None, 
                                                  YellowButton() -> None)
      if (redChoice != null) {
        choiceMap = choiceMap + (RedButton() -> Some(redChoice))
      }
      if (blueChoice != null) {
        choiceMap = choiceMap + (BlueButton() -> Some(blueChoice))
      }
      if (yellowChoice != null) {
        choiceMap = choiceMap + (YellowButton() -> Some(yellowChoice))
      }
      var result = new GenericMultipleChoicePuzzle(text, choiceMap, Some(correctChoice))
      result.successTransition = successTransition
      result.failureTransition = failureTransition
      result.isRepeatable = isRepeatable
      return result
    }
  }


  class PressButtonPuzzle(var toPress: Button) 
      extends GenericMultipleChoicePuzzle("", Map(), Some(toPress)) {
    val buttonString = toPress match {
      case RedButton()    => "red"
      case BlueButton()   => "blue"
      case YellowButton() => "yellow"
    }
    mainText = "Press the " + buttonString + " button."

    // Called at start of puzzle.
    override def start(): Unit = {
      isActive = true;

      drawer.reset()
            .setBackgroundColor(buttonString)
            .setMainText(mainText)

      System.out.println("starting " + (if (this.runner.isLeft) "left" else "right") 
                         + " isActive? " + isActive);
    }
  }

  object PressButtonPuzzle {
    def make(toPress: Button, successTransition: String, 
             failureTransition: String, isRepeatable: Boolean): PressButtonPuzzle = {
      val result = new PressButtonPuzzle(toPress)
      result.successTransition = successTransition
      result.failureTransition = failureTransition
      result.isRepeatable = isRepeatable
      return result
    }
  }


  trait MultipleChoicePuzzle extends AbstractPuzzle {
    // main text of question / puzzle
    var mainText: String
    // response strings to be shown on screen
    var choices: Map[Button, Option[String]]
    // button corresponding to correct answer
    var correctChoice: Option[Button]

    var isActive: Boolean

    def receiveInput(event: String): Unit = {
      System.out.println("receiveInput " + (if (this.runner.isLeft) "left" else "right") 
                         + " " + event + " isActive? " + isActive);
      if (isActive) {
        (toButtonPress(event), correctChoice) match {
          case (Some(button), Some(answer)) => respondToButton(button, answer)
          case _                            => () // unknown event or no correct answer
        }
      } 
    }

    def setChoice(button: Button, text: String): Unit = {
      choices = choices + (button -> Some(text))
    }

    def setCorrectChoice(button: Button): Unit = button match {
      case null => correctChoice = None
      case _    => correctChoice = Some(button)
    }

    private def respondToButton(button: Button, answerButton: Button) = button match {
      case `answerButton` => super.successful()
      case _              => super.failure()
    }

    private def toButtonPress(eventString: String): Option[Button] = eventString match {
      case "ctrl button red"    => Some(RedButton())
      case "ctrl button blue"   => Some(BlueButton())
      case "ctrl button yellow" => Some(YellowButton())
      case _                    => None
    }
  }

}
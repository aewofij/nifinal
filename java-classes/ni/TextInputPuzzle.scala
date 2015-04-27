import ni.AbstractPuzzle;

package ni {

  class GenericTextInputPuzzle(override var mainText: String,
                                 val correctPatternStr: Option[String])
    extends AbstractPuzzle with TextInputPuzzle {
    
    override var isActive = false
    override var correctPattern = correctPatternStr.map((pattern) => pattern.r)

    def start(): Unit = {
      // System.out.println("Starting " + this);
      isActive = true

      drawer.reset()
            .setBackgroundColor("white")
            .setMainText(mainText)
            // show text box?
    }

    // Called at end of puzzle. Any teardown goes here.
    def end(): Unit = {
      // System.out.println("Ending " + this);
      isActive = false;
      drawer.reset();
    }
  }

  object GenericTextInputPuzzle {
    def make(mainText: String, correctPattern: String, 
             successTransition: String, failureTransition: String,
             isRepeatable: Boolean): GenericTextInputPuzzle = {
      var correctResponseOpt = if (correctPattern == null) None
                               else Some(correctPattern)

      var result = new GenericTextInputPuzzle(mainText, correctResponseOpt)
      result.successTransition = successTransition
      result.failureTransition = failureTransition
      result.isRepeatable = isRepeatable
      return result
    }
  }


  trait TextInputPuzzle extends AbstractPuzzle {
    // main text of question / puzzle
    var mainText: String
    // correct answer string
    var correctPattern: Option[scala.util.matching.Regex]

    val drawer = new ni.PuzzleDrawer(this)
    var isActive: Boolean

    // string being built by user
    var workingString: String = ""

    sealed abstract class KeyEvent
    case class CharacterKey(char: String) extends KeyEvent
    case class Backspace() extends KeyEvent
    case class Return() extends KeyEvent

    override def receiveInput(event: String): Unit = {
      if (isActive) {
        // System.out.println("received input " + event);
        toKeyPress(event) match {
          case Some(CharacterKey(c)) => workingString = workingString ++ c; drawer.setResponseText(Some(workingString))
          case Some(Backspace())     => workingString = workingString dropRight 1; drawer.setResponseText(Some(workingString))
          case Some(Return())        => submit(workingString.trim())
          case None                  => ()
        }
      }
    }

    // sets the regex for a correct answer
    def setCorrectPattern(pattern: String): Unit = {
      this.correctPattern = 
        if (pattern == null) None
        else Some(pattern.r)
    }

    private def submit(response: String): Unit = (correctPattern, response) match {
      case (Some(patt), patt(_*)) => System.out.println("success!"); super.successful()
      case _                      => System.out.println("failure!"); super.failure()
    }

    private def toKeyPress(evt: String): Option[KeyEvent] = {
      val keySelector = "key "
      val charSelector = "char "
      if (evt startsWith keySelector) {
        (evt drop keySelector.length) match {
          case "backspace" => Some(Backspace())
          case "return" => Some(Return())
          case ks if ks startsWith charSelector =>
            Some(CharacterKey(ks drop charSelector.length))
          case _ => None
        }
      } else None
    }
  }
}
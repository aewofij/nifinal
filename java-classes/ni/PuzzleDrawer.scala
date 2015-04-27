package ni {
  class PuzzleDrawer(puzzle: AbstractPuzzle) {
    def setBackgroundColor(red: Double, blue: Double, green: Double): PuzzleDrawer = {
      fire("background", red.toString, blue.toString, green.toString)
    }

    def setBackgroundColor(color: String): PuzzleDrawer = {
      fire("background", color)
    }

    def setMainText(text: String): PuzzleDrawer = {
      if (text == null || text.length == 0) {
        fire("maintext", "none")
      } else {
        fire("maintext", "string", text)
      }
    }

    def setChoicesText(choices: Map[Button, Option[String]]): PuzzleDrawer = {
      val optionToEmpty = (optStr: Option[String]) => optStr match { 
        case Some(s) => "string " ++ s
        case None    => "none"
      }

      choices.foreach({ 
        case (RedButton(), text)    => fire("choice", "red", optionToEmpty(text))
        case (BlueButton(), text)   => fire("choice", "blue", optionToEmpty(text))
        case (YellowButton(), text) => fire("choice", "yellow", optionToEmpty(text))
        case _                      => ()
      })
      this
    }

    def setImageURL(imageURL: String): PuzzleDrawer = {
      // TODO
      this
    }

    def setResponseText(text: Option[String]): PuzzleDrawer = text match {
      case Some(str) if str.length > 0 => fire("textinput", "string", str)
      case _                           => fire("textinput", "none")
    }

    def reset() = {
      setBackgroundColor("white")
      setMainText(null)
      setChoicesText(Map(RedButton()    -> None, 
                         BlueButton()   -> None, 
                         YellowButton() -> None))
      setResponseText(None)
    }

    private def fire(msg: String*): PuzzleDrawer = {
      val outMsg = "drawpuzzle " + interject(msg.toList, " ").fold("")((a, b) => a + b)
      if (puzzle.runner != null) {
        puzzle.runner.firePatchControl(outMsg)
      }
      this
    }

    private def interject[T](l: List[T], item: T): List[T] = {
      if (l.isEmpty) {
        l
      } else {
        (l.head, l.tail) match {
          case (_, List()) => l
          case (h, t)      => h :: item :: (interject(t, item))
        }
      }
    }
  }
}
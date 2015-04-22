package ni {
  class PuzzleDrawer(puzzle: AbstractPuzzle) {
    def setBackgroundColor(red: Double, blue: Double, green: Double): PuzzleDrawer = {
      fire("background", red.toString, blue.toString, green.toString)
      this
    }

    def setBackgroundColor(color: String): PuzzleDrawer = {
      fire("background", color)
      this
    }

    def setMainText(text: String): PuzzleDrawer = {
      fire("maintext", text)
      this
    }

    def setChoicesText(choices: Map[Button, Option[String]]): PuzzleDrawer = {
      val optionToEmpty = (optStr: Option[String]) => optStr match { 
        case Some(s) => s
        case None    => ""
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

    def reset() = {
      this.setBackgroundColor("white")
      this.setMainText("")
      this.setChoicesText(Map(RedButton() -> None, 
                              BlueButton() -> None, 
                              YellowButton() -> None))
    }

    private def fire(msg: String*): Unit = {
      val outMsg = "drawpuzzle " + interject(msg.toList, " ").fold("")((a, b) => a + b)
      if (puzzle.runner != null) {
        puzzle.runner.firePatchControl(outMsg)
      }
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
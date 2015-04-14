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
      choices.foreach({ 
        case (RedButton(), Some(text))    => fire("choice", "red", text)
        case (BlueButton(), Some(text))   => fire("choice", "blue", text)
        case (YellowButton(), Some(text)) => fire("choice", "yellow", text)
        case _                            => ()
      })
      this
    }

    def setImageURL(imageURL: String): PuzzleDrawer = {
      // TODO
      this
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
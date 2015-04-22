// package ni;

// import ni.AbstractPuzzle;
// import java.util.*;

// public class GenericMultipleChoicePuzzle extends AbstractPuzzle {
//   String mainText;
//   java.util.Map<Button, String> choices;
//   Button correctChoice;

//   boolean isActive = false;
//   ni.PuzzleDrawer drawer;

//   MultipleChoicePuzzle mcpMixin;

//   public GenericMultipleChoicePuzzle(String mainText, 
//                                      Map<Button, String> choices, 
//                                      Button correctChoice) {
//     this.mainText = mainText;
//     this.choices = choices;
//     this.correctChoice = correctChoice;

//     this.mixin = // TODO;
//     this.drawer = new ni.PuzzleDrawer(this);
//   }

//   public void start() {
//     isActive = true;
//     drawer.reset()
//           .setBackgroundColor("white")
//           .setMainText(mainText)
//           .setChoicesText(this.choices);
//   }

//   public void end() {
//     isActive = false;
//   }

//   static GenericMultipleChoicePuzzle make(String text,
//                                           String successTransition,
//                                           String failureTransition,
//                                           Button correctChoice,
//                                           String redChoice,
//                                           String blueChoice,
//                                           String yellowChoice,
//                                           boolean isRepeatable) {
//     Map<Button, String> choiceMap = new HashMap<Button, String>();

//     choiceMap.put(Button.Red, redChoice);
//     choiceMap.put(Button.Blue, blueChoice);
//     choiceMap.put(Button.Yellow, yellowChoice);

//     GenericMultipleChoicePuzzle result = new GenericMultipleChoicePuzzle(text, choiceMap, correctChoice);
//     result.successTransition = successTransition;
//     result.failureTransition = failureTransition;
//     result.isRepeatable = isRepeatable;
//     return result;
//   }
// }

// class PressButtonPuzzle extends GenericMultipleChoicePuzzle {
//   public PressButtonPuzzle(Button toPress) {
//     super("", new HashMap<Button, String>(), toPress);

//     String buttonString;
//     switch (toPress) {
//       case Button.Red:
//         buttonString = "red"; break;
//       case Button.Blue:
//         buttonString = "blue"; break;
//       case Button.Yellow:
//         buttonString = "yellow"; break;
//       default:
//         buttonString = "<undefined>";
//     }

//     this.mainText = "Press the " + buttonString + " button.";
//   }

//   public void start() {
//     isActive = true;
//     drawer.reset()
//           .setBackgroundColor(buttonString)
//           .setMainText(mainText);

//   }

//   public static PressButtonPuzzle make(Button toPress,
//                                        String successTransition,
//                                        String failureTransition,
//                                        boolean isRepeatable) {
//     PressButtonPuzzle result = new PressButtonPuzzle(toPress);
//     result.successTransition = successTransition;
//     result.failtureTransition = failtureTransition;
//     result.isRepeatable = isRepeatable;
//     return result;
//   }
// }

// public abstract class MultipleChoicePuzzle extends AbstractPuzzle {
//   String mainText;
//   Map<Button, String> choices;
//   Button correctChoice;

//   boolean isActive;

//   public void receiveInput(String event) {
//     System.out.println("receiveInput " + (if (this.runner.isLeft) "left" else "right") 
//                          + " " + event + " isActive? " + isActive);
//     if (isActive) {
//       Button press = toButtonPress(event);
//       if (press != null && correctChoice != null) {
        
//       }

//       (toButtonPress(event), correctChoice) match {
//         case (Some(button), Some(answer)) => respondToButton(button, answer)
//         case _                            => () // unknown event or no correct answer
//       }
//     } 
//   }
// }


//   trait MultipleChoicePuzzle extends AbstractPuzzle {
//     // main text of question / puzzle
//     var mainText: String
//     // response strings to be shown on screen
//     var choices: Map[Button, Option[String]]
//     // button corresponding to correct answer
//     var correctChoice: Option[Button]

//     var isActive: Boolean

//     def receiveInput(event: String): Unit = {
//       System.out.println("receiveInput " + (if (this.runner.isLeft) "left" else "right") 
//                          + " " + event + " isActive? " + isActive);
//       if (isActive) {
//         (toButtonPress(event), correctChoice) match {
//           case (Some(button), Some(answer)) => respondToButton(button, answer)
//           case _                            => () // unknown event or no correct answer
//         }
//       } 
//     }

//     def setChoice(button: Button, text: String): Unit = {
//       choices = choices + (button -> Some(text))
//     }

//     def setCorrectChoice(button: Button): Unit = button match {
//       case null => correctChoice = None
//       case _    => correctChoice = Some(button)
//     }

//     private def respondToButton(button: Button, answerButton: Button) = button match {
//       case `answerButton` => super.successful()
//       case _              => super.failure()
//     }

//     private def toButtonPress(eventString: String): Option[Button] = eventString match {
//       case "ctrl button red"    => Some(RedButton())
//       case "ctrl button blue"   => Some(BlueButton())
//       case "ctrl button yellow" => Some(YellowButton())
//       case _                    => None
//     }
//   }

// }
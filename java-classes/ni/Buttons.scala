// Defines enumerations for the hardware buttons.
package ni {
  sealed abstract class Button
  case class RedButton()    extends Button
  case class BlueButton()   extends Button
  case class YellowButton() extends Button
}
package ni;

public class SimplePuzzleGenerator implements IPuzzleGenerator {

  private java.util.Random random;

  public SimplePuzzleGenerator () {
    this.random = new java.util.Random();
  }

  /* Generates `howMany` puzzles.
   */
  public Puzzle[] generate (int howMany) {
    Puzzle[] result = new Puzzle[howMany];

    for (int i = 0; i < howMany; i++) {
      IUserInputEvent[] answerEvts = new IUserInputEvent[i + 1];
      System.out.print("Constructing puzzle: ");
      for (int j = 0; j < i + 1; j++) {
        char nextChar = randomCharacter();
        System.out.print(nextChar + " ");
        answerEvts[j] = new KeyboardInputEvent(nextChar);
      }
      System.out.println();
      InputSequence answer = new InputSequence(answerEvts);

      result[i] = new Puzzle(answer,
                             new IExternalEvent[] { new OscExternalEvent("/puzzle/started " + i) },
                             new IExternalEvent[] { new OscExternalEvent("/puzzle/completed " + i) },
                             new IExternalEvent[] { new OscExternalEvent("/puzzle/correct " + i) }, 
                             new IExternalEvent[] { new OscExternalEvent("/puzzle/wrong " + i) },
                             new java.util.HashMap<Integer, IExternalEvent[]>());
    }

    return result;
  }

  private char randomCharacter () {
    byte alphaStart = 'a';
    byte offset = (byte)(random.nextInt(26));
    return (char)(alphaStart + offset);
  }
}
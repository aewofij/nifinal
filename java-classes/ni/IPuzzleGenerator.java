package ni;

/*
Interface to factory for creating puzzles. 
*/

public interface IPuzzleGenerator {
  /* Generates `howMany` puzzles.
   */
  public Puzzle[] generate(int howMany);
}
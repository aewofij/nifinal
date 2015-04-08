package ni;

public class PuzzleSequence {
  private Puzzle[] puzzles;
  private int index;

  public PuzzleSequence (Puzzle[] puzzles) {
    this.puzzles = puzzles;
    index = 0;
  }

  // Sets the current puzzle to the next puzzle, and returns that puzzle, or null if at end of puzzle list.
  public IPuzzle next() {
    i = i + 1;
    return i >= puzzles.length ? null : puzzles[i];
  } 

  // Returns the current puzzle.
  public IPuzzle current() {
    return puzzles[i];
  }
}
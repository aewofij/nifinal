package ni;

import java.util.*;
import util.Tuple;

public class PuzzleSequence {
  private List<Tuple<AbstractPuzzle, AbstractPuzzle>> puzzles;
  private int index;

  public PuzzleSequence () {
    this.puzzles = new ArrayList<Tuple<AbstractPuzzle, AbstractPuzzle>>(); 
    this.puzzles.add(new Tuple<AbstractPuzzle, AbstractPuzzle>(new RhythmPuzzle("123.mov"), new RhythmPuzzle("123.mov")));
    index = 0;
  }

  // Sets the current puzzle to the next puzzle, and returns that puzzle, or null if at end of puzzle list.
  public Tuple<AbstractPuzzle, AbstractPuzzle> next() {
    index = index + 1;
    return index >= puzzles.size() ? null : puzzles.get(index);
  } 

  // Returns the current puzzles.
  public Tuple<AbstractPuzzle, AbstractPuzzle> current() {
    return puzzles.get(index);
  }

  public void reset () {
    index = 0;
  }
}
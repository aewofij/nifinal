package ni;

import java.util.*;
import util.Tuple;

public class PuzzleSequence {
  private ArrayList<Tuple<AbstractPuzzle, AbstractPuzzle>> puzzles;
  // Maps <step index> to a list of tuples of form (<command>, <delay time>), 
  //   where <delay time> is the amount of time in milliseconds between transitioning to 
  //   puzzle at step <step index> and firing <command> out Referee's general outlet.
  // (0 -> <["inTheHole", 1000]>) will fire "inTheHole" one second after starting the first puzzle.
  private Map<Integer, List<Tuple<String, Integer>>> commandsOnStep;
  private int index;

  public PuzzleSequence () {
    this.puzzles = new ArrayList<Tuple<AbstractPuzzle, AbstractPuzzle>>(); 
    this.commandsOnStep = new HashMap<Integer, List<Tuple<String, Integer>>>();
    
    // this.puzzles.add(puzzlePair(new RhythmPuzzle("123.mov"), new RhythmPuzzle("123.mov")));
  	// this.puzzles.add(puzzlePair(new AnsMayVaryOnewayPuzzle("123.mov"), new AnsMayVaryOnewayPuzzle("123.mov")));

    this.addPair(GenericMultipleChoicePuzzle.make("What do I do?", 
                                                  "wheel.mov",
                                                  "garbage.mov",
                                                  new RedButton(), 
                                                  "correct",
                                                  "incorrect",
                                                  "also wrong"),
                 GenericMultipleChoicePuzzle.make("What do I do?", 
                                                  "wheel.mov",
                                                  "garbage.mov",
                                                  new RedButton(), 
                                                  "correct",
                                                  "incorrect",
                                                  "also wrong"));
    this.addPair(GenericMultipleChoicePuzzle.make("Where do I go?", 
                                                  null,
                                                  "garbage.mov",
                                                  new BlueButton(), 
                                                  "North",
                                                  "East",
                                                  "West"),
                  GenericMultipleChoicePuzzle.make("Where do I go?", 
                                                  null,
                                                  "123.mov",
                                                  new BlueButton(), 
                                                  "North",
                                                  "East",
                                                  "West"),
                  makeCommand("ai_video garbage.mov", 1000));
    this.addPair(GenericMultipleChoicePuzzle.make("Where do I go2?", 
                                                  null,
                                                  "garbage.mov",
                                                  new BlueButton(), 
                                                  "North",
                                                  "East",
                                                  "West"),
                  GenericMultipleChoicePuzzle.make("Where do I go2?", 
                                                  null,
                                                  "123.mov",
                                                  new BlueButton(), 
                                                  "North",
                                                  "East",
                                                  "West"));
    this.addPair(GenericMultipleChoicePuzzle.make("Press blue.", 
                                                  null,
                                                  "garbage.mov",
                                                  new BlueButton(), 
                                                  "North",
                                                  "East",
                                                  "West"),
                 GenericMultipleChoicePuzzle.make("Press yellow.", 
                                                  null,
                                                  "123.mov",
                                                  new YellowButton(), 
                                                  "North",
                                                  "East",
                                                  "West"));

    index = 0;
  }

  PuzzleSequence addPair(AbstractPuzzle p1, AbstractPuzzle p2) {
    this.puzzles.add(puzzlePair(p1, p2));
    return this;
  }

  PuzzleSequence addPair(AbstractPuzzle p1, AbstractPuzzle p2, List<Tuple<String, Integer>> commands) {
    this.puzzles.add(puzzlePair(p1, p2));
    this.commandsOnStep.put(this.puzzles.size() - 1, commands);
    return this;
  }


  // Sets the current puzzle to the next puzzle, and returns that puzzle, or null if at end of puzzle list.
  public Tuple<AbstractPuzzle, AbstractPuzzle> next() {
    index++;
    return index >= puzzles.size() ? null : puzzles.get(index);
  } 

  // Returns the current puzzles.
  public Tuple<AbstractPuzzle, AbstractPuzzle> current() {
    return puzzles.get(index);
  }

  // Gets the commands from `commandsOnStep` for this step, or an empty list if no commands.
  public List<Tuple<String, Integer>> currentCommands() {
    if (this.commandsOnStep.containsKey(index)) {
      return this.commandsOnStep.get(index);
    } else {
      return new ArrayList<Tuple<String, Integer>>();
    }
  }

  public void reset () {
    index = 0;
  }

  private Tuple<AbstractPuzzle, AbstractPuzzle> puzzlePair(AbstractPuzzle left, AbstractPuzzle right) {
    return new Tuple<AbstractPuzzle, AbstractPuzzle>(left, right);
  }

  // Creates a singleton command list with the given command/delay time.
  private List<Tuple<String, Integer>> makeCommand(String cmd, int delay) {
    List<Tuple<String, Integer>> result = new ArrayList<Tuple<String, Integer>>();
    result.add(new Tuple<String, Integer>(cmd, delay));
    return result;
  }
}
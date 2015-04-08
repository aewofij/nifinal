// package ni;

// import com.cycling74.max.*;
// import java.util.*;
// import util.Tuple;

// public class RefereeRunner extends MaxObject {

//   public class Referee extends MaxObject {

//     public static Referee instance;
//     public static Referee getInstance() {
//       if (instance == null) {
//         instance = new Referee(new Atom[0]);
//       }
//       return instance;
//     }

//     final int kLeftOutlet = 0;
//     final int kRightOutlet = 1;
//     final int kGeneralOutlet = 2;

//     enum Player {
//       left, right
//     };

//     private PuzzleSequence puzzles;
//     private Runner leftRunner, rightRunner;
//     private Player completed = null;
//     private boolean completedSuccessful;

//     // `protected` because we don't want anyone to call this except `Referee`, above
//     public Referee (Atom[] args) {
//       this.puzzles = new PuzzleSequence();
//     }

//     public void start () {
//       System.out.println("Starting Referee...");

//       puzzles.reset();
//       System.out.println("Sending...");
//       sendPuzzle(puzzles.current());
//     }


//     // called when a player completes a puzzle
//     public void completed (boolean isLeftPlayer, boolean success) {
//       if (!success) {
//         punish();
//       }

//       if (completed == null) {
//         completed = isLeftPlayer ? Player.left : Player.right;
//         completedSuccessful = success;
//         return;
//       } else {
//         if (isLeftPlayer && completed == Player.right) {
//           transition(success, completedSuccessful);
//         }
//         if (!isLeftPlayer && completed == Player.left) {
//           transition(completedSuccessful, success);
//         }
//       }
//     }

//     public void registerLeftRunner (Runner runner) {
//       leftRunner = runner;
//       System.out.println("Referee registered left Runner: " + runner + "; right runner " + rightRunner);
//     }

//     public void registerRightRunner (Runner runner) {
//       rightRunner = runner;
//       System.out.println("Referee registered right Runner: " + rightRunner + "; left runner " + leftRunner);
//     }

//     void punish () {
//       outlet(kGeneralOutlet, Atom.newAtom("punish"));
//     }

//     void transition (boolean leftSuccess, boolean rightSuccess) {
//       if (leftSuccess && rightSuccess) {
//         sendPuzzle(puzzles.next());
//       } else {
//         // stupid
//         if (puzzles.current().fst.isRepeatable && puzzles.current().snd.isRepeatable) {
//           sendPuzzle(puzzles.current());
//         } else {
//           sendPuzzle(puzzles.next());
//         }
//       }
//     }

//     void sendPuzzle (Tuple<AbstractPuzzle, AbstractPuzzle> puzzle) {
//       AbstractPuzzle leftPuzzle = puzzle.fst;
//       AbstractPuzzle rightPuzzle = puzzle.snd;

//       System.out.println("Sending to " + leftRunner + " and " + rightRunner);

//       if (leftRunner != null) {
//         leftPuzzle.runner = leftRunner;
//         if (!leftRunner.receiveNextPuzzle(leftPuzzle)) {
//           System.err.println("Left runner did not receive puzzle!");
//         }
//       }
//       if (rightRunner != null) {
//         rightPuzzle.runner = rightRunner;
//         if (!rightRunner.receiveNextPuzzle(rightPuzzle)) {
//           System.err.println("Right runner did not receive puzzle!");
//         }
//       }
//     }
//   }


//   // final int kLeftOutlet = 0;
//   // final int kRightOutlet = 1;
//   // final int kGeneralOutlet = 2;

//   // enum Player {
//   //   left, right
//   // };

//   // singleton
//   private static Referee instance;
//   public static Referee getInstance() {
//     return Referee.getInstance();
//     // if (instance == null) {
//     //   instance = new SecretReferee(new Atom[0]);
//     // }
//     // return instance;
//   }

//   // private PuzzleSequence puzzles;
//   // private Runner leftRunner, rightRunner;
//   // private Player completed = null;
//   // private boolean completedSuccessful;

//   // `protected` because we don't want anyone to call this except `Referee`, above
//   public RefereeRunner (Atom[] args) {
//     declareInlets(new int[] { DataTypes.ALL });
//     declareOutlets(new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL });

//     // this.puzzles = new PuzzleSequence();
//   }

//   public void start () {
//     Referee.getInstance().start();
//     // System.out.println("Starting Referee...");

//     // puzzles.reset();
//     // System.out.println("Sending...");
//     // sendPuzzle(puzzles.current());
//   }


//   // called when a player completes a puzzle
//   public void completed (boolean isLeftPlayer, boolean success) {
//     Referee.getInstance().completed(isLeftPlayer, success);
//     // if (!success) {
//     //   punish();
//     // }

//     // if (completed == null) {
//     //   completed = isLeftPlayer ? Player.left : Player.right;
//     //   completedSuccessful = success;
//     //   return;
//     // } else {
//     //   if (isLeftPlayer && completed == Player.right) {
//     //     transition(success, completedSuccessful);
//     //   }
//     //   if (!isLeftPlayer && completed == Player.left) {
//     //     transition(completedSuccessful, success);
//     //   }
//     // }
//   }

//   public void registerLeftRunner (Runner runner) {
//     Referee.getInstance().registerLeftRunner(runner);
//     // leftRunner = runner;
//     // System.out.println("Referee registered left Runner: " + runner + "; right runner " + rightRunner);
//   }

//   public void registerRightRunner (Runner runner) {
//     Referee.getInstance().registerRightRunner(runner);
//     // rightRunner = runner;
//     // System.out.println("Referee registered right Runner: " + rightRunner + "; left runner " + leftRunner);
//   }

//   // void punish () {
//   //   outlet(kGeneralOutlet, Atom.newAtom("punish"));
//   // }

//   // void transition (boolean leftSuccess, boolean rightSuccess) {
//   //   if (leftSuccess && rightSuccess) {
//   //     sendPuzzle(puzzles.next());
//   //   } else {
//   //     // stupid
//   //     if (puzzles.current().fst.isRepeatable && puzzles.current().snd.isRepeatable) {
//   //       sendPuzzle(puzzles.current());
//   //     } else {
//   //       sendPuzzle(puzzles.next());
//   //     }
//   //   }
//   // }

//   // void sendPuzzle (Tuple<AbstractPuzzle, AbstractPuzzle> puzzle) {
//   //   AbstractPuzzle leftPuzzle = puzzle.fst;
//   //   AbstractPuzzle rightPuzzle = puzzle.snd;

//   //   System.out.println("Sending to " + leftRunner + " and " + rightRunner);

//   //   if (leftRunner != null) {
//   //     leftPuzzle.runner = leftRunner;
//   //     if (!leftRunner.receiveNextPuzzle(leftPuzzle)) {
//   //       System.err.println("Left runner did not receive puzzle!");
//   //     }
//   //   }
//   //   if (rightRunner != null) {
//   //     rightPuzzle.runner = rightRunner;
//   //     if (!rightRunner.receiveNextPuzzle(rightPuzzle)) {
//   //       System.err.println("Right runner did not receive puzzle!");
//   //     }
//   //   }
//   // }

// }
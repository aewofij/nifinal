// package ni;

// // import com.cycling74.max.*;
//  import com.cycling74.jitter.*;
// // import com.cycling74.max.Atom;

// /*
// Jitter vid implemention of `IExternalEvent`.
// */

// //public class JitterExternalEvent extends com.cycling74.max.MaxObject implements IExternalEvent {
// public class JitterExternalEvent implements IExternalEvent {
//   private final String myIdentifier;
//   private JitterObject myWindow;
// //  private JitterObject movie;

//   public JitterExternalEvent (String id) {
//   // jit.window
// 	 try {
// 	 this.myWindow = new JitterObject("jit.window","myWindow");
// 	 } catch (Exception e) {
// 		 //this doesn't catch!???
// 		 System.err.println("sdfs");
// 	 }
// 	// jit.qt.movie
// //	 this.movie = new com.cycling74.jitter.JitterObject("jit.qt.movie");
//     this.myIdentifier = id;
//   }

//   public String identifier () {
//     return myIdentifier;
//   }

//   @Override
//   public String toString () {
//     return myIdentifier;
//   }
// }
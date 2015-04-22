package ni;

import com.cycling74.max.*;
import com.cycling74.jitter.*;

public class ProgressBar extends MaxObject
{

private static ProgressBar instance;
  public static ProgressBar getInstance() {
    if (instance == null) {
      instance = new ProgressBar(new Atom[0]);
    }
    return instance;
  }

JitterObject[] progressBar;
JitterObject[][] tankEnds;
JitterObject[] tankMiddle;
// JitterObject[] videoPlanes;
// JitterObject texture;
int players;
float progressBarLength, progressBarWidth, progress, tankWidth, scale, startx, starty;

public ProgressBar(Atom[] args)
{
System.out.println("creating progress bar");
declareInlets(new int[]{ DataTypes.ALL, DataTypes.ALL});
declareOutlets(new int[]{ DataTypes.ALL, DataTypes.ALL});
setInletAssist(new String[] { "bang to output","list from TuioClient (ID,X,Y,angle,scale,R,G,B,alpha)"});
setOutletAssist(new String[] { "the result opengl text"});

Referee.getInstance().registerProgressBar(this);
System.out.println("registering progress bar");

players = 2;
progressBarLength = 1.0f;
progressBarWidth = 0.25f;
tankWidth = progressBarWidth+0.1f;
scale = 0.2f;
startx = -1.3f;
starty = 0.6f;
progress = 1.0f;
progressBar = new JitterObject[players];
tankEnds = new JitterObject[players][2];
tankMiddle = new JitterObject[players];
// videoPlanes = new JitterObject[players];

createInfoOutlet(false);

for (int i= 0; i<players; i++) {

	// videoPlanes[i] = new JitterObject("jit.gl.videoplane");
	// videoPlanes[i].setAttr("antialias", 1);
	// videoPlanes[i].setAttr("lighting_enable", 0);
	// videoPlanes[i].setAttr("layer", 7);
	progressBar[i] = new JitterObject("jit.gl.sketch");
	progressBar[i].setAttr("antialias", 1);
	progressBar[i].setAttr("lighting_enable", 0);
	progressBar[i].setAttr("layer", 8);
	
	tankMiddle[i] = new JitterObject("jit.gl.sketch");
	tankMiddle[i].setAttr("antialias", 1);
	tankMiddle[i].setAttr("lighting_enable", 0);
	tankMiddle[i].setAttr("layer", 8);
	
	for (int j= 0; j<2; j++) {
		tankEnds[i][j] = new JitterObject("jit.gl.sketch");
		tankEnds[i][j].setAttr("antialias", 1);
		tankEnds[i][j].setAttr("lighting_enable", 0);
		tankEnds[i][j].setAttr("layer", 8);
	}
	
	//assuming we only have 2 players
	if (i == 0) {
		progressBar[i].setAttr("drawto", "left-ctx");
		tankMiddle[i].setAttr("drawto", "left-ctx");
		for (int j= 0; j<2; j++) tankEnds[i][j].setAttr("drawto", "left-ctx");
	}
	else {
		progressBar[i].setAttr("drawto", "right-ctx");
		tankMiddle[i].setAttr("drawto", "right-ctx");
		for (int j= 0; j<2; j++) tankEnds[i][j].setAttr("drawto", "right-ctx");
	}
}
this.draw();

//texture = new JitterObject("jit.gl.texture");
//texture.read("texture2.tif");
//texture.setAttr("name","cloud");
//texture.setAttr("file","texture2.tif");

}

public void bang()
{
}

public void inlet(float f)
{
if (f <0) {
	progress = 0;
}
else if (f>1.0f) {
	progress = 1.0f;
}
else {
progress = f;
}
System.out.println("f= " + f);
this.draw();
System.out.println("drawn");
}

public void draw()
{
for (int i= 0; i<players; i++) {
//fuel tank
	//tankMiddle
	tankMiddle[i].send("reset");
	tankMiddle[i].send("glmatrixmode",new Atom[]{Atom.newAtom("modelview")});
	tankMiddle[i].send("glpushmatrix");
	tankMiddle[i].send("gltranslate", new float[]{startx,starty,0.0f});
	tankMiddle[i].send("glscale", new float[]{scale,scale,1.0f});
	tankMiddle[i].send("glbegin", new Atom[]{Atom.newAtom("tri_strip")});
	for (int x=-1; x<2; x+=2) {
		for (int y=-1; y<2; y+=2) {
			tankMiddle[i].send("glcolor", new float[]{0.0f,0.0f,1.0f,1.0f});
			tankMiddle[i].send("glvertex", new float[]{tankWidth*(x)/2.0f,progressBarLength*(y)/2.0f,0.0f});
		}
	}
	tankMiddle[i].send("glend");
	tankMiddle[i].send("glpopmatrix");
	// outlet(0, new Atom[]{Atom.newAtom(tankMiddle[i].send("draw"))});
	// outlet(0, "jit_matrix", tankMiddle[i].send("draw"));
	
	//tankEnds
	for (int j= 0; j<2; j++) {
		tankEnds[i][j].send("reset");
		if (j == 0) tankEnds[i][j].send("moveto", new float[]{startx,starty+progressBarLength*scale/2.0f,0.0f});
		else tankEnds[i][j].send("moveto", new float[]{startx,starty-progressBarLength*scale/2.0f,0.0f});
		tankEnds[i][j].send("glcolor", new float[]{0.0f,0.0f,1.0f,1.0f});
		tankEnds[i][j].send("circle", new float[]{tankWidth*scale/2.0f});
		// outlet(0, new Atom[]{Atom.newAtom(tankEnds[i][j].send("draw"))});
		// outlet(0, "jit_matrix", tankEnds[i][j].send("draw"));
	}
	
// progress bar
	progressBar[i].send("reset");
	progressBar[i].send("glmatrixmode",new Atom[]{Atom.newAtom("modelview")});
	progressBar[i].send("glpushmatrix");
	progressBar[i].send("gltranslate", new float[]{startx,starty-(progressBarLength*scale*(1.0f-progress)/2.0f),0.0f});
	progressBar[i].send("glscale", new float[]{scale,scale,1.0f});
	progressBar[i].send("glbegin", new Atom[]{Atom.newAtom("tri_strip")});
	for (int x=-1; x<2; x+=2) {
		for (int y=-1; y<2; y+=2) {
			progressBar[i].send("glcolor", new float[]{0.0f,1.0f,0.0f,1.0f});
			if (progress<0.5f) progressBar[i].send("glcolor", new float[]{1.0f,1.0f,0.0f,1.0f});
			if (progress<0.2f) progressBar[i].send("glcolor", new float[]{1.0f,0.0f,0.0f,1.0f});
			progressBar[i].send("glvertex", new float[]{progressBarWidth*(x)/2.0f,progressBarLength*progress*(y)/2.0f,0.0f});
		}
	}
	progressBar[i].send("glend");
	progressBar[i].send("glpopmatrix");
	// outlet(1, new Atom[]{Atom.newAtom(progressBar[i].send("draw"))});
	// outlet(1, "jit_matrix", progressBar[i].send("draw"));
}
}

public void notifyDeleted()
{
	for (int i= 0; i<players; i++) {
		progressBar[i].freePeer();
		tankMiddle[i].freePeer();
		for (int j= 0; j<2; j++) {
		tankEnds[i][j].freePeer();
		}
	}
}

public void decrease(float f) {
progress = progress - f;
this.draw();
}

public float getProgress() {
	return progress;
}

}
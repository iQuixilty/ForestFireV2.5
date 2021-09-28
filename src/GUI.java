import processing.core.*;

public class GUI extends PApplet {
    Simulator sim;
    DisplayWindow display;

    public void settings() {
        size(640, 550); // set the size of the screen.
    }

    public void setup() {
        // Create a simulator
        sim = new Simulator(100, 100);
        frameRate(10);
        sim.fullRun(50);

        // Create the display
        // parameters: (10,10) is upper left of display
        // (620, 530) is the width and height
        display = new DisplayWindow(this, 10, 10, 620, 530);

        display.setNumCols(sim.getWidth());       // NOTE:  these must match your simulator!!
        display.setNumRows(sim.getHeight());

        // Set different grid values to different colors
        int red = color(255, 0, 0);          // pattern:  color(redAmount, greenAmount, blueAmount)
        int grey = color(178, 190, 181);
        int green = color(0, 255, 0);
        int brown = color(150, 75, 0);
        display.setColor(sim.livingTree, green);          // 1 displays as green
        display.setColor(sim.burningTree, red);            // 2 displays as red
        display.setColor(sim.ashTree, grey);           // 3 displays as grey
        display.setColor(sim.emptySpace, brown);          // 0 displays as white

    }

    @Override
    public void draw() {
        background(200);
        sim.propagateFire();
        display.drawGrid(sim.getDisplayGrid()); // display the game
        if (sim.isDone() && sim.getIsDone()) {
            FFStats forestFireStats = sim.getStats();
            System.out.println(forestFireStats);
        }
    }

    public static void main(String[] args) {
        PApplet.main("GUI");
    }
}

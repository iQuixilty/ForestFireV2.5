public class Simulator {
    private Tree[][] forest;
    private int rows;
    private int cols;
    private int timeSteps;
    private boolean isDone = true;
    public final int emptySpace = 0;
    public final int livingTree = 1;
    public final int burningTree = 2;
    public final int ashTree = 3;


    /***
     * Create a new simulator.  The simulator creates a new Forest of size (r, c).
     *
     * @param r amount of rows
     * @param c amount of columns
     */
    public Simulator(int r, int c) {
        forest = new Tree[r][c];
        rows = r;
        cols = c;
    }

    public void createForest() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                forest[i][j] = new Tree(0);
            }
        }
    }


    /**
     * Creates a forest with the given tree density. Fills the grid with that tree density.
     *
     * @param treeDensity amount of trees in the grid
     */
    public void initialize(int treeDensity) {
        int treesToFill = (int) ((treeDensity / 100.0) * (this.cols * this.cols)); // calculates amount of tress to fill in grid
        for (int i = 0; i < treesToFill; i++) {
            int randRow = (int) (Math.random() * this.rows); // generates random locations to fill trees
            int randCol = (int) (Math.random() * this.cols);
            if (forest[randRow][randCol].getTypeOfTree() == livingTree)
                i--; // if a living tree already exists, loop one more more
            else forest[randRow][randCol].setTypeOfTree(livingTree);
        }
    }

    /**
     * Sets fire to one living tree in the grid.
     */
    public void setFire() {
        while (true) { // keeps looping until one tree is set on fire
            int randRow = (int) (Math.random() * this.rows); // generates random location to set fire to
            int randCol = (int) (Math.random() * this.cols);
            if (forest[randRow][randCol].getTypeOfTree() == livingTree) { // if location has a living tree, sets fire to it
                forest[randRow][randCol].setTypeOfTree(burningTree);
                return; // ends method
            }
        }
    }

    /**
     * Copies the forest grid
     *
     * @return cloned forest grid
     */
    public Tree[][] copyForest() {
        Tree[][] newForest = new Tree[this.rows][this.cols];
        for (int a = 0; a < this.rows; a++) { // loops over rows and columns
            for (int b = 0; b < this.cols; b++) {
                newForest[a][b] = new Tree(forest[a][b].getTypeOfTree()); // copies each location to new 2d array
            }
        }
        return newForest; // returns new forest
    }

    /**
     * Checks if a tree has any neighboring trees
     *
     * @param arr grid to check neighboring trees
     * @param r   row index to check for
     * @param c   column index to check for
     * @return if point is in bound
     */
    public boolean inBound(Tree[][] arr, int r, int c) {
        return (arr.length - 1 >= r && arr[0].length - 1 >= c && r >= 0 && c >= 0); // checks if the row and column are within the boundaries
    }

    /**
     * Lights each neighboring tree on fire
     *
     * @param arr neighboring forest grid (3x3)
     * @param r   row index for tree that's being lit
     * @param c   column index for tree that's being lit
     */
    public void lightOnFire(Tree[][] arr, int r, int c) {
        for (int nRow = r - 1; nRow <= r + 1; nRow++) {
            for (int nCol = c - 1; nCol <= c + 1; nCol++) {
                if (inBound(arr, nRow, nCol)) {
                    if (arr[nRow][nCol].getTypeOfTree() == livingTree && Math.random() > 0.2) { // 80% chance to light on fire
                        forest[nRow][nCol].setTypeOfTree(burningTree); // lights living trees on fire
                    }
                }
            }
        }
    }

    public void addVariability(int r, int c) {
        if (forest[r][c].getTypeOfTree() == burningTree) {
            forest[r][c].setBurningSteps(forest[r][c].getBurningSteps() + 1);
            if (forest[r][c].getBurningSteps() > 10)
                forest[r][c].setTypeOfTree(ashTree); // if the tree has burnt for longer than 15 steps, turns to ash
        }
//        if (forest[r][c].getTypeOfTree() == ashTree) { // 40% chance if an ash tree is changed into empty space / living tree
//            forest[r][c].setAshSteps(forest[r][c].getAshSteps() + 1);
//            if (Math.random() > 0.8 && forest[r][c].getAshSteps() > 8) {
//                if (Math.random() > 0.7) forest[r][c].setTypeOfTree(emptySpace);
//                else forest[r][c].setTypeOfTree(livingTree);
//            }
//        }
    }

    /**
     * Starts spreading the fire
     */
    public void propagateFire() {
        Tree[][] newForest = copyForest(); // makes a new forest to check for fires against
        for (int row = 0; row < this.rows; row++) { // loops over above forest
            for (int col = 0; col < this.cols; col++) {
                if (newForest[row][col].getTypeOfTree() == burningTree) { // if the new forest contains a burning tree
                    lightOnFire(newForest, row, col); // lights neighbors on fire
                }
                addVariability(row, col);
            }
        }
        this.timeSteps++; // increments time steps
    }

    /**
     * Checks if the fire has burnt out
     *
     * @return if sim has finished
     */
    public boolean isDone() {
        for (int a = 0; a < getHeight(); a++) {
            for (int b = 0; b < getWidth(); b++) {
                if (forest[a][b].getTypeOfTree() == burningTree) return false; // if sim contains a burning tree, return
            }
        }
        return true;
    }

    /**
     * Gets statistics from the simulation
     *
     * @return FFStats object with various statistics
     */
    public FFStats getStats() {
        int livingTrees = 0;
        int ashTrees = 0;
        for (int a = 0; a < getWidth(); a++) {
            for (int b = 0; b < getHeight(); b++) {
                if (forest[a][b].getTypeOfTree() == livingTree) livingTrees++; // adds living trees
                if (forest[a][b].getTypeOfTree() == ashTree) ashTrees++; // adds ash trees
            }
        }
        this.isDone = false;
        return new FFStats(livingTrees, ashTrees, this.timeSteps); // returns FFStats object with stats
    }


    public void fullRun(int treeDensity) { // runs a full experiment
        createForest();
        initialize(treeDensity);
        setFire();
    }

    public int getWidth() {
        return forest[0].length;
    }

    public int getHeight() {
        return forest.length;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public Tree[][] getDisplayGrid() {
        return forest;
    }
}

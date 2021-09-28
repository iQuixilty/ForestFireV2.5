public class Tree {
    private int typeOfTree;
    private int burningSteps;
    private int ashSteps;

    public Tree(int typeOfTree) {
        this.typeOfTree = typeOfTree;
        this.burningSteps = 0;
        this.ashSteps = 0;
    }

    public int getTypeOfTree() {
        return typeOfTree;
    }

    public int getBurningSteps() {
        return burningSteps;
    }

    public int getAshSteps() {
        return ashSteps;
    }

    public void setTypeOfTree(int typeOfTree) {
        this.typeOfTree = typeOfTree;
    }

    public void setBurningSteps(int burningSteps) {
        this.burningSteps = burningSteps;
    }

    public void setAshSteps(int ashSteps) {
        this.ashSteps = ashSteps;
    }
}

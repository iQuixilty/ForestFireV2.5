public class FFStats {
    private int livingTrees;
    private int ashTrees;
    private int timeSteps;

    public FFStats(int livingTrees, int ashTrees, int timeSteps) {
        this.livingTrees = livingTrees;
        this.ashTrees = ashTrees;
        this.timeSteps = timeSteps;
    }

    public int getLivingTrees() {
        return livingTrees;
    }

    public int getAshTrees() {
        return ashTrees;
    }

    public int getTimeSteps() {
        return timeSteps;
    }

    @Override
    public String toString() {
        return "FFStats{" +
                "livingTrees=" + livingTrees +
                ", ashTrees=" + ashTrees +
                ", timeSteps=" + timeSteps +
                '}';
    }
}

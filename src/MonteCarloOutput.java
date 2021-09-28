public class MonteCarloOutput {
    public static void main(String[] args) {
        displayTable(10000);
    }

    public static double runExperiment(int treeDensity) {
        Simulator sim = new Simulator(20, 20);
        sim.fullRun(treeDensity);

        while (!sim.isDone()) sim.propagateFire();
        FFStats stats = sim.getStats();
        return ((double) stats.getAshTrees() / (stats.getLivingTrees() + stats.getAshTrees()));
    }

    public static double calcTrials(int n, int treeDensity) {
        double averagePercentBurned = 0;
        for (int i = 0; i < n; i++) {
            averagePercentBurned += runExperiment(treeDensity);
        }

        return averagePercentBurned / n;
    }

    public static void displayTable(int trials) {
        for (int i = 5; i <= 100; i += 5) {
            System.out.println(i + ", " + (calcTrials(trials, i) * 100));
        }
    }
}

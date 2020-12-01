import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/*public class PercolationStats {
    private final int trials;
    private final double[] fractions;
    private static final double time = 1.96;

    public PercolationStats(int n, int trials) {
        int openSites;
        Percolation pc = new Percolation(n);
        this.trials = trials;
        //sw =new Stopwatch();
        if (n < 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        fractions = new double[trials];
        /**Repeat the following until the system percolates:
         *Choose a site uniformly at random among all blocked sites.
         *Open the site.
         */
        /*for (int i = 0; i < trials; i++) {
            openSites = 0;
            while (!pc.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                if (!pc.isOpen(row,col)) {
                    pc.open(row,col);
                }
                openSites++;
            }
            fractions[i] = openSites/n*n;
        }
    }

    // sample mean of percolation threshold
    public double mean() {return StdStats.mean(fractions);}

    // sample standard deviation of percolation threshold
    public double stddev() {return StdStats.stddev(fractions);}

    // low endpoint of 95% confidence interval
    public double confidenceLo() {return  mean() - (time * stddev()/Math.sqrt(trials));}

    // high endpoint of 95% confidence interval
    public double confidenceHi() {return mean() + (time * stddev()/Math.sqrt(trials));}

    // test client (see below)
    public static void main(String[] args) {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats test = new PercolationStats(N, T);
        System.out.println("mean            = " + test.mean());
        System.out.println("stddev           = " + test.stddev());
        System.out.println("95% confidence interval            = " + "[" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }
}

*/
public class PercolationStats {
    private double stddev = Double.NaN;
    private double mean = Double.NaN;
    private double confidenceLo = Double.NaN;
    private double confidenceHi = Double.NaN;

    public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException("n:" + n + " or trials:" + trials + " is invalid.");
        }
        double[] trailsResult = new double[trials];
        for(int i = 0; i < trailsResult.length; i++){
            trailsResult[i] = generateRandomPercolationModel(n);
        }
        this.mean = StdStats.mean(trailsResult);
        this.stddev = StdStats.stddev(trailsResult);
        this.confidenceLo = mean - 1.96 * stddev / Math.sqrt(trials);
        this.confidenceHi = mean + 1.96 * stddev / Math.sqrt(trials);
    }

    private double generateRandomPercolationModel(int n) {
        Percolation p = new Percolation(n);
        double openCount = 0;
        while(!p.percolates()){
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            if (!p.isOpen(row, col)){
                p.open(row, col);
                openCount++;
            }
        }
        return openCount / (n * n);
    }

    public double mean(){                          // sample mean of percolation threshold
        return this.mean;
    }
    public double stddev(){                        // sample standard deviation of percolation threshold
        return this.stddev;
    }
    public double confidenceLo(){                  // low  endpoint of 95% confidence interval
        return this.confidenceLo;
    }
    public double confidenceHi(){                  // high endpoint of 95% confidence interval
        return this.confidenceHi;
    }

    public static void main(String[] args){
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats test = new PercolationStats(N, T);
        System.out.println("mean            = " + test.mean());
        System.out.println("stddev           = " + test.stddev());
        System.out.println("95% confidence interval            = " + "[" + test.confidenceLo() + ", " + test.confidenceHi() + "]");// test client (described below)
    }
}
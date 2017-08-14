/**
 * 
 * @author charl
 *
 */

public class PercolationStats {
    
    private int trials;
    private double[] results;
    
    /**
     * perform trials independent experiments on an n-by-n grid
     * @param n
     * @param trials
     * @throws 
     */
    public PercolationStats(int n, int trials){

        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException("Grid size and number of trials must be greater than '0'");
        }
        else{

            this.trials = trials;
            results = new double[trials];
            
            for (int i = 0; i < results.length; i++){

                Percolation p = new Percolation(n);
                int x = 0;
                while (!p.percolates()){

                    int row = StdRandom.uniform(1, n+1);
                    int col = StdRandom.uniform(1, n+1);
                    
                    if (!p.isOpen(row, col)){

                        p.open(row, col);
                        x++;
                    }
                }
                results[i] = (double) x /(double) (n*n); //result
            }//for
        }//else 
    }
    
    /**
     * sample mean of percolation threshold
     * @return
     */
    public double mean(){

        return StdStats.mean(results);
    }
    
    /**
     * sample standard deviation of percolation threshold
     * @return
     */
    public double stddev(){

        return StdStats.stddev(results);
    }
    
    /**
     * low  endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLo(){

        return mean() - ((1.96*stddev())/Math.sqrt(trials));
    }
    
    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHi(){

        return mean() + ((1.96*stddev())/Math.sqrt(trials));
    }
    
    /**
     * MAIN (test client)
     * @param args
     */
    public static void main(String[] args){
        
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        
        PercolationStats stats = new PercolationStats(n, trials);
        
        System.out.println("---Percolation Stats---------------------------------------------------");
        System.out.println("Mean                     =" + stats.mean());
        System.out.println("Std                      =" + stats.stddev());
        System.out.println("95% Confidence Interval  =[ "+ stats.confidenceLo() + ", " + stats.confidenceHi() + " ]");
    }
}

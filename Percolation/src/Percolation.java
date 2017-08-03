/**
 * 
 * @author charles
 *
 */

public class Percolation {
	
	private final int vT;                          //virtual top
	private final int vB;                          //virtual bottom
	private final WeightedQuickUnionUF grid;               
	private final WeightedQuickUnionUF backwash;   //used to fix backwash problem
	private boolean[]site;
	private int n;
	
	/**
	 * create n-by-n grid, with all sites blocked
	 * @param n
	 * @throws IllegalArgumentException
	 */
	public Percolation(int n)
	{
		if (n <= 0)
		{
			throw new IllegalArgumentException("n must be greater than zero");
		}
		else
		{
		    this.n = n;
            this.vT = 0;
            this.vB = n*n+1;
            grid = new WeightedQuickUnionUF(n*n + 2); 
            backwash = new WeightedQuickUnionUF(n*n+ 1);
            site = new boolean[n*n];
		}
	} 
	
	/**
	 * Validates parameters and opens site.
	 * Checks surrounding sites and if they are open the connected them through union().
	 * @param row
	 * @param col
	 */
	public void open(int row, int col)
	{
	    validate(row, col);
	    
	    int index = siteIndex(row, col);
	    // open site
	    site[index] = true;
	    
	    if (row == 1)//top row
        { 
            grid.union(index, vT); 
            backwash.union(index, vT);
        }
        if (row == n)//bottom row
        {
            grid.union(index, vB);
        }
        
        if (row > 1 && isOpen(row-1, col))//left of site
        {
            grid.union(index, siteIndex(row-1, col));
            backwash.union(index, siteIndex(row-1, col));
        }
        
        if (row < n && isOpen(row+1, col))//right of
        {
            grid.union(index, siteIndex(row+1, col));
            backwash.union(index, siteIndex(row+1, col));
        }
        
        if (col > 1 && isOpen(row, col-1))//above 
        {
            grid.union(index, siteIndex(row, col-1));
            backwash.union(index, siteIndex(row, col-1));
        }
        
        if (col < n && isOpen(row, col+1))//below
        {
            grid.union(index, siteIndex(row, col+1));
            backwash.union(index, siteIndex(row, col+1));
        }
	}
	
	/**
	 * validates parameters and checks whether site is open or not
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isOpen(int row, int col)
	{
		validate(row, col);
		return site[siteIndex(row, col)];
	}
	
	/**
	 * Validates parameters and checks whether site is either directly connected to virtual top(vT)
	 * or connected through other open sites
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isFull(int row, int col)
	{
		validate(row, col);
		return  site[siteIndex(row, col)] && backwash.connected(vT, siteIndex(row, col));
	}
	
	/**
	 * Returns the number of open sites
	 * @return
	 */
	public int numberOfOpenSites()
	{
	    int open = 0;
	    for (int i = 0; i < site.length; i++)
	    {
	        if (site[i])
	        {
	            open++;
	        }
	    }
		return open;
	}
	
	/**
	 * Checks whether the Virtual top and Virtual Bottom are connected through opened sites
	 * Meaning that the grid percolates
	 * @return
	 */
	public boolean percolates()
	{
		return grid.connected(vT, vB);
	}
	
	//Added Private Methods
	
	/**
	 * Validates parameters then changes row and col into 1D arr[] indexes (0-n)
	 * @param row
	 * @param col
	 * @return
	 */
	private int siteIndex(int row, int col)
	{
		validate(row, col);
		return ((n * (row-1) + col)-1);
	}

	/**
	 * Validates row and col parameters to makes sure they are within specified grid (1 through N)
	 * @param row
	 * @param col
	 * @throws IllegalArgumentException
	 */
	private void validate(int row, int col)
	{
		if ((row-1 < 0 || row-1 > n) || (col-1 < 0 || col-1 > n))
			throw new IllegalArgumentException("Out of bounds");
	}
}

<h2>Programming Assignment 1: Percolation</h2>

<p>
Write a program to estimate the value of the 
<em>percolation threshold</em>
via Monte Carlo simulation.


</p><p><b>Install a Java programming environment.</b>
Install a Java programming environment on your computer by following
these step-by-step instructions for your operating system
[
<a href = "http://algs4.cs.princeton.edu/mac">Mac OS X</a>
&middot;
<a href = "http://algs4.cs.princeton.edu/windows">Windows</a>
&middot;
<a href = "http://algs4.cs.princeton.edu/linux">Linux</a>
]. After following these instructions, the commands <tt>javac-algs4</tt> and <tt>java-algs4</tt>
will classpath in <a href = "http://algs4.cs.princeton.edu/code/algs4.jar">algs4.jar</a>,
which contains Java classes for I/O and all of the algorithms in the textbook.

<p>
To access a class in <code>algs4.jar</code>,
you need an <code>import</code> statement, such as the ones below:</em>

<blockquote>
<pre>
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
</pre></blockquote>

<p>
Note that <em>your</em> code must be in the <em>default package</em>; if you use a <tt>package</tt>
statement, the autograder will not be able to assess your work.


<p><b>Percolation.</b>
Given a composite systems comprised of randomly distributed insulating and metallic
materials: what fraction of the materials need to be metallic so that the composite system is an 
electrical conductor? Given a porous landscape with water on the surface (or oil below),
under what conditions will the water be able to drain through to the bottom (or the
oil to gush through to the surface)?
Scientists have defined an abstract process known as <em>percolation</em>
to model such situations.

<p><b>The model.</b>
We model a percolation system using an <em>n</em>-by-<em>n</em> grid of <em>sites</em>.
Each site is either <em>open</em> or <em>blocked</em>.
A <em>full</em> site is an open site
that can be connected to an open site in the top row via a chain of
neighboring (left, right, up, down) open sites.
We say the system <em>percolates</em> if 
there is a full site in the bottom row.
In other words, a system percolates if we fill all open sites
connected to the top row and that process fills some open
site on the bottom row. (For the 
insulating/metallic materials example, the open sites correspond
to metallic materials, so that a system that percolates 
has a metallic path from top to bottom, with full sites conducting.
For the porous substance example, the open sites 
correspond to empty space through which water might 
flow, so that a system that percolates lets water fill open sites, 
flowing from top to bottom.)

<p><b>The problem.</b>
In a famous scientific problem, researchers are interested in the
following question: if sites are independently set to be open with
probability <em>p</em> (and therefore blocked with
probability 1 &minus; <em>p</em>), what is the probability that the system percolates?
When <em>p</em> equals 0, the system does not percolate; when <em>p</em> equals 1,
the system percolates.
The plots below show the site vacancy probability <em>p</em> versus the percolation
probability for 20-by-20 random grid (left) and 100-by-100 random grid (right).
<p>

<p>
When <em>n</em> is sufficiently large, there is a <em>threshold</em> value <em>p</em>* such
that when <em>p</em> &lt; <em>p</em>* a random <em>n</em>-by-<em>n</em> grid 
almost never percolates, and when <em>p</em> &gt; <em>p</em>*,
a random <em>n</em>-by-<em>n</em> grid almost always percolates.
No mathematical solution for determining the percolation threshold <em>p</em>*
has yet been derived.
Your task is to write a computer program to estimate <em>p</em>*.


<p><b>Percolation data type.</b>
To model a percolation system, create a data type <tt>Percolation</tt> with the following API:

<blockquote>
<pre>
<b>public class Percolation {</b>
   <b>public Percolation(int n)</b>                <font color = gray>// create n-by-n grid, with all sites blocked</font>
   <b>public    void open(int row, int col)</b>    <font color = gray>// open site (row, col) if it is not open already</font>
   <b>public boolean isOpen(int row, int col)</b>  <font color = gray>// is site (row, col) open?</font>
   <b>public boolean isFull(int row, int col)</b>  <font color = gray>// is site (row, col) full?</font>
   <b>public     int numberOfOpenSites()</b>       <font color = gray>// number of open sites</font>
   <b>public boolean percolates()</b>              <font color = gray>// does the system percolate?</font>

   <b>public static void main(String[] args)</b>   <font color = gray>// test client (optional)</font>
<b>}</b>
</pre></blockquote>


<p><em>Corner cases.&nbsp;</em>
By convention, the row and column indices 
are integers between 1 and <em>n</em>, where (1, 1) is the upper-left site:
Throw a <tt>java.lang.IllegalArgumentException</tt>
if any argument to <tt>open()</tt>, <tt>isOpen()</tt>, or <tt>isFull()</tt> 
is outside its prescribed range.
The constructor should throw a <tt>java.lang.IllegalArgumentException</tt> if <em>n</em> &le; 0.


<p><em>Performance requirements.&nbsp;</em>
The constructor should take time proportional to <em>n</em><sup>2</sup>; all methods should
take constant time plus a constant number of calls to the union&ndash;find methods 
<tt>union()</tt>, <tt>find()</tt>, <tt>connected()</tt>, and <tt>count()</tt>.

<p><b>Monte Carlo simulation.</b>
To estimate the percolation threshold, consider the following computational experiment:
<ul>

<p><li> Initialize all sites to be blocked.

<p><li> Repeat the following until the system percolates:

<ul>
<p><li> Choose a site uniformly at random among all blocked sites.
<p><li> Open the site.
</ul>

<p><li> The fraction of sites that are opened when the system percolates
provides an estimate of the percolation threshold.
</ul>

<p>
For example, if sites are opened in a 20-by-20 lattice according to the snapshots below,
then our estimate of the percolation threshold is 204/400 = 0.51 because the system
percolates when the 204th site is opened.
<p>

<p>
By repeating this computation experiment <em>T</em> times and averaging the results,
we obtain a more accurate estimate of the percolation threshold.
Let <em>x<sub>t</sub></em> be the fraction of open sites in computational experiment <em>t</em>.
The sample mean \(\overline x\) provides an estimate of the percolation threshold;
the sample standard deviation <em>s</em>; measures the sharpness of the threshold.
<p>

<p>
To perform a series of computational experiments, create a data type <tt>PercolationStats</tt>
with the following API.

<blockquote>
<pre>
<b>public class PercolationStats {</b>
<b>   public PercolationStats(int n, int trials)  </b>  <font color = gray>// perform trials independent experiments on an n-by-n grid</font>
<b>   public double mean()                        </b>  <font color = gray>// sample mean of percolation threshold</font>
<b>   public double stddev()                      </b>  <font color = gray>// sample standard deviation of percolation threshold</font>
<b>   public double confidenceLo()                </b>  <font color = gray>// low  endpoint of 95% confidence interval</font>
<b>   public double confidenceHi()                </b>  <font color = gray>// high endpoint of 95% confidence interval</font>

<b>   public static void main(String[] args)      </b>  <font color = gray>// test client (described below)</font>
<b>}</b>
</pre>
</blockquote>

The constructor should throw a <tt>java.lang.IllegalArgumentException</tt> if either <em>n</em> &le; 0 or
<em>trials</em> &le; 0.
<p>
Also, include a <tt>main()</tt> method
that takes two <em>command-line arguments</em>
<em>n</em> and <em>T</em>, performs <em>T</em> independent
computational experiments (discussed above) on an <em>n</em>-by-<em>n</em> grid,
and prints the sample mean, sample standard deviation, and the 
<em>95% confidence interval</em> for the percolation threshold.
Use
<a href = "http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdRandom.html"><tt>StdRandom</tt></a>
to generate random numbers; use 
<a href = "http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdStats.html"><tt>StdStats</tt></a>
to compute the sample mean and sample standard deviation.

<blockquote>
<pre>
% <b>java PercolationStats 200 100</b>
mean                    = 0.5929934999999997
stddev                  = 0.00876990421552567
95% confidence interval = [0.5912745987737567, 0.5947124012262428]

% <b>java PercolationStats 200 100</b>
mean                    = 0.592877
stddev                  = 0.009990523717073799
95% confidence interval = [0.5909188573514536, 0.5948351426485464]


% <b>java PercolationStats 2 10000</b>
mean                    = 0.666925
stddev                  = 0.11776536521033558
95% confidence interval = [0.6646167988418774, 0.6692332011581226]

% <b>java PercolationStats 2 100000</b>
mean                    = 0.6669475
stddev                  = 0.11775205263262094
95% confidence interval = [0.666217665216461, 0.6676773347835391]
</pre>
</blockquote>



<p><b>Analysis of running time and memory usage (optional and not graded).</b>
Implement the <tt>Percolation</tt> data type using the <em>quick find</em> algorithm in
<a href ="http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/QuickFindUF.html"><tt>QuickFindUF</tt></a>.
<ul>
<p><li>
Use <a href ="http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/Stopwatch.html"><tt>Stopwatch</tt></a>
to measure the total running time of <tt>PercolationStats</tt> for various values of
<em>n</em> and <em>T</em>.
How does doubling <em>n</em> affect the total running time?
How does doubling <em>T</em> affect the total running time?
Give a formula (using tilde notation) of the total running
time on your computer (in seconds) as a single function of both
<em>n</em> and <em>T</em>.

<p><li>
Using the 64-bit memory-cost model from lecture,
give the total memory usage in bytes (using tilde notation) that a <tt>Percolation</tt>
object uses to model an <em>n</em>-by-<em>n</em> percolation system.     
Count all memory that is used, including memory for the union&ndash;find data structure.
</ul>

</ul>

<p>
Now, implement the <tt>Percolation</tt> data type using the <em>weighted quick union</em> algorithm in
<a href ="http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/WeightedQuickUnionUF.html"><tt>WeightedQuickUnionUF</tt></a>.
Answer the questions in the previous paragraph.


<p><b>Deliverables.</b>
Submit only <tt>Percolation.java</tt> (using the weighted quick-union algorithm 
from 
<a href ="http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/WeightedQuickUnionUF.html"><tt>WeightedQuickUnionUF</tt></a>)
and <tt>PercolationStats.java</tt>.
We will supply <tt>algs4.jar</tt>.
Your submission may not call library functions except those in
<a href ="http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdIn.html"><tt>StdIn</tt></a>,
<a href ="http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html"><tt>StdOut</tt></a>,
<a href ="http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdRandom.html"><tt>StdRandom</tt></a>,
<a href ="http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdStats.html"><tt>StdStats</tt></a>,
<a href ="http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/WeightedQuickUnionUF.html"><tt>WeightedQuickUnionUF</tt></a>,
and
<tt>java.lang</tt>.

<p><b>For fun.</b>
Create your own percolation input file and share it in the discussion forums.
For some inspiration, do an image search for "nonogram puzzles solved."

<p><br>


<ADDRESS><SMALL>
This assignment was developed by Bob Sedgewick and Kevin Wayne.
<br>Copyright &copy; 2008.
</SMALL>
</ADDRESS>
</BODY></HTML>

import java.util.Arrays;
import java.io.*;
import java.util.Random;

public class randomizedShellSortShellSort {
    public static final int C=1; // number of region compare-exchange repetitions
    public static void exchange(int[ ] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    public static void compareExchange(int[ ] a, int i, int j) {
        if (((i < j) && (a[i] > a[j])) || ((i > j) && (a[i] < a[j])))
        exchange(a, i, j);
    }
    public static void permuteRandom(int a[ ], Random rand) {
        for (int i=0; i<a.length; i++) // Use the Knuth random perm. algorithm
        exchange(a, i, rand.nextInt(a.length - i)+i);
    }
    // compare-exchange two regions of length offset each
    public static void compareRegions(int[ ] a, int s, int t, int offset, Random rand) {
        int mate[ ] = new int[offset]; // index offset array
        for (int count=0; count<C; count++) { // do C region compare-exchanges 
            for (int i=0; i<offset; i++) mate[i] = i;
            permuteRandom(mate,rand); // comment this out to get a deterministic Shellsort
            for (int i=0; i<offset; i++)
            compareExchange(a, s+i, t+mate[i]);
        }
    }
    public static void randomizedShellSort(int[ ] a) {
        int n = a.length; // we assume that n is a power of 2
        Random rand = new Random(); // random number generator (not shown)
        for (int offset = n/2; offset > 0; offset /= 2) {
            for (int i=0; i < n - offset; i += offset) // compare-exchange up
                {
                    // System.out.println(i);
                    compareRegions(a,i,i+offset,offset,rand);
                }

            for (int i=n-offset; i >= offset; i -= offset) // compare-exchange down
                {
                    // System.out.println(i);
                    compareRegions(a,i-offset,i,offset,rand);
                }

            for (int i=0; i < n-3*offset; i += offset) // compare 3 hops up
            {
                compareRegions(a,i,i+3*offset,offset,rand);
            }
   
            for (int i=0; i < n-2*offset; i += offset) // compare 2 hops up
                compareRegions(a,i,i+2*offset,offset,rand);

            for (int i=0; i < n; i += 2*offset) // compare odd-even regions
                compareRegions(a,i,i+offset,offset,rand); 

            for (int i=offset; i < n-offset; i += 2*offset) // compare even-odd regions
                compareRegions(a,i,i+offset,offset,rand);
        }
    }

    public static void main(String[] args) {
    

    try{ 
        // int length = 512;
        // int length = 8192;
        int length = 65536;
        int[] intArray = new int[length];
        String line;
        BufferedReader in;

        in = new BufferedReader(new FileReader("output3.txt"));
        line = in.readLine();
        int counter = 0;
        while(line != null)
        {
                intArray[counter] = Integer.parseInt(line);
                line = in.readLine();
        }
        in.close();

        
        // Sort the copy in ascending order
        
        int[] sortedArray = Arrays.copyOf(intArray, intArray.length);
        Arrays.sort(sortedArray);

        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long begin = System.currentTimeMillis();

        randomizedShellSort(intArray);

        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long end = System.currentTimeMillis();
        long time = end-begin;
        long actualMemUsed=(afterUsedMem-beforeUsedMem);

        System.out.println("Elapsed Time: "+time +" milli seconds");
        System.out.println("Memory Usage: "+actualMemUsed+ " KB");
        
        boolean flag = true;
        for (int i = 0 ; i<intArray.length ; i++) {
            if(sortedArray[i] != intArray[i]){
                System.out.println(i);
                System.out.println(sortedArray[i]);
                System.out.println(intArray[i]);
                flag = false;
            }
        }

        System.out.println(flag);
    }catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
    }	
}
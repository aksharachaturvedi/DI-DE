package Median;

import java.util.Comparator;

/**
 * Created by  on 3/8/15.
 */
    // Comparator that sorts integers from highest to lowest
    public class MaxHeapComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        if (o1 < o2) return 1;
        else if (o1 == o2) return 0;
        else return -1;
    }
}
package Median;
import java.io.*;
import java.util.*;

/**
 Two heaps are used to maintain the median at given time.
 Max heap has value below median and Min heap has value above median.
 When a new item is added it is placed in the max heap if the value is less than or equal to the median,
 otherwise it is placed into the min heap. The heap sizes can be equal or the below heap has one extra.
 Elements are shifted from one heap to the other to make sure the difference between two heaps is nver greater then 1.
 */

public class RunningMedianIntegers {

    private static Comparator<Integer> maxHeapComparator; //comparator for max heap
    private static Comparator<Integer> minHeapComparator; //comparator for min heap
    private static PriorityQueue<Integer> maxHeap;
    private static PriorityQueue<Integer> minHeap;

    //New Number is added in a heap based on the current value of the intermediate median
    public static void addNewIntegerInHeap(int incomingNumber) {
        //size of both the arrays are equal then
        if (maxHeap.size() == minHeap.size()) {
            if ((minHeap.peek() != null) && incomingNumber > minHeap.peek()) {
                maxHeap.offer(minHeap.poll()); //pop min heap and add the value in max heap
                minHeap.offer(incomingNumber); // add new number in min heap
            } else {
                maxHeap.offer(incomingNumber);  // if new number is smaller than min-heap's top value than add it in the maxheap
            }
        } else {
            if (incomingNumber < maxHeap.peek()) {
                minHeap.offer(maxHeap.poll());
                maxHeap.offer(incomingNumber);
            } else {
                minHeap.offer(incomingNumber);
            }
        }
    }

    //returns the median for the stream of numbers
    public static double getMedian() {
		// maxHeap >= minHeap. if maxHeap is empty, then minHeap is empty as well
        if (maxHeap.isEmpty()) {
            return 0;
        }
        //if the items in both the heaps are equal then take the average of two
        if (maxHeap.size() == minHeap.size()) {
            return ((double) minHeap.peek() + (double) maxHeap.peek()) / 2;
        } else {
			// If maxHeap and minHeap are of different sizes, then maxHeap must have one extra element.
            return maxHeap.peek();
        }
    }

    //sorts the files in a directory and get the count of words per line in a list
    public static List<Integer> getFileAndWordCount() throws FileNotFoundException, IOException {
        File file = new File("wc_input"); //directory with files

        File[] listOfFiles = file.listFiles();
        List<Integer>lineMedian = new ArrayList<Integer>();
        Arrays.sort(listOfFiles, new FileNameComparator()); //sort the files based on the name

        for (File filename : listOfFiles) { //go by files one by one
            Scanner in = new Scanner(filename);

            try {
                int words = 0;
                while (in.hasNext()) {
                    String line = in.nextLine().replaceAll("[^a-zA-Z ]", "").toLowerCase(); //remove the punctuations
                    words = line.split(" ").length; //split the words and get the count of the words
                    lineMedian.add(words);
                }
            }
            catch (Exception e) {
                System.err.println(e);
            }
        }
        return lineMedian;
    }

    public static void addNewIntAndMedianInList(int newNumber, List<Double> wordMedianList) {
        addNewIntegerInHeap(newNumber);
        wordMedianList.add(getMedian());
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
            maxHeapComparator = new MaxHeapComparator();
            minHeapComparator = new MinHeapComparator();

            List<Integer> medianPerLine = getFileAndWordCount();

            //set the default size of the queues
            maxHeap = new PriorityQueue<Integer>(medianPerLine.size() - medianPerLine.size() / 2, maxHeapComparator);
            minHeap = new PriorityQueue<Integer>(medianPerLine.size() / 2, minHeapComparator);
            List<Double> wordMedianList = new ArrayList<Double>();

            for (Integer medianNumber : medianPerLine) {
                addNewIntAndMedianInList(medianNumber, wordMedianList); // print add and print he median
            }
            writeToFile(wordMedianList); //write to a file
        }
        catch (Exception ex) {

        }
    }

    public static void writeToFile(List<Double> wordMedianList) {
        try {
            File file = new File("wc_output/med_result.txt");
            if (!file.exists()) {          // if file doesnt exists, then create it
                file.createNewFile();
            }

            FileWriter filewriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter buffwriter = new BufferedWriter(filewriter);

            for (Double median : wordMedianList) {
                buffwriter.write(median + "\n");
            }
            buffwriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}






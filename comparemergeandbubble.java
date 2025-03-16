import java.util.Random;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.Arrays;


public class comparemergeandbubble {

public static void main(String[] args) {
    String filename = "storearray.txt";
    String filename2 = "sortarray.txt";
    boolean run = true;
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("\nWelcome to the Bubble-Merge-Sort Machine!");

    System.out.println("\nAn array will be randomly generated using the integer you entered as the number of elements in the array.");
    System.out.println("For Example, entering '9' (without the quotation marks) generates an array of 9 random numbers).");
    System.out.println("\nThese numbers will then be sorted from smallest to largest using Bubblesort and Mergesort.");
    System.out.println("The time it took each array to be sorted will appear below each array in nanoseconds.");
    System.out.println("Note that your unsorted array will be stored to the 'storearray.txt' file and the sorted one will be stored to 'sortarray.txt'");

    while (run) {

    System.out.println("\nEnter any integer between 0 and 100 (inclusive) to generate your array.");
   
    System.out.println("\nEnter '-1' to exit.\n");

    int arraySize = scanner.nextInt();

    if ((arraySize < 0 || arraySize > 100) && (arraySize != -1)) {
        System.out.println("\nPlease enter a valid input between 0 and 100 (inclusive).\n");
    }
    else if (arraySize == -1) {
        System.out.println("\nExit successful.\n");
        run = false;
    }
    else if ((arraySize >= 0 && arraySize <= 100)) {

    int[] randomArray = createRandomArray(arraySize);

    writeArrayToFile(randomArray, filename);
        System.out.println("\nHere is your unsorted array with " + arraySize + " numbers in it:\n");
        for (int i = 0; i < randomArray.length; i++) {
            System.out.println(randomArray[i]);
        }

    int[] inputToArray = readFileToArray(filename);

    long beginBubbleSort = System.nanoTime();
    bubbleSort(inputToArray);
    long stopBubbleSort = System.nanoTime();
    long bubbleSortTime = stopBubbleSort - beginBubbleSort;

    writeArrayToFile2(inputToArray, filename2, "Bubblesort Time: " + bubbleSortTime + " nanoseconds.\n", true);
    System.out.println("\nHere is your bubblesorted array:\n");
        for (int i = 0; i < inputToArray.length; i++) {
            System.out.println(inputToArray[i]);
        }

        System.out.println("\nBubblesort time in nanoseconds: " + bubbleSortTime);
        System.out.println();

    inputToArray = Arrays.copyOf(randomArray, randomArray.length);

    long beginMergeSort = System.nanoTime();
    mergeSort(inputToArray, 0, inputToArray.length - 1);
    long stopMergeSort = System.nanoTime();
    long mergeSortTime = stopMergeSort - beginMergeSort;

    writeArrayToFile2(inputToArray, filename2, "Mergesort Time: " + mergeSortTime + " nanoseconds.\n", false);
    System.out.println("\nHere is your mergesorted array:\n");
        for (int i = 0; i < inputToArray.length; i++) {
            System.out.println(inputToArray[i]);
    }

    System.out.println("\nMergesort time in nanoseconds: " + mergeSortTime);
    System.out.println();

    }
    }

        scanner.close();

}

public static int[] createRandomArray(int arraySize) {
    Random random = new Random();
    int[] array = new int[arraySize];
    for (int i = 0; i < arraySize; i++) {
        array[i] = random.nextInt(101);
    }

    return array;
}

public static void writeArrayToFile(int[] array, String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (int i = 0; i < array.length; i++) {
        writer.write(Integer.toString(array[i]));
        writer.newLine();
        }
    }
    catch (IOException e) {
        System.out.println(e.getMessage());
    }

}

public static void writeArrayToFile2(int[] array, String filename, String time, boolean blank) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
        if (blank) {
            new FileWriter(filename, false).close();
        }
        writer.write(time);
        writer.newLine();
        for (int i = 0; i < array.length; i++) {
            writer.write(Integer.toString(array[i]));
            writer.newLine();
    }
            writer.newLine();
    }
    catch (IOException e) {
        System.out.println(e.getMessage());
    }
}


public static int[] readFileToArray(String filename) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        int[] array = new int[100];
        int increment = 0;
        String forLine;
        while ((forLine = reader.readLine()) != null) {
            array[increment] = Integer.parseInt(forLine);
            increment++;
        }
        return Arrays.copyOf(array, increment);
        }
    catch (IOException e) {
        System.out.println(e.getMessage());
        return new int[0];

    }
    }

public static void bubbleSort(int[] array) {
    int m = array.length;
    for (int i = 0; i < m - 1; i++) {
        for (int j = 0; j < m - 1 - i; j++) {
            if (array[j] > array[j + 1]) {
                int swap = array[j];
                array[j] = array[j + 1];
                array[j + 1] = swap;
            }
        }
        boolean ordered = true;
        for (int n = 0; n < m - 1 - i; n++) {
            if (array[n] > array[n + 1]) {
                ordered = false;
                break;
            }
        }
        if (ordered) break;

    }

}

public static void mergeSort(int[] array, int left, int right) {
    if (left < right) {
        int middle = (((right - left) / 2) + left);

        mergeSort(array, left, middle);
        mergeSort(array, middle + 1, right);

        mergeSort2(array, left, middle, right);

    }
}

public static void mergeSort2(int[] array, int left, int middle, int right) {
    int a = (middle - left) + 1;
    int b = right - middle;

    int[] L = new int[a];
    int[] R = new int[b];

    System.arraycopy(array, left, L, 0, a);
    System.arraycopy(array, middle + 1, R, 0, b);

    int i = 0;
    int j = 0;
    int k = left;

    while (i < a && j < b) {
        if (L[i] <= R[j]) {
        array[k] = L[i];
        i++;
    }
    else {
        array[k] = R[j];
        j++;
    }
        k++;
    }

    while (i < a) {
        array[k] = L[i];
        i++;
        k++;
    }
    while (j < b) {
        array[k] = R[j];
        j++;
        k++;
    }

}

}
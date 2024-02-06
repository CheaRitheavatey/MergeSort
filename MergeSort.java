import java.util.Arrays;
public class MergeSort {
    // method 1 to divide all the elements 
    public static void merge_Sort(int[] array) {
        // in order to divide array into half have to determine the length of left and right array 
        if (array.length < 2)
            return;

        int midIndex = array.length / 2;
        int[] leftArray = new int[midIndex];
        int[] rightArray = new int[array.length - midIndex];

        // put the value inside the left and right array correctly
        // for left array
        for (int i = 0; i < midIndex; i++)
            leftArray[i] = array[i];

        // for right array
        for (int i = midIndex; i < array.length; i++)
            rightArray[i - midIndex] = array[i];

        // recursive
        merge_Sort(leftArray);
        merge_Sort(rightArray);
        merge(array, leftArray, rightArray);
    }

    // method 2 to merge the sorted arrays
    public static void merge(int[] array, int[] leftArray, int[] rightArray) {
        // some index to help guide
        int i = 0; // pointer in the left array
        int j = 0; // pointer in the right array
        int k = 0; // pointer in the big array

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            }
            else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // put the left-over number in

        // for left array
        while (i < leftArray.length) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        // for right array
        while (j < rightArray.length) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
}



class ArraySorting {
    public static void main(String[] args) {
        int[] array = {7,5,3,1,4,7,2,6,8};

        System.out.println("before sorting: "+ Arrays.toString(array));
        MergeSort.merge_Sort(array);
        System.out.print("after sorting: " +Arrays.toString(array));

    }
}
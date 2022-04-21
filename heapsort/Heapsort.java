package pl.marcinbaranowski;

public class Heapsort {

    public static void sort(int []arrayToSort) {
        int n = arrayToSort.length;

        // Budujemy kopiec zaczynając od ostatniego ojca
        for(int i = n / 2 - 1; i >= 0; i--) {
            heapify(arrayToSort, n, i);
        }

        for(int i = n - 1; i > 0; i--) {
            swap(arrayToSort, 0, i); // zamieniamy element maksymlany znajdujący się w pozycji korzenia z ostatnim elementem
            --n; // po czym usuwamy wartość pod ostatnim korzeniem
            heapify(arrayToSort, n, 0); // po czym porządkujemy nasz nowy kopiec
        }
    }

    private static void heapify(int []array, int heapSize, int parentIndex) {
        int maxIndex = parentIndex; // ojciec
        int leftChild = parentIndex * 2 + 1; // lewy syn
        int rightChild = parentIndex * 2 + 2; // prawy syn

        // sprawdzamy czy lewy syn jest większy od ojca
        if(leftChild < heapSize && array[leftChild] > array[maxIndex]) {
            maxIndex = leftChild; // jeżeli jest to  maxIndex przypisujemy wartość lewego syna
        }

        // sprawdzamy czy prawy syn jest większy od rodzica
        if(rightChild < heapSize && array[rightChild] > array[maxIndex]) {
            maxIndex = rightChild; // jeżeli jest to maxIndex przypisujemy wartość prawego syna
        }

        if(maxIndex != parentIndex) { // jeżeli wartość maxIndex się zmieniła to zamieniamy go pozycjami z ojcem
            swap(array, maxIndex, parentIndex);
            heapify(array, heapSize, maxIndex); // za pomocą rekursji sprawdzamy czy nasz ojciec po zamianie jest w odpowiedniej pozycji
        }

    }

    private static void swap(int []array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}

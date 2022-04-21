package pl.marcinbaranowski;

public class HeapsortIterative {

    static void heapify(int arr[], int heapSize) {
        for (int i = 1; i < heapSize; i++)	{
            // jeżeli syn jest większy od ojca
            if (arr[i] > arr[(i-1)/2]) {
                int j = i;
                // to zamieniamy ich miejscami
                // dopoki rodzic jest mniejszy
                while (arr[j] > arr[(j-1)/2]) {
                    swap(arr, j, (j-1)/2);
                    j = (j-1)/2;
                }
            }
        }
    }

    static void sort(int arr[], int n) {
        heapify(arr, n);

        for (int i = n - 1; i > 0; i--) {
            // zamieniamy miejscami max wartosć znajdującą się w pierwszym indeksie
            // z wartością znajdującą się w ostatnim indeksie
            swap(arr, 0, i);

            // po każdej zamianie sprawdzamy czy własności kopca są zachowane
            int j = 0, index;

            do {
                index = (2 * j + 1); // pod zmienną index zapisujemy lewego syna

                // jeżeli lewy syn jest mniejszy od prawego syna
                // sprawiamy aby zmienna index wskazywała
                // na prawego syna
                if (index < (i-1) && arr[index] < arr[index+1])
                    index++;
                // jeżeli ojciec jest mniejszy od któregoś z synów
                // zamieniamy ich miejscami
                // z tym synem który jest większy
                if (index < i && arr[j] < arr[index])
                    swap(arr, j, index);
                j = index;
            } while (index < i);
        }
    }

    private static void swap(int []array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}




package pl.marcinbaranowski;



import java.io.*;
import java.util.*;
import java.lang.*;

    public class Main {
        public static void bubbleSort(Integer[] arr, int n) {
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (arr[j] > arr[j + 1]) {
                        swap(arr, j, j+1);
                    }
        }

        public static void quickSort(Integer[] arr, int p, int r) {
            if (p < r) {
                int q = partition(arr, p, r);
                quickSort(arr, p, q);
                quickSort(arr, q + 1, r);
            }
        }

        public static int partition(Integer[] arr, int p, int r) {
            int x = arr[r];
            int i = p;
            for (int j = 0; j < r; j++) {
                if (arr[j] < x) {
                    swap(arr, i, j);
                    i++;
                }
            }
            int temp = arr[r];
            arr[r] = arr[i];
            arr[i] = temp;
            return i;
        }

        public static void swap(Integer[] arr, int a, int b) {
            int temp;
            temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }

        public static void main(String[] args) {
            int[] size = {100, 500, 1000, 5000};
            long[][] res = new long[4][4];
            long begin, end;

            for (int i = 0; i < size.length; i++) {
                Integer[] arr = new Integer[size[i]];
                Arrays.fill(arr, new Random().nextInt());
                Integer dup[] = arr;
                Integer dupDesc[] = arr;
                Integer dupDesc2[] = arr;
                Arrays.sort(dupDesc, Collections.reverseOrder());
                Arrays.sort(dupDesc2, Collections.reverseOrder());

                begin = System.nanoTime();
                quickSort(arr, 0, size[i]-1);
                end = System.nanoTime();
                res[0][i] = (end - begin);

                begin = System.nanoTime();
                quickSortDeluxe(dup, 0, size[i]-1);
                end = System.nanoTime();
                res[1][i] = (end - begin);

                begin = System.nanoTime();
                quickSort(dupDesc, 0, size[i]-1);
                end = System.nanoTime();
                res[2][i] = (end - begin);

                begin = System.nanoTime();
                quickSortDeluxe(dupDesc2, 0, size[i]-1);
                end = System.nanoTime();
                res[3][i] = (end - begin);
            }
            System.out.println("Wyniki działania programu:");
            System.out.println();
            System.out.println("Rozmiar tablicy n=100:\nDane korzystne|QuickSort: "+res[0][0]+"\nDane korzystne|QuickSort+: "+res[1][0]+"\nDane niekorzystne|QuickSort: "+res[2][0]+"\nDane niekorzystne|QuickSort+: "+res[3][0]);
            System.out.println();
            System.out.println("Rozmiar tablicy n=500:\nDane korzystne|QuickSort: "+res[0][1]+"\nDane korzystne|QuickSort+: "+res[1][1]+"\nDane niekorzystne|QuickSort: "+res[2][1]+"\nDane niekorzystne|QuickSort+: "+res[3][1]);
            System.out.println();
            System.out.println("Rozmiar tablicy n=1000:\nDane korzystne|QuickSort: "+res[0][2]+"\nDane korzystne|QuickSort+: "+res[1][2]+"\nDane niekorzystne|QuickSort: "+res[2][2]+"\nDane niekorzystne|QuickSort+: "+res[3][2]);
            System.out.println();
            System.out.println("Rozmiar tablicy n=5000:\nDane korzystne|QuickSort: "+res[0][3]+"\nDane korzystne|QuickSort+: "+res[1][3]+"\nDane niekorzystne|QuickSort: "+res[2][3]+"\nDane niekorzystne|QuickSort+: "+res[3][3]);
        }
    }


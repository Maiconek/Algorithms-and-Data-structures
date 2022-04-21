import java.util.*;

public class Main {
    static void radixSort(String[] arr) {
        if(arr.length > 1) {
            //Szukamy najdłuższego stringa
            int maxLength = 0;
            for(int i = 0; i < arr.length; i++) {
                if(arr[i].length() > maxLength)
                    maxLength = arr[i].length();
            }

            System.out.println("Największa długość : " + maxLength);

            //Zmieniamy długość stringów na taką samą
            for(int STRIndex = 0; STRIndex < arr.length; STRIndex++) {
                if(arr[STRIndex].length() < maxLength) {
                    while(arr[STRIndex].length() < maxLength) {
                        arr[STRIndex] = arr[STRIndex] + " ";
                    }
                    System.out.println("Długość stringa po zmianie: [" + arr[STRIndex] + "] " + "Długość: " + arr[STRIndex].length());
                }
            }

            //Sortujemy litera po literze w odwrotnej kolejności
            for (int column = maxLength - 1; column >= 0; column--) {
                System.out.println("Sorting Column: " );
                countingSort(arr, column);
            }

            //Wypisujemy wynik
            System.out.print("\nPOSORTOWANA TABLICA: \n");
            print(arr);

        }
    }

    static void countingSort(String[] arr, int letterColumn) {
        int NUM_CHARACTERS = 37; //Liczba roznych znaków jakie mozemy znaleźć w stringu

        //Tworzymy mapę która bierze char jako klucz, i liczbe jako wartość
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        // Liczby jako char
        map.put(' ', 0); map.put('0',1); map.put('1',2); map.put('2',3); map.put('3',4); map.put('4',5); map.put('5',6); map.put('6',7);
        map.put('7',8); map.put('8',9); map.put('9',10);
        // i litery
        map.put('a',11); map.put('b',12); map.put('c',13);  map.put('d',14); map.put('e',15); map.put('f',16); map.put('g',17);
        map.put('h',18); map.put('i',19); map.put('j',20); map.put('k',21); map.put('l',22); map.put('m',23); map.put('n',24);
        map.put('o',25); map.put('p',26); map.put('q',27); map.put('r',28); map.put('s',29); map.put('t',30); map.put('u',31);
        map.put('v',32);map.put('w',33); map.put('x',34); map.put('y',35); map.put('z',36);

        String[] result = new String[arr.length];
        int[] count = new int[NUM_CHARACTERS];

        print(arr);

        //Zapisujemy liczbe powtórzeń każdego znaku w danej kolumnie dla kazdego napisu
        for(int index = 0; index < arr.length; index++) {
            String currentString = arr[index];//weź aktualnego stringa
            int currentChar = map.get(currentString.charAt(letterColumn)); //bierzemy aktualną wartość int danego znaku
            count[currentChar]++; //liczymy instancje
        }

        for(int index = 1; index < NUM_CHARACTERS; index++) {
            count[index] += count[index-1];
        }

        for(int index = arr.length - 1; index >= 0; index--) {
            String currentString = arr[index]; //weź aktualnego stringa
            int currentChar = map.get(arr[index].charAt(letterColumn));//bierzemy aktualną wartość int danego znaku

            result[count[currentChar]-1] = arr[index]; //umieść znak, aby go posortować
            count[currentChar]--;//odejmij ten znak z licznika
        }

        // Kopiujemy starą tablice do nowej
        System.arraycopy(result, 0, arr, 0, arr.length);

    }


    static void print(String[] arr) {
        for (String s : arr) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String[] arr = {"marcin", "piotr", "pawel", "anna", "zenon", "robert", "nikodem"};
        System.out.print("Nieposortowana tablica: ");
        print(arr);
        radixSort(arr);
    }

}


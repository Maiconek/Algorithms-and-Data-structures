package pl.marcinbaranowski;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

class Hashing {
    static String[] readToArray(String filepath, int size) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filepath));           //utworzenie BufferedReader
        String[] output = new String[size];                                         //utworzenie listy wyjściowej
        for(int i = 0; i < size; i++) {                                             //wczytanie 2*m linii do listy
            output[i] = br.readLine();
        }
        br.close();                                                                 //zamknięcie pliku
        return output;                                                              //zwraca listę słów
    }

    static int[] convert(String[] vals) {
        int[] output = new int[vals.length];          //utworzenie listy wyjściowej
        char[] val;                                   //tablica znakowa do rozbijania każdego słowa na litery
        for(int i = 0; i < vals.length; i++){         //do osiągnięcia rozmiaru tablicy
            val = vals[i].toCharArray();              //wczytanie słowa do tablicy znaków
            for(int j = 0; j < val.length; j++) {     //dla każdej litery słowa
                output[i] += (j+1)*((int) val[j]);    //słowo(n liter) = 1*(wartość ASCII 1. litery) + 2*(wartość ASCII 2.litery) + ... + n*(wartość ASCII n-tej litery)
            }
        }
        return output;                                      //zwraca listę wartości słów
    }

    static void countCols(int[] T, int[] vals, int mod) {   //zliczanie kolizji na poszczególnych pozycjach
        int res;
        for(int i = 0; i < vals.length; i++) {
            res = vals[i]%mod;
            T[res]++;
        }
    }

    static int countEmpty(int[] T){
        int result = 0;                                     //zmienna z wynikiem zliczania pustych pozycji
        for(int i = 0; i < T.length; i++) {                 //na całej długości tablicy
            if(T[i]==0) result++;                           //jeśli wartość T[i] == 0 to zwiększa zmienną wynikową
        }
        return result;                                      //zwraca ilość pustych pozycji
    }

    static int maxVal(int[] T) {
        int maxVal = 0;                                     //zmienna z wynikiem, czyli największą ilością kolizji
        for(int i = 0; i < T.length; i++){                  //na całej długości tablicy
            if(maxVal < T[i]) maxVal = T[i];                //jeśli zmienna wynikowa jest mniejsza od wartości danego elementu tablicy to przyjmuje wartość tego elementu
        }
        return maxVal;                                      //zwraca największą ilość kolizji
    }

    static double avgVal(int[] T) {
        int sum = 0;                                        //suma kolizji
        int count = 0;                                      //niepuste pozycje tablicy
        for(int i = 0; i < T.length; i++) {
            if(T[i] != 0) {                                 //jeśli pozycja jest niepusta
                sum += T[i];                                //suma+=wartość pozycji
                count++;                                    //zwiększ ilość niepustych pozycji
            }
        }
        double result = ((double)sum)/((double)count);      //średnia ilość kolizji na niepustych pozycjach
        return result;                                      //zwraca średnią
    }

    public static void main(String args[]) throws IOException {
        String file = "src/3700.txt";
        int m1 = 1117;                                                  //modulo korzystne
        int m2 = 1200;                                                  //modulo niekorzystne
        int[] T_1 = new int[m1];                                        //T[m] dla korzystnego modulo
        int[] T_2 = new int[m2];                                        //T[m] dla niekorzystnego modulo
        Arrays.fill(T_1, 0);                                        //wypełnienie T_1 i T_2 zerami
        Arrays.fill(T_2, 0);
        int[] vals_1 = new int[2*m1];                                   //listy 2*m wartości słów dla obu modulo
        int[] vals_2 = new int[2*m2];

        vals_1 = convert(readToArray(file, 2*m1));                  //wstawienie wartości do list
        vals_2 = convert(readToArray(file, 2*m2));

        countCols(T_1, vals_1, m1);                                     //zliczanie kolizji
        countCols(T_2, vals_2, m2);

        System.out.println("Dane dla m = 1117 (liczba pierwsza)");             //wydruk wyników
        System.out.println("Puste miejsca: "+countEmpty(T_1));
        System.out.println("Najwięcej kolizji: "+maxVal(T_1));
        System.out.println("Średnia ilość kolizji: "+avgVal(T_1));
        System.out.println();

        System.out.println("Dane dla m = 1200 (niekorzystna)");
        System.out.println("Puste miejsca: "+countEmpty(T_2));
        System.out.println("Najwięcej kolizji: "+maxVal(T_2));
        System.out.println("Średnia ilość kolizji: "+avgVal(T_2));
        System.out.println();
    }
}

package pl.marcinbaranowski;

public class Main {

    // funkcja szukająca NWP
    public static String LCS(String X, String Y, int m, int n, int[][] T) {
        // jeśli któryś ciąg się skończył zwróć pusty string
        if (m == 0 || n == 0)
            return new String();

        // jeśli ostatnie znaki X i Y są takie same dołącz ten znak do NWP
        if (X.charAt(m - 1) == Y.charAt(n - 1))
            return LCS(X, Y, m - 1, n - 1, T) + X.charAt(m - 1);

        // w przeciwnym przypadku, jeśli w tabelce kratka wyżej jest większa od kratki po lewej, weź kolejny znak X i szukaj dalej
        if (T[m - 1][n] > T[m][n - 1])
            return LCS(X, Y, m - 1, n, T);

        // jeśli w tabelce kratka po lewej jest większa od kratki wyżej, weź kolejny znak Y i szukaj dalej
        else
            return LCS(X, Y, m, n - 1, T);
    }

    // funkcja do wypełnienia tabeli
    public static void LCSLength(String X, String Y, int m, int n, int[][] T) {
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // jeśli znaki się zgadzają weź wartość kratki po skosie z lewej u góry i dodaj 1
                if (X.charAt(i - 1) == Y.charAt(j - 1)) T[i][j] = T[i - 1][j - 1] + 1;
                // w przeciwnym przypdaku przekopiuj większą wartość z kratki u góry lub z lewej
                else {T[i][j] = Integer.max(T[i - 1][j], T[i][j - 1]);}
            }
        }
    }

    public static void main(String[] args) {
        String x = "abbaac";                                        // ciągi z zajęć
        String y = "bacbacba";

        int[][] t = new int[x.length() + 1][y.length() + 1];        // tabela do wyliczenia długości NWP
        LCSLength(x, y, x.length(), y.length(), t);                 // wypełnia tabelę
        System.out.println("NWP (abbaac, bacbacba):");
        System.out.print(LCS(x, y, x.length(), y.length(), t));     // drukuje NWP


        String a = "bccbb";                                         // ciągi ze sprawdzianu
        String b = "ccbcb";
        System.out.println();
        System.out.println("NWP (bccbb, ccbcb):");
        int[][] arr = new int[a.length() + 1][b.length() + 1];
        LCSLength(a, b, a.length(), b.length(), arr);
        System.out.print(LCS(a, b, a.length(), b.length(), t));//
    }
}
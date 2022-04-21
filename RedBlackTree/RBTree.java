package pl.marcinbaranowski;


// węzeł
class Node {
    int key;                    // przechowuje klucz
    Node parent, left, right;   // wskaźniki na sąsiadujące węzły
    boolean color;              // true - czerwony, false - czarny
}


// drzewo czerwono-czarne
public class RBTree {
    private Node root;          // korzeń
    private Node tNull;         // wartownik

    public RBTree() {     // inicjacja nowego drzewa
        tNull = new Node();     // tworzy węzeł - wartownika
        tNull.color = false;    // wartownik jest czarny
        tNull.left = null;      // nie wskazuje na żadne węzły
        tNull.right = null;
        root = tNull;           // korzeń na start jest wartownikiem
    }

    // wstawianie węzła
    public void insert(int key) {
        // utworzenie nowego węzła
        Node node = new Node();
        node.parent = null;
        node.key = key;
        node.left = tNull;
        node.right = tNull;
        node.color = true;      // nowy węzeł jest czerwony

        Node y = null;          // y = pusty węzeł
        Node x = this.root;     // x = wskazanie na korzeń drzewa

        while (x != tNull) {                        // dopóki x nie trafi na puste miejsce, tj. wartownika
            y = x;                                  // niech y = x
            if (node.key < x.key) {x = x.left;}     // jeśli klucz wprowadzany jest mniejszy od klucza wskazywanego przez x, niech x = wskazanie na lewe dziecko x
            else {x = x.right;}                     // w przeciwnym przypadku niech x = wskazanie na prawe dziecko x
        }

        node.parent = y;                                // rodzicem wprowadzanego klucza będzie y

        if (y == null) root = node;                     // jeśli y = null, wprowadzany klucz będzie korzeniem
        else if (node.key < y.key) y.left = node;       // w przeciwnym przypadku jeśli wprowadzany klucz jest mniejszy od rodzica, będzie jego lewym dzieckiem
        else y.right = node;                            // w przeciwnym przypadku będzie jego prawym dzieckiem

        if (node.parent == null){
            node.color = false;         // jeśli wprowadzony klucz jest korzeniem zakończ wstawianie i zmien kolor na czarny
            return;
        }

        if (node.parent.parent == null) {return;}       // jeśli wstawiany klucz jest dzieckiem korzenia - zakończ

        fixInsert(node);        // naprawa drzewa jeśli wprowadzony klucz jest dalej niż dzieckiem korzenia
    }

    // metoda naprawiająca drzewo
    private void fixInsert(Node k) {

        Node u;                 // węzeł do wskazania "wujka"

        while (k.parent.color) {                        // jeśli rodzic k jest czerwony
            if (k.parent == k.parent.parent.right) {        // jeśli rodzic k jest prawym dzieckiem dziadka
                u = k.parent.parent.left;                       // wujkiem k jest lewe dziecko dziadka
                if (u.color) {                                  // jeśli wujek k jest czerwony
                    u.color = false;                                // wujek -> czarny
                    k.parent.color = false;                         // ojciec -> czarny
                    k.parent.parent.color = true;                   // dziadek -> czerwony
                    k = k.parent.parent;                            // niech k wskazuje na dziadka
                } else {                                        // w przeciwnym przypadku
                    if (k == k.parent.left) {                       // jeśli k jest lewym dzieckiem
                        k = k.parent;                                   // niech k wskazuje ojca
                        rightRotate(k);                                 // obróć w prawo na węźle k
                    }
                    k.parent.color = false;                         // ojciec k -> czarny
                    k.parent.parent.color = true;                   // dziadek k -> czerwony
                    leftRotate(k.parent.parent);                    // obróć w lewo na dziadku k
                }
            } else {                                        // w przeciwnym przypadku
                u = k.parent.parent.right;                      // wujkiem k jest prawe dziecko dziadka
                if (u.color) {                                  // jeśli wujek k jest czerwony
                    u.color = false;                                // wujek -> czarny
                    k.parent.color = false;                         // ojciec -> czarny
                    k.parent.parent.color = true;                   // dziadek -> czerwony
                    k = k.parent.parent;                            // niech k wskazuje na dziadka
                } else {                                        // w przeciwnym przypadku
                    if (k == k.parent.right) {                      // jeśli k jest prawym dzieckiem
                        k = k.parent;                                   // niech k wskazuje na ojca
                        leftRotate(k);                                  // obróć w lewo na węźle k
                    }
                    k.parent.color = false;                         // ojciec k -> czarny
                    k.parent.parent.color = true;                   // dziadek k -> czerwony
                    rightRotate(k.parent.parent);                   // obróć w prawo na dziadku k
                }
            }
            if (k == root) {        // jeśli k wskazuje na korzeń przerwij pętlę
                break;
            }
        }
        root.color = false;         // zmieniamy kolor korzenia na czarny
    }

    // obrót w lewo na węźle x
    public void leftRotate(Node x) {
        Node y = x.right;                                   // y = prawe dziecko x
        x.right = y.left;                                   // prawe dziecko x = lewe dziecko y
        if (y.left != tNull) {y.left.parent = x;}           // jeśli lewe dziecko y nie jest wartownikien -> rodzic lewego dziecka y = x
        y.parent = x.parent;                                // rodzic y = rodzic x
        if (x.parent == null) {this.root = y;}              // jeśli x to korzeń -> korzeń = y
        else if (x == x.parent.left) {x.parent.left = y;}   // jeśli x jest lewym dzieckiem -> lewe dziecko rodzica x = y
        else {x.parent.right = y;}                          // odbicie lustrzane poprzedniej opcji
        y.left = x;                                         // lewe dziecko y = x
        x.parent = y;                                       // rodzic x = y
    }

    // obróć w prawo na węźle x
    public void rightRotate(Node x) {
        Node y = x.left;                                    // y = lewe dziecko x
        x.left = y.right;                                   // lewe dziecko x = prawe dziecko y
        if (y.right != tNull) {y.right.parent = x;}         // jeśli prawe dziecko y nie jest wartownikiem -> rodzic prawego dziecka y = x
        y.parent = x.parent;                                // rodzic y = rodzic x
        if (x.parent == null) {this.root = y;}              // jeśli x to korzeń -> korzeń = y
        else if (x == x.parent.right) {x.parent.right = y;} // jeśli x jest prawym dzieckiem// prawe dziecko rodzica x = y
        else {x.parent.left = y;}                           // odbicie lustrzane poprzedniej opcji
        y.right = x;                                        // prawe dziecko y = x
        x.parent = y;                                       // rodzic x = y
    }

    // drukowanie w formie drzewa
    public void print() {printHelper(this.root, "", true);}   // wywołaj pomocnika na korzeniu, bez wcięcia, z oznaczeniem że ostatni
    private void printHelper(Node root, String indent, boolean last) {
        if (root != tNull) {                        // jeśli korzeń nie jest wartownikiem/nie jest pusty
            System.out.print(indent);               // drukuj wcięcie
            if (last) {                             // jeśli ostatni
                System.out.print("R----");          // drukuj (...)
                indent += "     ";                  // popraw wcięcie
            } else {                                // w przeciwnym wypadku
                System.out.print("L----");          // drukuj (...)
                indent += "|    ";                  // popraw wcięcie i daj kreskę
            }
            String sColor = root.color ? "Red" : "Black";             // w sColor zapisz kolor węzła w formie tekstowej
            System.out.println(root.key + "(" + sColor + ")");  // drukuj klucz(kolor)
            printHelper(root.left, indent, false);              // wywołaj się na lewym dziecku, przekaż wcięcie, bez oznaczenia że ostatni
            printHelper(root.right, indent, true);              // wywołaj się na prawym dziecku, przekaż wcięcie, z oznaczeniem że ostatni
        }
    }

    // najmniejsza głębokość drzewa
    public int minDepth() {return minDepHelper(root, 0);}  // wywołuje pomocnika na korzeniu, początkowa głębokość = 0
    private int minDepHelper(Node root, int level) {
        if (root == tNull) {return level;}                                                   // jeśli korzeń/węzeł jest wartownikiem zwróć głębokość
        level++;                                                                             // zwiększ głębokość
        return Math.min(minDepHelper(root.left, level), minDepHelper(root.right, level));    // zwróć najmniejszą wartość z kolejnych wywołań
    }

    // największa głębokość drzewa (wysokość drzewa)
    public int maxDepth() {return maxDepHelper(root, 0);}  // analogicznie do poprzedniej funkcji
    private int maxDepHelper(Node root, int level) {
        if (root == null) {return level;}
        level++;
        return Math.max(maxDepHelper(root.left, level), maxDepHelper(root.right, level));   // zwraca największą wartość z kolejnych wywołań
    }

    // zlicza czerwone
    public int red() {return redHelp(root);}    // wywołuje pomocnika na korzeniu
    private int redHelp(Node node) {
        if(node == tNull) {return 0;}                                           // jeśli węzeł jest wartownikiem zwróć 0
        if(node.color) {return 1 + redHelp(node.left) + redHelp(node.right);}   // jeśli węzeł jest czerwony zwróć 1 + ilość czerwonych w poddrzewach
        else {return redHelp(node.left) + redHelp(node.right);}                 // jeśli węzeł jest czarny zwróć ilość czerwonych w poddrzewach
    }

    // funkcja testująca
    public static void main(String [] args){
        RBTree tree = new RBTree();
        System.out.println("Zadanie 8.3.1");
        System.out.println();

        tree.insert(38);
        tree.insert(31);
        tree.insert(22);
        tree.insert(8);
        tree.print();
        System.out.println();

        tree.insert(4);
        System.out.println("Wstawiamy klucz 4\n");
        tree.print();
        System.out.println();

        tree.insert(20);
        System.out.println("Wstawiamy klucz 20\n");
        tree.print();
        System.out.println();

        tree.insert(5);
        System.out.println("Wstawiamy klucz 5\n");
        tree.print();
        System.out.println();

        tree.insert(3);
        System.out.println("Wstawiamy klucz 3\n");
        tree.print();
        System.out.println();

        tree.insert(10);
        System.out.println("Wstawiamy klucz 10\n");
        tree.print();
        System.out.println();

        tree.insert(9);
        System.out.println("Wstawiamy klucz 9\n");
        tree.print();
        System.out.println();

        /*tree.insert(21);
        tree.insert(27);
        tree.insert(29);
        tree.insert(25);
        tree.insert(28);
        tree.print();
        System.out.println();*/

        System.out.println("Zadanie 8.3.2");
        System.out.println("MAŁE DANE");
        System.out.println("Głębokość minimalna: " + tree.minDepth());
        System.out.println("Głębokość maksymalna: " + tree.maxDepth());
        System.out.println("Ilość czerwonych węzłów: " + tree.red());
        System.out.println();

        RBTree bigTree = new RBTree();
        for(int i = 1; i <= 10000; i++) {
            bigTree.insert(i);
        }

        System.out.println("DUŻE DANE");
        System.out.println("Głębokość minimalna: " + bigTree.minDepth());
        System.out.println("Głębokość maksymalna: " + bigTree.maxDepth());
        System.out.println("Ilość czerwonych węzłów: " + bigTree.red());
        System.out.println();

    }
}
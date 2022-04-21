package pl.marcinbaranowski;

public class DoublyLinkedList {
    Node start = new Node();

    DoublyLinkedList(){
        clear(this);    //tworzy pustą listę z wartownikiem
    }

    static class Node {
        String data;        //zawartość węzła
        Node next, prev;    //wskaźniki na kolejny i poprzedni węzeł
    }

    static void insertEnd(DoublyLinkedList list, String value) {
        if (list.start.next == null) {                          //jeśli lista jest pusta
            Node new_node = new Node();                         //tworzy nowy węzeł
            new_node.data = value;                              //wstawia wartość
            new_node.next = new_node.prev = list.start;         //wstawia go na koniec listy
        } else {                                                //jeśli list nie jest pusta
            Node last = list.start.prev;                            //znajduje ostatni węzeł
            Node new_node = new Node();                             //tworzy nowy węzeł
            new_node.data = value;                                  //wstawia wartość
            new_node.next = list.start;                             //wskaźnik na kolejny = wartownik
            new_node.prev = last;                                   //wskaźnik na poprzedni = ostatni element listy
            list.start.prev = last.next = new_node;                 //wstawienie na koniec listy
        }
    }

    static void insertBegin(DoublyLinkedList list, String value) {
        Node first = list.start.next;                   //znajduje pierwszy węzeł po wartowniku
        Node new_node = new Node();                     //tworzy nowy węzeł
        new_node.data = value;                          //wstawia wartosć
        new_node.next = first;                          //wskaźnik na kolejny = pierwszy węzeł
        new_node.prev = list.start;                     //wskaźnik na poprzedni = wartownik
        first.prev = list.start.next = new_node;        //wstawienie na początku (za wartownikiem, przed pierwszym węzłem)
    }

    static void print(DoublyLinkedList list) {
        if(list.start.next == list.start)
            System.out.println("Lista jest pusta.");  //jeśli lista jest pusta to koniec
        else {                                                                      //jeśli nie jest pusta
            Node current = list.start.next;                                         //wskaźnik na pierwszy element listy
            while (current != list.start) {                                         //dopóki wskaźnik nie ustawi się na wartowniku
                System.out.print(current.data+" ");                                 //drukuje wartość węzła
                current = current.next;                                             //wskaż kolejny węzeł
            }
            System.out.println();
        }
    }

    static void remove(DoublyLinkedList list, String key){
        Node current = list.start.next;                                         //wskaźnik na pierwszy element listy
        while(current.data != key) {                                            //dopóki wartość wskaźnika nie będzie równa szukanemu wyrażeniu
            if(current == list.start) {                                         //sprawdza czy nie wskazuje na wartownika
                System.out.println("Lista nie zawiera węzła \'"+key+"\'.");     //jeśli tak to znaczy że przeszukał całą listę
                return;                                                         //lista nie zawiera szukanego wyrażenia, koniec
            }                                                                   //jeśli nie wskazuje na wartownika to przesuwa sie na kolejny element listy
            current = current.next;
        }                                                                       //jeśli znalazł to
        (current.next).prev = current.prev;                                     //przesuwa wskaźniki sąsiadujących z wyszukiwanym wyrażeniem na siebie
        (current.prev).next = current.next;
        System.out.println("Węzeł \'"+key+"\' został usunięty.");               //tym samym węzeł zostaje usunięty z listy i zwalnia pamięć
    }

    static int search(DoublyLinkedList list, String key){
        Node current = list.start.next;                     //wskaźnik na pierwszy element listy
        int pointer = 0;                                    //pozycja szukanego węzła
        while(current.data != key){                         //dopóki wartosć wskaźnika nie bedzie równa szukanemu wyrażeniu
            pointer++;                                      //zwiększa pozycję
            if(current == list.start) {                     //sprawdza czy nie wskazuje na wartownika
                return -1;                                  //jeśli tak to zwraca -1, ponieważ lista nie zawiera szukanego wyrażenia
            }
            current = current.next;                         //jeśli nie wskazuje na wartownika to przesuwa sie na kolejny węzeł
        }
        return pointer;                                     //jeśli znajdzie szukane wyrażenie to zwraca jego pozycję
    }

    static void clear(DoublyLinkedList list){
        list.start.data = null;                             //czyści listę poprzez utworzenie wartownika ze wskazaniem na siebie samego
        list.start.next = list.start.prev = list.start;
    }

    static DoublyLinkedList norep(DoublyLinkedList list) {
        DoublyLinkedList output = new DoublyLinkedList();   //tworzy listę wyjściową
        Node current = list.start.next;                     //tworzy wskaźnik na pierwszy węzeł listy wejściowej
        while(current != list.start) {                      //dopóki wskaźnik nie ustawi się na wartownika listy wejściowej
            if(search(output, current.data) == -1) {        //szuka wartości węzła z pierwszej listy w drugiej liście
                insertEnd(output, current.data);            //jeśli go nie znajdzie to wstawia go do drugiej listy
            }
            current = current.next;                         //przesunięcie wskaźnika na kolejny węzęł
        }
        return output;                                      //zwraca listę wyjściową
    }

    static void merge(DoublyLinkedList first, DoublyLinkedList second) {
        Node current = (second.start).next;                 //tworzy wskaźnik na pierwszy element drugiej listy
        while (current != second.start) {                   //dopóki nie wskaże na wartownika drugiej listy
            insertEnd(first, current.data);                  //wstawia węzły do pierwszej listy
            current = current.next;                          //przesunięcie wskaźnika na kolejny węzeł
        }
        clear(second);                                      //czyszczenie drugiej listy
    }

    static void deleteList(DoublyLinkedList list) {
        list.start = null;
    }

    public static void main(String[] args){
        DoublyLinkedList list = new DoublyLinkedList();
        DoublyLinkedList list2 = new DoublyLinkedList();

        System.out.println("Wstawianie: ");
        insertBegin(list, "i struktury");
        print(list);
        insertEnd(list, "danych");
        print(list);
        insertBegin(list, "Algorytmy");
        print(list);
        System.out.println();


        String searchkey = "danych";
        System.out.println("Wyszukiwanie: ");
        if(search(list, searchkey) == -1)
            System.out.println("Lista nie zawiera węzła \'"+searchkey+"\'.");
        else
            System.out.println("Lista zawiera węzeł \'"+searchkey+"\' na pozycji: "+search(list, searchkey));

        searchkey = "matematyka";
        if(search(list, searchkey) == -1)
            System.out.println("Lista nie zawiera węzła \'"+searchkey+"\'.");
        else
            System.out.println("Lista zawiera węzeł \'"+searchkey+"\' na pozycji: "+search(list, searchkey));
        System.out.println();

        System.out.println("Usuwanie: ");
        remove(list, "danych");
        print(list);
        System.out.println();

        System.out.println("Kopia bez powtórzeń: ");
        list2 = norep(list);
        print(list2);
        System.out.println();

        System.out.println("Scalanie: ");
        merge(list, list2);
        print(list);
        print(list2);
        System.out.println();

        System.out.println("Czyszczenie listy: ");
        clear(list);
        print(list);
        System.out.println();
    }
}
# zwraca boolean czy zawartość listy jest większa od a
def content_greater(arr, a):
    for elem in arr:
        if elem < a:
            return False
    return True


# zwraca boolean czy zawartość listy jest mniejsza od a
def content_less(arr, a):
    for elem in arr:
        if elem > a:
            return False
    return True


# zwraca boolean czy zawartość listy mieści się w przedziale od a do b
def content_between(arr, a, b):
    for elem in arr:
        if elem < a or elem > b:
            return False
    return True


# obiekt węzła
class Node:
    def __init__(self):
        self.keys = []      # klucze
        self.child = []     # dzieci

    # własność "czy liść"
    @property
    def leaf(self):
        if len(self.child) == 0:
            return True
        else:
            return False

    # reprezentacja dla str()
    def __str__(self):
        res = "|"
        for i in self.keys:
            res += str(i) + "|"
        return res


# obiekt B-drzewa
class BTree:
    def __init__(self, t=None):
        self.root = Node()          # korzeń
        self.t = t                  # stopień minimalny

    # drukowanie drzewa - recykling
    def print(self, node=None, indent=""):
        # jeśli nie podano węzła, zacznij od korzenia
        if node is None:
            node = self.root
        
        # wypisz wcięcie
        print(indent, end="")

        # popraw wcięcie
        indent += "| "

        # wypisz węzeł
        print(str(node))

        # jeśli węzeł nie jest liściem
        if not node.leaf:

            # drukowanie dla każdego jego dziecka
            for child in node.child:
                self.print(child, indent)

    # szukanie klucza w drzewie
    def search(self, key, node=None):

        # jeśli nie wskazano węzła, zacznij od korzenia
        if node is None:
            node = self.root

        # indeks
        i = 0

        # dopóki nie znajdziesz pierwszego klucza większego/równego szukanemu to zwiększaj indeks o 1
        while i < len(node.keys) and key > node.keys[i]:
            i += 1

        # jeśli indeks wskazuje klucz, którego szukamy, to zwracamy ten węzeł
        if i < len(node.keys) and key == node.keys[i]:
            return node

        # w przeciwnym przypadku, jeśli węzeł jest liściem zwracamy None
        elif node.leaf:
            return None

        # a jeśli nie jest liściem, szukamy w odpowiednim dziecku
        else:
            return self.search(key, node.child[i])

    # wstawianie klucza do drzewa
    def insert(self, key):

        # obsługa duplikatów - jeśli klucz znajduje się w drzewie, to nie podwajamy go
        if self.search(key) is not None:
            print("Ten klucz już znajduje się w drzewie.")

        # jeśli klucza w drzewie nie ma
        else:

            # niech root będzie korzeniem drzewa
            root = self.root

            # jeśli korzeń jest pełny, podziel go i wstaw klucz w odpowiednim miejscu
            if len(root.keys) == ((2 * self.t) - 1):
                temp = Node()
                self.root = temp
                temp.child.insert(0, root)
                self._split_child(temp, 0)
                self._insert_nf(temp, key)

            # w przeciwnym wypadku przeprowadź operację wstawiania klucza do niepełnego węzła
            else:
                self._insert_nf(root, key)

    # wstawianie z warunkiem niepełności
    def _insert_nf(self, node, key):

        # ostatni indeks
        i = len(node.keys) - 1

        # jeśli węzeł jest liściem
        if node.leaf:

            # do listy dzieci dodaj None
            node.keys.append(None)

            # iteruj po liście aż znajdziesz miejsce wstawienia nowego klucza
            while i >= 0 and key < node.keys[i]:
                node.keys[i + 1] = node.keys[i]
                i -= 1

            # wstaw nowy klucz
            node.keys[i + 1] = key

        # w przeciwnym wypadku
        else:

            # znajdź indeks odpowiedniego dziecka
            while i >= 0 and key < node.keys[i]:
                i -= 1
            i += 1

            # jeśli dziecko jest pełne to je podziel
            if len(node.child[i].keys) == (2 * self.t) - 1:
                self._split_child(node, i)
                if key > node.keys[i]:
                    i += 1

            # przeprowadź operację wstawiania na odpowiednim dziecku
            self._insert_nf(node.child[i], key)

    # dzielenie dziecka
    def _split_child(self, node, cidx):
        t = self.t
        a = node.child[cidx]                    # dziecko do podziału
        b = Node()                              # nowe dziecko

        # wstaw nowe dziecko do listy dzieci węzła
        node.child.insert(cidx + 1, b)

        # do kluczy węzła wstaw środkowy klucz dziecka do podziału
        node.keys.insert(cidx, a.keys[t - 1])

        # prawą część kluczy wstaw do nowego dziecka
        b.keys = a.keys[t: (2 * t) - 1]

        # lewą część zostaw u starego dziecka
        a.keys = a.keys[0: t - 1]

        # jeśli a było liściem, podziel liste dzieci na pół, prawe do nowego, lewe do starego
        if not a.leaf:
            b.child = a.child[t: 2 * t]
            a.child = a.child[0: t]

    # generowanie zapisu, działa na podobnej zasadzie co drukowanie drzewa w terminalu
    def _generate_save_string(self, node=None):
        res = ""
        if node is None:
            node = self.root
        res += str(node) + "\n"
        if not node.leaf:
            for child in node.child:
                res += self._generate_save_string(child)
        return res

    # zapis drzewa do pliku .txt, jesli nie podamy ścieżki zapisu to plikiem domyślnym będzie output.txt
    def save(self, out="output.txt"):
        # otwarcie pliku w trybie zapisu
        file = open(out, 'w')

        # zapis stopnia drzewa
        file.write(str(self.t) + "\n")

        # zapis drzewa do pliku
        file.write(self._generate_save_string())

        # zamknięcie pliku
        file.close()

    # odczyt danych do drzewa
    def _load_data(self, data, node=None):
        # jeśli nie podano węzłą, weź korzeń
        if node is None:
            node = self.root

        # kluczami węzła będą wartości z pierwszej linii
        node.keys = [x for x in data[0]]

        # jeśli podane dane mają tylko jedną linię to zwróć węzeł
        if len(data) == 1:
            return node

        # w przeciwnym wypadku
        else:

            # jako dziecko dodaj kolejny odczyt danych do drzewa na liniach,
            # które zawierają wartości mniejsze od pierwszego klucza
            node.child.append(self._load_data([x for x in data[1:] if content_less(x, node.keys[0])], Node()))

            # jeśli węzeł ma więcej niż jeden klucz
            if len(node.keys) > 1:

                # dla wszystkich par sąsiadujących kluczy
                for i in range(len(node.keys)-1):
                    # wyznacz linie, które zawierają dane, większe od klucza[i] i mniejsze od klucza[i+1]
                    appender = [x for x in data[1:] if content_between(x, node.keys[i], node.keys[i+1])]
                    # i dodaj je do listy dzieci jako kolejny odczyt danych
                    node.child.append(self._load_data(appender, Node()))

            # dodaj jako dziecko odczyt danych z linii, które zawierają wartości większe niż ostatni klucz
            node.child.append(self._load_data([x for x in data[1:] if content_greater(x, node.keys[-1])], Node()))

            # zwróć węzeł
            return node

    # wczytywanie drzewa z pliku .txt, jesli nie podamy ścieżki zapisu to plikiem domyślnym będzie input.txt
    def load(self, src="input.txt"):
        # otwórz plik w trybie odczytu
        file = open(src, 'r')

        # jako dane zainicjalizuj tablicę, z pierwszą linią (stopień minimalny t drzewa)
        data = [file.readline(1)]

        # dla pozostałych linii pliku
        for line in file.readlines()[1:]:

            # podziel linie na znaku "|"
            temp = line.split("|")

            # usuń znaki nowej linii oraz puste
            temp.remove('\n')
            temp.remove('')

            # zamień znaki na integery
            temp = [int(x) for x in temp]

            # dodaj linię do listy danych
            data.append(temp)

        # wczytaj pierwszą linię pliku jako t
        self.t = int(data[0])

        # wczytaj resztę danych z nakierowaniem na korzeń
        self.root = self._load_data(data[1:])

        # zamknij plik
        file.close()


# kod testowy
def main():
    filename = "tree.txt"
    tree = BTree(3)
    for i in range(1, 30): #1, 7 -- 1, 30
        tree.insert(i)

    tree.save(filename)
    tree.print()
    print()
    print(f"Szukanie wartości 1: {tree.search(1)}")
    print()
    print(f"Szukanie wartości 4: {tree.search(4)}")
    print()
    print(f"Szukanie wartości 8: {tree.search(8)}")
    print()
    loadtree = BTree()
    loadtree.load(filename)
    loadtree.print()


if __name__ == '__main__':
    main()

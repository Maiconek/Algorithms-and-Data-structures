from random import randint as random

sum = 0

class Node:
    def __init__(self, freq, symbol, left=None, right=None):
        self.freq = freq  # częstość znaku
        self.symbol = symbol  # znak
        self.left = left  # lewe dziecko
        self.right = right  # prawe dziecko


def get_data(filename):
    # wczytuje wyłącznie pierwsza linię pliku jako listę znaków
    data = list(open(filename).readlines()[0])

    # listy do przechowywania znaków z ich częstościami
    arr1 = [[], []]  # znaki pojedynczo
    arr2 = [[], []]  # znaki parami

    # dla każdego znaku na liście
    for x in data:
        # jeśli znak pojawia się po raz pierwszy - dodaj znak do listy znaków pojedynczych oraz daj mu częstość 1
        if x not in arr1[0]:
            arr1[0].append(x)
            arr1[1].append(1)
        # jeśli znak pojawia się po raz kolejny - zwiększ jego częstość o 1
        else:
            arr1[1][arr1[0].index(x)] += 1

    n = 0
    # jeśli długość ciągu znaków jest nieparzysta, dodajemy losowy int na końcu ciągu
    if len(data) % 2:
        data.append(str(random(0, 7)))
    # pętla wyznacza pary znaków tj. znak1+znak2, znak3+znak4 itd.
    while n < len(data) - 1:
        # wyznacz parę znaków
        add = data[n] + data[n + 1]
        # działanie analogiczne do listy pojedynczych znaków
        if add not in arr2[0]:
            arr2[0].append(add)
            arr2[1].append(1)
        else:
            arr2[1][arr2[0].index(add)] += 1
        n += 2

    return [arr1, arr2]


def table(node, val=0, header=True):
    # nagłówek tabeli drukuje się tylko przy pierwszym wywołaniu (header = True)
    global sum
    if header:
        print("\nZnak | Długość Kodu")
    # jeśli węzeł ma lewe dziecko to przejdź do niego ze zwiększeniem val o 1 (ścieżka)
    if node.left:
        table(node.left, val + 1, False)
    # jeśli węzeł ma prawe dziecko to analogicznie
    if node.right:
        table(node.right, val + 1, False)
    # jeśli węzeł nie posiada dzieci, wydrukuj jego znak:częstość oraz długość jego kodu
    if not node.left and not node.right:
        print(f"{node.symbol}:{node.freq} | {val}")
        sum += val*node.freq


def print_tree(node, indent="", last=True):
    # drukuj wcięcie
    print(indent, end="")
    # jeśli ostatni - drukuj (...), zwiększ wcięcie
    if last:
        print("R----", end="")
        indent += "     "
    # w przeciwnym wypadku - drukuj(...), zwiększ wcięcie dodając kreskę
    else:
        print("L----", end="")
        indent += "|    "
    # jeśli węzeł jest "liściem" - wydrukuj go wyróżnijac go na żółto
    if 'z' not in node.symbol:
        print(f"\033[93m{node.symbol}:{node.freq}\033[0m")
    # w przeciwnym przypadku drukuj normalnie
    else:
        print(f"{node.symbol}:{node.freq}")
    # jeśli węzeł posiada lewe dziecko - przejdź do niego, przekazując wcięcie i że nieostatni
    if node.left:
        print_tree(node.left, indent, False)
    # jeśli węzeł posiada prawe dziecko - przejdź do niego, przekazując wcięcie i że ostatni
    if node.right:
        print_tree(node.right, indent, True)


def huff(arr):
    # lista niewstawionych węzłów
    nodes = []

    # uzupełnij listę węzłami do kodowania na podstawie podanej listy znaków i częstości
    for x in range(len(arr[0])):
        nodes.append(Node(arr[1][x], arr[0][x]))

    z = 0
    # dopóki lista ma więcej niż jeden węzeł
    while len(nodes) > 1:
        z += 1
        # sortuj węzły (wg częstości rosnąco) i wybierz dwa o najmniejszej częstości
        nodes = sorted(nodes, key=lambda n: n.freq)
        left = nodes[0]
        right = nodes[1]
        # utwórz nowy węzeł o nazwie z<liczba>, częstości sumującej częstości dzieci, daj wskazanie na dzieci
        new_node = Node(left.freq + right.freq, 'z' + str(z), left, right)
        # usuń z listy wykorzystane węzły
        nodes.remove(left)
        nodes.remove(right)
        # dodaj do listy nowy węzeł
        nodes.append(new_node)

    # wydrukuj drzewo oraz tabelę
    print_tree(nodes[0])
    table(nodes[0])
    print("\n")


print("Zadanie 10.6")
ready = get_data("liczby.txt")  # wczytuje plik tekstowy z ciągiem znaków
print("Znaki pojedynczo", end="\n\n")
huff(ready[0])  # kodowanie huffmana z pojedynczych znaków
print("Suma:", sum)
sum = 0
print("Znaki parami", end="\n\n")
huff(ready[1])  # kodowania huffmana z par znaków
print("Suma: ", sum)
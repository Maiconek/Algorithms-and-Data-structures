from disset import find_set, make_set, union


class Graph:
    # struktura grafu, może zostać utworzona z podanymi wierzchołkami i krawędziami lub bez
    def __init__(self, vertices=None, edges=None):
        if vertices is not None:
            self.vertices = vertices
        else:
            self.vertices = []
        if edges is not None:
            self.edges = edges
        else:
            self.edges = []


class Edge:
    # struktura krawędzi, zawiera 2 wierzchołki i "wagę"
    def __init__(self, vertex_1, vertex_2, weight):
        self.vertices = (vertex_1, vertex_2)
        self.weight = weight

    # reprezentacja krawędzi "waga: a-b"
    def __repr__(self):
        return f"{self.weight}: {self.vertices[0]}-{self.vertices[1]}"


def get_set(arr, key):
    # zwraca zbiór z listy zawierający wskazany klucz
    for item in arr:
        if item.key == key:
            return item
    else:
        return None


def kruskal(graph):
    # drukuje krawędzie drzewa spinającego o najmniejszym koszcie
    refs = []
    # dla każdego wierzchołka tworzy jego zbiór i dodaje do listy referencyjnej
    for vertex in graph.vertices:
        refs.append(make_set(vertex))
    # sortowanie krawędzi grafu wg wagi rosnąco
    graph.edges.sort(key=lambda eg: eg.weight)
    # dla każdej krawędzi w grafie
    for edge in graph.edges:
        # znajdź korzeń zbiorów od wierzchołków krawędzi
        ru = find_set(get_set(refs, edge.vertices[0]))
        rv = find_set(get_set(refs, edge.vertices[1]))
        # jeśli są różne
        if ru != rv:
            # wydrukuj krawędź i połącz wierzchołki
            print(edge)
            union(ru, rv)


if __name__ == '__main__':
    # na przykładzie z wykładu
    # lista wierzchołków od 'a' od 'i'
    v = [chr(x + 97) for x in range(9)]
    # lista krawędzi
    e = [Edge('a', 'b', 4),
         Edge('a', 'h', 9),
         Edge('b', 'h', 11),
         Edge('b', 'c', 10),
         Edge('c', 'd', 9),
         Edge('d', 'e', 7),
         Edge('d', 'f', 8),
         Edge('e', 'f', 10),
         Edge('f', 'g', 2),
         Edge('f', 'c', 4),
         Edge('c', 'i', 2),
         Edge('i', 'g', 6),
         Edge('i', 'h', 7),
         Edge('g', 'h', 1)]
    # graf utworzony na podstawie podanych wierzchołków i krawędzi
    g = Graph(v, e)
    # wywołanie algorytmu kruskala na grafie
    kruskal(g)

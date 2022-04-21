package pl.marcinbaranowski;

public class BinaryTree {

    class Node {
        int key;

        Node leftChild; // wskaźniki na dzieci
        Node rightChild;

        Node(int key) {
            this.key = key;
        }

        public String toString() {
            return "Key: " + key;
        }
    }

    Node root; // korzeń drzewa

    public void addNode(int key) {
        Node newNode = new Node(key);

        if (root == null) {
            root = newNode;
        } else {
            Node focusNode = root;
            Node parent;

            while (true) {
                parent = focusNode; // rodzic jako korzen

                if (key < focusNode.key) { // jezeli klucz mniejszy od rodzica to przyjmujemy ze jest lewym synem
                    focusNode = focusNode.leftChild;

                    if (focusNode == null) { // jezeli lewy syn nie ma synów to umiesczamy klucz po lewej
                        parent.leftChild = newNode;
                        return;
                    }
                } else { // jezeli klucz jest więksy od rodzica to jest jego prawym synem
                    focusNode = focusNode.rightChild;

                    if (focusNode == null) {
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }

    public void inOrderTraverseTree(Node focusNode) {
        if (focusNode != null) {                        // jeśli korzeń nie jest pusty
            inOrderTraverseTree(focusNode.leftChild);   // sprawdza lewe poddrzewo
            System.out.println(focusNode);              // wypisuje wartość klucza
            inOrderTraverseTree(focusNode.rightChild);  // sprawdza prawe poddrzewo
        }
    }

    public void postOrderTraverseTree(Node focusNode) {
        if (focusNode != null) {                        // jeśli korzeń nie jest pusty
            postOrderTraverseTree(focusNode.leftChild);   // sprawdza lewe poddrzewo
            postOrderTraverseTree(focusNode.rightChild); // sprawdza prawa poddrzewo
            System.out.println(focusNode);              // wypisuje wartość klucza
        }
    }

    public boolean remove(int key) {
        Node focusNode = root;
        Node parent = root;

        boolean isItALeftChild = true;

        while (focusNode.key != key) {
            parent = focusNode;

            if (key < focusNode.key) { // jezeli szukany klucz jest mniejszy od rodzica, to jest lewym dzieckiem
                isItALeftChild = true;
                focusNode = focusNode.leftChild;
            } else { // jezeli nie jest to znaczy jest prawym dzieckiem
                isItALeftChild = false;
                focusNode = focusNode.rightChild;
            }

            if (focusNode == null)
                return false;
        }

        if (focusNode.leftChild == null && focusNode.rightChild == null) { // jezeli nie ma dzieci
            if (focusNode == root) {
                root = null;
            } else if(isItALeftChild) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
        }
        else if(focusNode.rightChild == null) { // jezeli nie ma prawego dziecka
            if(focusNode == root)
                root = focusNode.leftChild;
            else if(isItALeftChild)
                parent.leftChild = focusNode.leftChild;
            else parent.rightChild = focusNode.rightChild;
        }
        else if(focusNode.leftChild == null) { // jezeli nie ma lewego dziecka
            if(focusNode == root)
                root = focusNode.rightChild;
            else if(isItALeftChild)
                parent.leftChild = focusNode.rightChild;
            else
                parent.rightChild = focusNode.leftChild;
        }
        else { // jezeli oba dziecka istnieją
            Node replacement = getReplacementNode(focusNode);

            if(focusNode == root)
                root = replacement;
            else if(isItALeftChild)
                parent.leftChild = replacement;
            else
                parent.rightChild = replacement;

            replacement.leftChild = focusNode.leftChild;
        }
        return true;
    }

    public Node getReplacementNode(Node replacedNode) {
        Node replacementParent = replacedNode;
        Node replacement = replacedNode;

        Node focusNode = replacedNode.rightChild;

        while (focusNode != null) {
            replacementParent = replacement;
            replacement = focusNode;
            focusNode = focusNode.leftChild;
        }

        if(replacement != replacedNode.rightChild) {
            replacementParent.leftChild = replacement.rightChild;
            replacement.rightChild = replacedNode.rightChild;
        }

        return replacement;
    }

    public boolean ifNodeExists(Node node, int searched) {
        if (node == null)
            return false;

        if (node.key == searched)
            return true;

        // szukamy klucza na lewym poddrzewie
        boolean res1 = ifNodeExists(node.leftChild, searched);

        // jezeli go znaleźliśmy zwracamy true
        if(res1) return true;

        // jezeli nie to sprawdzamy prawe poddrzewo
        boolean res2 = ifNodeExists(node.rightChild, searched);

        return res2;
    }

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();

        bt.addNode(1);
        bt.addNode(17);
        bt.addNode(54);
        bt.addNode(4);
        bt.addNode(28);
        bt.addNode(34);
        bt.addNode(11);

        System.out.println("In-order: ");
        bt.inOrderTraverseTree(bt.root);

        System.out.println("\nPost-order: ");
        bt.postOrderTraverseTree(bt.root);
        System.out.println("\nCzy 11 znajduje się w drzewie?\nOdpowiedz: "+ bt.ifNodeExists(bt.root, 11));
        System.out.println();
        System.out.println("Usuwamy klucz 11\n");
        bt.remove(11);
        System.out.println("Czy 11 znajduje się w drzewie?\nOdpowiedz: "+ bt.ifNodeExists(bt.root, 11));
        System.out.println("\nIn-order:");
        bt.inOrderTraverseTree(bt.root);
        System.out.println();
        System.out.println("Wstawiamy klucz 32\n");
        bt.addNode(32);
        bt.inOrderTraverseTree(bt.root);
        System.out.println("\nCzy 32 znajduje się w drzewie?\nOdpowiedz: " + bt.ifNodeExists(bt.root, 32));
    }
}

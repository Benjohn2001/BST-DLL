import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

/**
 * @author benjohnston
 * @param <T>
 * @number 2432411j
 * @description Task 2 to implement binary search tree dynamic set ADT
 *
 */
public class binarySearchTree<T extends Comparable<T>> {

	public static class Node<T> {

		T data;
		Node<T> left;
		Node<T> right;

		public Node(T x) {
			data = x;
		}

	}

	Node<T> root;

	public boolean isElement(T x) {
		Node<T> temp = root;
		if (temp == null) {
			return false;
		}
		while (temp != null && !temp.data.equals(x)) {
			if (x.compareTo(temp.data) < 0) {
				temp = temp.left;
			} else {
				temp = temp.right;
			}
		}
		if (temp == null) {
			return false;
		}
		if (!temp.data.equals(x)) {
			return false;
		} else {
			return true;
		}
	}

	public Node<T> add(T x) {
		if (isElement(x) == false) {
			Node<T> pointer = null;
			Node<T> temp = null;
			if (root == null) {
				root = new Node<T>(x);
				return root;
			} else {
				temp = root;
			}
			while (root != null) {
				pointer = temp;
				if (x.compareTo(temp.data) < 0) {
					temp = temp.left;
					if (temp == null) {
						pointer.left = new Node<T>(x);
						return root;
					}
				} else if (x.compareTo(temp.data) > 0) {
					temp = temp.right;
					if (temp == null) {
						pointer.right = new Node<T>(x);
						return root;
					}
				}
			}

		}
		return root;
	}

	public void outputRec(Node<T> x) {
		if (x == null) {
			return;
		} else {
			outputRec(x.left);
			System.out.print(x.data + " ");
			outputRec(x.right);
		}
	}

	public void output() {
		Node<T> temp = root;
		outputRec(temp);
	}

	public int setSizeRec(Node<T> x) {
		int counter = 0;
		if (x == null) {
			return 0;
		}
		counter = 1 + setSizeRec(x.left) + setSizeRec(x.right);
		return counter;
	}

	public int setSize() {
		Node<T> temp = root;
		int num = setSizeRec(temp);
		return num;
	}

	public boolean setEmpty() {
		if (root == null) {
			return true;
		} else {
			return false;
		}
	}

	public Node<T> getMin(Node<T> n) {
		while (n.left != null) {
			n = n.left;
		}
		return n;

	}

	public void remove(T x) {
		if (root == null) {
			return;
		}
		Node<T> pointer = null;
		Node<T> temp = root;
		while (temp != null && !temp.data.equals(x)) {
			pointer = temp;
			if (x.compareTo(temp.data) < 0) {
				temp = temp.left;
			} else {
				temp = temp.right;
			}
		}
		if (temp == null) {
			return;
		}

		if (temp.left == null && temp.right == null) {
			if (temp != root) {
				if (pointer.left.equals(temp)) {
					pointer.left = null;
				} else {
					pointer.right = null;
				}

			} else {
				root = null;
			}
		}

		else if (temp.left != null && temp.right != null) {
			Node<T> minNode = getMin(temp.right);
			T minData = minNode.data;
			remove(minData);
			temp.data = minData;
		}

		else {
			Node<T> nodeC;
			if (temp.left != null) {
				nodeC = temp.left;
			} else {
				nodeC = temp.right;
			}
			if (!temp.equals(root)) {
				if (temp.equals(pointer.left)) {
					pointer.left = nodeC;
				} else {
					pointer.right = nodeC;
				}
			} else {
				root = nodeC;
			}
		}

	}

	public void inorderTrav(Node<T> x, binarySearchTree<T> items) {
		if (x == null) {
			return;
		} else {
			inorderTrav(x.left, items);
			items.add(x.data);
			inorderTrav(x.right, items);
		}
	}

	public void union(binarySearchTree<T> T) {
		Node<T> temp = root;
		Node<T> tempT = T.root;
		binarySearchTree<T> tempBST = new binarySearchTree<T>();
		inorderTrav(temp, tempBST);
		inorderTrav(tempT, tempBST);
		tempBST.output();
	}

	public void inorderTravIntersect(Node<T> x, binarySearchTree<T> items, binarySearchTree<T> inter) {
		if (x == null) {
			return;
		} else {
			inorderTravIntersect(x.left, items, inter);
			int size = items.setSize();
			items.add(x.data);
			if (size == items.setSize()) {
				inter.add(x.data);
			}
			inorderTravIntersect(x.right, items, inter);
		}
	}

	public void intersection(binarySearchTree<T> T) {
		Node<T> temp = root;
		Node<T> tempT = T.root;
		binarySearchTree<T> tempBST = new binarySearchTree<T>();
		binarySearchTree<T> inter = new binarySearchTree<T>();
		inorderTravIntersect(temp, tempBST, inter);
		inorderTravIntersect(tempT, tempBST, inter);
		inter.output();
	}

	public void inorderTravDifference(Node<T> x, binarySearchTree<T> items) {
		if (x == null) {
			return;
		} else {
			inorderTravDifference(x.left, items);
			if (items.isElement(x.data)) {
				items.remove(x.data);
			}
			inorderTravDifference(x.right, items);
		}
	}

	public void difference(binarySearchTree<T> T) {
		Node<T> temp = root;
		Node<T> tempT = T.root;
		binarySearchTree<T> diff = new binarySearchTree<T>();
		inorderTrav(temp, diff);
		inorderTravDifference(tempT, diff);
		diff.output();

	}

	public void inorderTravSubset(Node<T> x, binarySearchTree<T> items, binarySearchTree<T> subset) {
		if (x == null) {
			return;
		} else {
			inorderTravSubset(x.left, items, subset);
			if (items.isElement(x.data)) {
				subset.add(x.data);
			}
			inorderTravSubset(x.right, items, subset);
		}
	}

	public boolean subset(binarySearchTree<T> T) {
		Node<T> temp = root;
		Node<T> tempT = T.root;
		binarySearchTree<T> counter = new binarySearchTree<T>();
		binarySearchTree<T> BSTtemp = new binarySearchTree<T>();
		inorderTrav(temp, BSTtemp);
		inorderTravSubset(tempT, BSTtemp, counter);
		if (counter.setSize() == BSTtemp.setSize()) {
			return true;
		} else {
			return false;
		}

	}

	public int treeHeight(Node<T> temp) {
		if (temp == null) {
			return 0;
		} else {
			int left = treeHeight(temp.left);
			int right = treeHeight(temp.right);
			if (right > left) {
				return (right + 1);
			} else {
				return (left + 1);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		binarySearchTree<Integer> BST = new binarySearchTree<Integer>();
		System.out.print("Set empty: " + BST.setEmpty() + "\n");
		BST.add(7);
		BST.add(9);
		BST.add(3);
		BST.add(11);
		BST.add(5);
		BST.add(4);
		BST.add(4);
		BST.add(12);
		BST.add(10);
		BST.remove(10);
		System.out.println("Set A contents: ");
		BST.output();
		System.out.print("\nIs 7 present: " + BST.isElement(7));
		System.out.print("\nIs 9 present: " + BST.isElement(9));
		System.out.print("\nIs 20 present: " + BST.isElement(20));
		System.out.print("\nIs the set empty: " + BST.setEmpty());
		System.out.print("\nSet Size: " + BST.setSize());
		binarySearchTree<Integer> T = new binarySearchTree<Integer>();
		T.add(9);
		T.add(2);
		T.add(8);
		T.add(1);
		T.add(6);
		T.add(10);
		T.remove(10);
		System.out.print("\nSet B contents: ");
		T.output();
		System.out.print("\nUnion of A&B: ");
		BST.union(T);
		System.out.print("\nIntersection of A&B: ");
		BST.intersection(T);
		System.out.print("\nDifference of A&B: ");
		BST.difference(T);

		System.out.print("\nSubset Example:\n");
		binarySearchTree<Integer> subS = new binarySearchTree<Integer>();
		subS.add(7);
		subS.add(9);
		System.out.println("Set C contents: ");
		subS.output();
		binarySearchTree<Integer> subT = new binarySearchTree<Integer>();
		subT.add(7);
		subT.add(9);
		subT.add(3);
		System.out.println("\nSet D contents: ");
		subT.output();
		System.out.print("\nIs C a subset of D: " + subS.subset(subT));

		Path p = Paths.get("/Users/benjohnston/Downloads/int20k.txt");
		Scanner s = new Scanner(p);
		binarySearchTree<Integer> twentyK = new binarySearchTree<Integer>();
		while (s.hasNext()) {
			if (s.hasNextInt()) {
				twentyK.add(s.nextInt());
			} else {
				s.next();
			}
		}
		doublyLinkedList<Integer> rands = new doublyLinkedList<Integer>();
		Random rand = new Random();
		for (int i = 0; i < 100; i++) {
			int temp = rand.nextInt(49999);
			rands.add(temp);
		}

		long total = 0;
		doublyLinkedList.Node<Integer> temp = rands.head;
		while (temp != null) {
			int random = temp.data;
			long start = System.nanoTime();
			twentyK.isElement(random);
			long end = System.nanoTime();
			long time = end - start;
			total += time;
			temp = temp.next;
		}
		long avg = total / 100;
		System.out.println("\n\nInt20k tests: ");
		System.out.print("Avg time: " + avg + "\n");
		System.out.print("Set Size: " + twentyK.setSize());
		System.out.print("\nBST Height: " + twentyK.treeHeight(twentyK.root));

	}

}
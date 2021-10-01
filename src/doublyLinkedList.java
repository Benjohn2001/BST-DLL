import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

/**
 * @author benjohnston
 * @param <T>
 * @number 2432411j
 * @description Task 1 to implement doubly linked list dynamic set ADT
 *
 */
public class doublyLinkedList<T> {

	public static class Node<T> {

		T data;
		Node<T> previous;
		Node<T> next;

		public Node(T x) {
			data = x;
		}

	}

	Node<T> head;

	public boolean isElement(T x) {
		Node<T> temp = head;
		if (temp == null) {
			return false;
		}
		while (!temp.data.equals(x) && temp.next != null) {
			temp = temp.next;
		}
		if (!temp.data.equals(x)) {
			return false;
		} else {
			return true;
		}
	}

	public void add(T x) {
		if (isElement(x) == false) {
			Node<T> newN = new Node<T>(x);
			newN.next = null;
			if (head == null) {
				newN.previous = null;
				head = newN;
				return;
			}
			Node<T> end = head;
			while (end.next != null) {
				end = end.next;
			}
			end.next = newN;
			newN.previous = end;

		}
	}

	public void delNode(Node<T> x) {
		if (head == null || x == null) {
			return;
		}
		if (head.equals(x)) {
			head = x.next;
		}
		if (x.next != null) {
			x.next.previous = x.previous;
		}
		if (x.previous != null) {
			x.previous.next = x.next;
		}
		return;
	}

	public void remove(T x) {
		if (head == null) {
			return;
		}
		Node<T> temp = head;
		Node<T> next;
		while (temp != null) {
			if (temp.data.equals(x)) {
				next = temp.next;
				delNode(temp);
				temp = next;
			} else {
				temp = temp.next;
			}
		}
	}

	public boolean setEmpty() {
		if (head == null) {
			return true;
		} else {
			return false;
		}
	}

	public int setSize() {
		Node<T> temp = head;
		int counter = 0;
		while (temp != null) {
			counter += 1;
			temp = temp.next;
		}
		return counter;
	}

	public void union(doublyLinkedList<T> t) {
		Node<T> tHead = t.head;
		doublyLinkedList<T> tempDLL = new doublyLinkedList<T>();
		Node<T> tempS = head;
		Node<T> tempT = tHead;
		while (tempS != null) {
			tempDLL.add(tempS.data);
			tempS = tempS.next;
		}
		while (tempT != null) {
			if (!tempDLL.isElement(tempT.data)) {
				tempDLL.add(tempT.data);
			}
			tempT = tempT.next;
		}
		System.out.println(tempDLL.output());
	}

	public void intersection(doublyLinkedList<T> t) {
		Node<T> tHead = t.head;
		doublyLinkedList<T> tempDLL = new doublyLinkedList<T>();
		Node<T> tempS = head;
		while (tempS != null) {
			Node<T> tempT = tHead;
			while (tempT != null) {
				if (tempS.data.equals(tempT.data)) {
					tempDLL.add(tempS.data);
					break;
				}
				tempT = tempT.next;
			}
			tempS = tempS.next;
		}
		System.out.println(tempDLL.output());
	}

	public void difference(doublyLinkedList<T> t) {
		Node<T> tHead = t.head;
		doublyLinkedList<T> tempDLL = new doublyLinkedList<T>();
		Node<T> tempS = head;
		boolean status = false;
		while (tempS != null) {
			Node<T> tempT = tHead;
			while (tempT != null) {
				if (tempS.data.equals(tempT.data)) {
					status = true;
					break;
				}
				tempT = tempT.next;
			}
			if (status == false) {
				tempDLL.add(tempS.data);
			}
			status = false;
			tempS = tempS.next;
		}
		System.out.println(tempDLL.output());
	}

	public boolean subset(doublyLinkedList<T> t) {
		Node<T> tHead = t.head;
		Node<T> tempS = head;
		Node<T> tempT = tHead;
		boolean status = true;
		while (tempS != null && status == true) {
			while (tempT != null) {
				if (tempS.data.equals(tempT.data)) {
					status = true;
					break;
				} else {
					status = false;
				}
				tempT = tempT.next;

			}
			tempS = tempS.next;
		}
		return status;

	}

	public String output() {
		Node<T> temp = head;
		String out = "";
		while (temp != null) {
			out = out + temp.data + ",";
			temp = temp.next;
		}
		out = out.replace(',', ' ');
		return out;
	}

	public static void main(String[] args) throws IOException {

		doublyLinkedList<Integer> S = new doublyLinkedList<Integer>();
		S.add(1);
		S.add(5);
		S.add(5);
		S.add(6);
		S.add(3);
		S.add(9);
		S.remove(9);
		System.out.println("Contents of set A: " + S.output());
		System.out.println("Is set A empty: " + S.setEmpty());
		System.out.println("Set Size: " + S.setSize());

		doublyLinkedList<Integer> T = new doublyLinkedList<Integer>();
		System.out.println("Is set B empty: " + T.setEmpty());
		T.add(4);
		T.add(5);
		T.add(6);
		T.add(7);
		System.out.println("Contents of Set B: " + T.output());
		System.out.println("Is 5 in B: " + T.isElement(5));

		System.out.println("Union of A&B: ");
		S.union(T);
		System.out.println("Intersection of A&B: ");
		S.intersection(T);
		System.out.println("Difference of A&B: ");
		S.difference(T);
		System.out.print("Is A a subset of B: " + S.subset(T));

		Path p = Paths.get("/Users/benjohnston/Downloads/int20k.txt");
		Scanner s = new Scanner(p);
		doublyLinkedList<Integer> twentyK = new doublyLinkedList<Integer>();
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
		Node<Integer> temp = rands.head;
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

	}

}

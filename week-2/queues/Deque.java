import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node<Item> head;
	private Node<Item> tail;
	private int size;

	public Deque() {

	}

	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<>();
		deque.addFirst(1);
		System.out.println(deque);
		deque.addLast(2);
		System.out.println(deque);
		deque.addFirst(3);
		System.out.println(deque);

		System.out.println(deque.isEmpty());
		System.out.println(deque.size());
		deque.removeFirst();
		System.out.println(deque);
		deque.removeLast();
		System.out.println(deque);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		Iterator<Item> itemIterator = iterator();
		while (itemIterator.hasNext()) {
			Item item = itemIterator.next();
			stringBuilder.append(" -> ").append(item.toString());
		}
		return stringBuilder.toString();
	}

	public boolean isEmpty() {
		return head == null;
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}

		if (size == 0) {
			head = new Node<>(item);
			tail = head;
			size = 1;
		} else {
			Node<Item> newHead = new Node<>(item);
			newHead.next = head;
			head.prev = newHead;
			head = newHead;
			size++;
		}
	}

	public void addLast(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}

		if (size == 0) {
			head = new Node<>(item);
			tail = new Node<>(item);
			head.next = tail;
			tail.prev = head;
			size = 1;
		} else {
			Node<Item> newTail = new Node<>(item);
			tail.next = newTail;
			newTail.prev = tail;
			tail = newTail;
			size++;
		}
	}

	public Item removeFirst() {
		if (size == 0) {
			throw new NoSuchElementException();
		}

		Node<Item> oldHead = head;
		head = oldHead.next;
		size--;
		if (size == 0) {
			tail = null;
		} else {
			head.prev = null;
			oldHead.next = null;
		}
		return oldHead.item;
	}

	public Item removeLast() {
		if (size == 0) {
			throw new NoSuchElementException();
		}

		Node<Item> oldTail = tail;
		tail = oldTail.prev;
		size--;
		if (size == 0) {
			head = null;
		} else {
			tail.next = null;
			oldTail.prev = null;
		}
		return oldTail.item;
	}

	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator(head);
	}

	class DequeIterator implements Iterator<Item> {
		private Node<Item> current;

		public DequeIterator(Node<Item> current) {
			this.current = current;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (current == null) {
				throw new NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	class Node<Item> {
		private Item item;
		private Node next;
		private Node prev;

		public Node(Item item) {
			this.item = item;
		}

		public Node(Item item, Node next, Node prev) {
			this.item = item;
			this.next = next;
			this.prev = prev;
		}
	}
}

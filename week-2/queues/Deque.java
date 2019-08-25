import java.util.Iterator;
import java.util.LinkedList;

public class Deque<Item> implements Iterable<Item> {
	private LinkedList<Item> list;

	public Deque() {
		list = new LinkedList<>();
	}

	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<>();
		deque.isEmpty();
		deque.size();
		deque.addFirst(1);
		deque.addLast(2);
		deque.removeFirst();
		deque.removeLast();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public int size() {
		return list.size();
	}

	public void addFirst(Item item) {
		list.addFirst(item);
	}

	public void addLast(Item item) {
		list.addLast(item);
	}

	public Item removeFirst() {
		return list.removeFirst();
	}

	public Item removeLast() {
		return list.removeLast();
	}

	@Override
	public Iterator<Item> iterator() {
		return list.iterator();
	}
}

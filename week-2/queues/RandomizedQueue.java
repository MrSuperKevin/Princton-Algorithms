import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Deque<Item> deque;

	public RandomizedQueue() {
		this.deque = new Deque<>();
	}

	// unit testing (required)
	public static void main(String[] args) {
		System.out.println("======= enqueue() ======");
		RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
		randomizedQueue.enqueue(" I ");
		randomizedQueue.enqueue(" am ");
		randomizedQueue.enqueue(" waiting ");
		randomizedQueue.enqueue(" in ");
		randomizedQueue.enqueue(" an ");
		randomizedQueue.enqueue(" queue ");
		System.out.println(randomizedQueue.toString());

		System.out.println("======= iterator() ======");
		Iterator<String> iterator = randomizedQueue.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

		System.out.println("======= Sample() ======");
		System.out.println(randomizedQueue.sample());
		System.out.println(randomizedQueue.sample());

		System.out.println("======= dequeue() ======");
		randomizedQueue.dequeue();
		System.out.println(randomizedQueue.toString());
		randomizedQueue.dequeue();
		System.out.println(randomizedQueue.toString());
		randomizedQueue.dequeue();
		System.out.println(randomizedQueue.toString());
	}

	@Override
	public String toString() {
		return deque.toString();
	}

	public boolean isEmpty() {
		return deque.isEmpty();
	}

	public int size() {
		return deque.size();
	}

	public void enqueue(Item item) {
		deque.addLast(item);
	}

	public Item dequeue() {
		return StdRandom.uniform(2) % 2 == 0 ? deque.removeFirst() : deque.removeLast();
	}

	public Item sample() {
		return StdRandom.uniform(2) % 2 == 0 ? deque.getHeadItem() : deque.getTailItem();
	}

	// return an independent iterator over items in random order
	@Override
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	class RandomizedQueueIterator implements Iterator<Item> {
		private Deque.Node headCursor = deque.getHead();// 由于内部类的问题报错，把类移到外面试试
		private Deque.Node tailCursor = deque.getTail();

		@Override
		public boolean hasNext() {
			return headCursor.prev != tailCursor;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			if (StdRandom.uniform(2) % 2 == 1) {
				Item item = (Item) headCursor.getItem();
				headCursor = headCursor.next;
				return item;
			} else {
				Item item = (Item) tailCursor.getItem();
				tailCursor = tailCursor.prev;
				return item;
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}

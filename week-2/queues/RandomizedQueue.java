import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int capacity = 4;
	private int size;
	private Item[] array;

	public RandomizedQueue() {
		this.array = (Item[]) new Object[capacity];
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
		System.out.println(randomizedQueue.size());

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
		StringBuilder stringBuilder = new StringBuilder();
		for (Item item : array) {
			stringBuilder.append(" --> ").append(item);
		}
		return stringBuilder.toString();
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void scaleUp() {
		Item[] tmp = (Item[]) new Object[capacity * 2];
		capacity *= 2;
		for (int i = 0; i < size; i++) {
			tmp[i] = array[i];
		}
		array = tmp;
	}

	private void scaleDown() {
		Item[] tmp = (Item[]) new Object[capacity / 2];
		capacity /= 2;
		for (int i = 0; i < size; i++) {
			tmp[i] = array[i];
		}
		array = tmp;
	}

	public int size() {
		return size;
	}

	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		// enlarge
		if (size == capacity) {
			scaleUp();
		}
		array[size] = item;
		size++;
	}

	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		int index = StdRandom.uniform(size);
		Item item = array[index];
		array[index] = array[size - 1];
		array[size - 1] = null;
		size--;
		// scale down
		if (size < capacity / 4) {
			scaleDown();
		}
		return item;
	}

	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return array[StdRandom.uniform(size)];
	}

	// return an independent iterator over items in random order
	@Override
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private class RandomizedQueueIterator implements Iterator<Item> {
		private final int[] randomIndexArray;
		private int current;

		public RandomizedQueueIterator() {
			current = 0;
			randomIndexArray = new int[size];
			for (int i = 0; i < size; i++) {
				randomIndexArray[i] = i;
			}
			StdRandom.shuffle(randomIndexArray);
		}

		@Override
		public boolean hasNext() {
			return current < size;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return array[randomIndexArray[current++]];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

/**
 * 读懂题意：有一个n行的文件，n未知，要均匀分布地输出n行中的k行。思路是先确定均匀分布的概率，对于每行应该是k/n，那么就尽量通过遍历中的随机操作来凑出来k/n。
 */
public class Permutation {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
		for (int i = 0; i < k; i++) {
			String in = StdIn.readString();
			randomizedQueue.enqueue(in);
		}
		int n = k;
		while (!StdIn.isEmpty()) {
			String in = StdIn.readString();
			n++;
			// 精髓
			if (StdRandom.uniform(n) < k) {
				randomizedQueue.dequeue();
				randomizedQueue.enqueue(in);
			}
		}

		Iterator<String> iterator = randomizedQueue.iterator();
		for (int i = 0; i < k; i++) {
			StdOut.println(iterator.next());
		}
	}
}

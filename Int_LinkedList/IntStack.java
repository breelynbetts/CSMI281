package int_linkedlist;

public class IntStack extends IntLinkedList implements TrueIntStack {

	IntStack() {
		super();
	}
	public void push(int toPush) {
		throw new IllegalArgumentException();
	}
	public void pop() {
		throw new IllegalArgumentException();
	}
	public void peek() {
		throw new IllegalArgumentException();
	}
	
}

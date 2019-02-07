package int_linkedlist;

import IntLinkedList.Iterator;
import IntLinkedList.Node;

public class IntLinkedList {

	private int size;
	private Node head;
	
	IntLinkedList () {
		head = null;
		size = 0; 
	}
	
	public int getSize() {
		return size; 
	}
	
	public void prepend(int item) {
		Node heldHead = head; // designate where the head is
		head = new Node(item); // assigns the head to a new node 
		head.next = heldHead; 
		size ++;
	}
	
	
	public void removeAt(int index) {
		Node current = head;
		Node prev = null;
		
		// find node to remove, making sure to
		// record keep its predecessor 
		while(current != null && index > 0 ) {
			prev = current;
			current = current.next;
			index --;
		}
		
		// remove the Node and account for edge cases
		if ( current == null ) {return;}
		// Case: removing the head
		if ( current == head ) {head = head.next;}
		// Case: removing inner Node
		if (prev != null) {prev.next = current.next;}
		
	}
	
	public Iterator getIteratorAt (int index) {
		if (index >= size || index < 0) {
			throw new IllegalArgumentException();
		}
		Iterator it = new Iterator();
		while (index > 0) {
			it.next();
			index--;
		}
		return it;
	}
	
	
	private class Node {
		
		int data;
		Node next;
		
		Node (int d) {
			data = d;
			next = null;
		}
	}
	
	public class Iterator {
		
		private Node current;
		
		Iterator (){
			current = head; 
		}
		
		public boolean hasNext() {
			return current != null && current.next != null; // order in this instance is important!! short circuits the thing
		}
		
		public void next() {
			if (current == null) {return;}
			current = current.next;
		}
		
		public int getCurrentInt() {
			return current.data;
		}
		
	}
	
	public static void main (String[] args) {
		IntLinkedList coolJ = new IntLinkedList();
		coolJ.prepend(3);
		coolJ.prepend(2);
		coolJ.prepend(1);
		
		IntLinkedList.Iterator it = coolJ.getIteratorAt(0);
		System.out.println(it.getCurrentInt());
		it.next();
		System.out.println(it.getCurrentInt());
		it.next();
		System.out.println(it.getCurrentInt());
		
	}
}



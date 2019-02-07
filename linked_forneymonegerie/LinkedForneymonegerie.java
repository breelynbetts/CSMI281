package linked_forneymonegerie;

import java.util.NoSuchElementException;

public class LinkedForneymonegerie implements LinkedForneymonegerieInterface {

	// Fields
	// -----------------------------------------------------------
	private ForneymonType head;
	private ForneymonType tail;
	private int size, typeSize, modCount;

	// Constructor
	// -----------------------------------------------------------
	LinkedForneymonegerie() {
		head = null;
		tail = null;
		size = 0;
		typeSize = 0;
		modCount = 0;
	}

	// Methods
	// -----------------------------------------------------------
	public boolean empty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public int typeSize() {
		return typeSize;
	}

	public boolean collect(String toAdd) {
		return insertForneymonType(toAdd, 1);
	}

	public boolean release(String toRemove) {
		return removeAt(toRemove, 1);
	}

	public void releaseType(String toNuke) {
		removeAt(toNuke, findType(toNuke).count);
	}

	public int countType(String toCount) {
		ForneymonType location = findType(toCount);

		// Case: no type to count
		if (location == null) {
			return 0;
		}

		// Case: return number of instances
		return location.count;
	}

	public boolean contains(String toCheck) {
		return findType(toCheck) != null;
	}

	public String rarestType() {
		String rare = null;
		int lowestCount = 0;

		for (ForneymonType n = head; n != null; n = n.next) {
			if (n.count <= lowestCount || rare == null) {
				lowestCount = n.count;
				rare = n.type;
			}
		}
		return rare;
	}

	public LinkedForneymonegerie clone() {
		LinkedForneymonegerie clone = new LinkedForneymonegerie();
		ForneymonType current = head;

		while (current != null) {
			ForneymonType item = new ForneymonType(current.type, current.count);
			clone.insertForneymonType(item.type, item.count);
			current = current.next;
		}
		clone.size = size;
		clone.typeSize = typeSize;
		clone.modCount = modCount;
		return clone;
	}

	public void trade(LinkedForneymonegerie other) {
		LinkedForneymonegerie temp = new LinkedForneymonegerie();
		temp.head = head;
		temp.size = size;
		temp.typeSize = typeSize;
		temp.modCount = modCount;

		head = other.head;
		size = other.size;
		typeSize = other.typeSize;
		modCount = other.modCount;

		other.head = temp.head;
		other.size = temp.size;
		other.typeSize = temp.typeSize;
		other.modCount = temp.modCount;
		modCount++;

	}

	public String toString() {
		String result = "";
		ForneymonType current = head;
		while (current != null) {
			if (current.next == null) {
				result += "\"" + current.type + "\": " + current.count;
			} else {
				result += "\"" + current.type + "\": " + current.count + ", ";
			}
			current = current.next;
		}
		return "[ " + result + " ]";
	}

	public LinkedForneymonegerie.Iterator getIterator() {
		if (empty()) {
			throw new IllegalStateException();
		}
		LinkedForneymonegerie.Iterator iter = new LinkedForneymonegerie.Iterator(this);
		return iter;
	}

	// -----------------------------------------------------------
	// Static methods
	// -----------------------------------------------------------

	public static LinkedForneymonegerie diffMon(LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
		LinkedForneymonegerie result = y1.clone();

		for (ForneymonType n = y2.head; n != null; n = n.next) {
			result.removeAt(n.type, n.count);
		}
		return result;
	}

	public static boolean sameCollection(LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
		return diffMon(y1, y2).empty() && y1.size == y2.size && y1.typeSize == y2.typeSize;
	}

	// Private helper methods
	// -----------------------------------------------------------

	public ForneymonType findType(String typeToFind) {
		for (ForneymonType n = head; n != null; n = n.next) {
			if (n.type.equals(typeToFind)) {
				return n;
			}
		}
		return null;
	}

	private boolean insertForneymonType(String toAdd, int count) {
		ForneymonType current = findType(toAdd);
		ForneymonType oldTail;

		// Case: existing forneymonType
		if (current != null) {
			current.count++;
			size++;
			modCount++;
			return false;
		}

		// Case: first in list
		else if (empty()) {
			head = new ForneymonType(toAdd, count);
			tail = head;
			typeSize++;
			size += count;
			modCount++;
			return true;
		} else {
			oldTail = tail;
			tail = new ForneymonType(toAdd, count);
			oldTail.next = tail;
			tail.prev = oldTail;
			typeSize++;
			size++;
			modCount++;
			return true;
		}
	}

	private boolean removeAt(String typeRemoved, int count) {

		if (findType(typeRemoved) == null) {
			return false;
		}

		ForneymonType found = findType(typeRemoved);
		int newCount = found.count - count;

		ForneymonType prev = found.prev;
		ForneymonType next = found.next;

		if (newCount <= 0) {
			if (found == head) {
				head = next;
			} else if (found == tail) {
				tail = prev;
				prev.next = null;
			} else {
				prev.next = next;
				next.prev = prev;
			}
			size -= found.count;
			typeSize--;
		} else {
			found.count = newCount;
			size -= count;
		}
		modCount++;
		return true;
	}

	// Inner Classes
	// -----------------------------------------------------------

	public class Iterator implements LinkedForneymonegerieIteratorInterface {
		LinkedForneymonegerie owner;
		ForneymonType current;
		int itModCount, itIndex;

		Iterator(LinkedForneymonegerie y) {
			owner = y;
			current = head;
			itModCount = owner.modCount;
			itIndex = 0;
		}

		public boolean hasNext() {
			if (current == tail && itIndex == current.count - 1) {
				return false;
			}
			return true;
		}

		public boolean hasPrev() {
			if (current == head && itIndex == 0) {
				return false;
			}
			return true;
		}

		public boolean isValid() {
			return itModCount == owner.modCount;
		}

		public String getType() {
			if (!isValid()) {
				return null;
			}
			return current.type;
		}

		public void next() {
			if (!isValid()) {
				throw new IllegalStateException();
			}

			// case: new node
			if (itIndex == current.count - 1 && current.next == null) {
				throw new NoSuchElementException();
			}
			if (itIndex == current.count - 1) {
				itIndex = 0;
				current = current.next;
			}
			if (itIndex < current.count - 1) {
				itIndex++;
			}
		}

		public void prev() {
			if (!isValid()) {
				throw new IllegalStateException();
			}

			if (itIndex == 0 && current.prev == null) {
				throw new NoSuchElementException();
			}
			if (itIndex == 0) {
				current = current.prev;
				itIndex = current.count;
			}
			if (itIndex > 0) {
				itIndex--;
			}
		}

		public void replaceAll(String toReplaceWith) {
			if (!isValid()) {
				throw new IllegalStateException();
			}

			if (current.type.equals(toReplaceWith)) {
				return;
			}

			ForneymonType found = findType(toReplaceWith);
			int tempCount;
			if (found == null) {
				tempCount = current.count;
				insertForneymonType(toReplaceWith, current.count);
				releaseType(current.type);
			} else {
				tempCount = current.count;
				found.count += tempCount;
				releaseType(current.type);
			}

			itModCount++;
			owner.modCount = itModCount;
		}

	}

	private class ForneymonType {
		ForneymonType next, prev;
		String type;
		int count;

		ForneymonType(String t, int c) {
			type = t;
			count = c;
		}
	}

}

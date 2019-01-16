package dictreenary;

import java.util.ArrayList;

public class Dictreenary implements DictreenaryInterface {

	// Fields
	// -----------------------------------------------------------
	TTNode root;

	// Constructor
	// -----------------------------------------------------------
	Dictreenary() {
		root = null;
	}

	// Methods
	// -----------------------------------------------------------

	public boolean isEmpty() {
		return root == null;
	}

	public void addWord(String toAdd) {
		root = insertNode(root, normalizeWord(toAdd), 0);
	}

	public boolean hasWord(String query) {
		return findWord(query);
	}

	public String spellCheck(String query) {
		return swappedCheck(query);
	}

	public ArrayList<String> getSortedWords() {
		ArrayList<String> al = new ArrayList<String>();
		traverse(root, "", al);
		return al;
	}

	// Helper Methods
	// -----------------------------------------------------------

	private String normalizeWord(String s) {
		// Edge case handling: empty Strings illegal
		if (s == null || s.equals("")) {
			throw new IllegalArgumentException();
		}
		return s.trim().toLowerCase();
	}

	/*
	 * Returns: int less than 0 if c1 is alphabetically less than c2 0 if c1 is
	 * equal to c2 int greater than 0 if c1 is alphabetically greater than c2
	 */
	private int compareChars(char c1, char c2) {
		return Character.toLowerCase(c1) - Character.toLowerCase(c2);
	}

	// [!] Add your own helper methods here!

	private char[] splitWord(String toSplit) {
		normalizeWord(toSplit);
		return toSplit.toCharArray();
	}

	private TTNode insertNode(TTNode curr, String word, int t) {
		if (curr == null) {
			curr = new TTNode(word.charAt(t), false);
		}
		int compare = compareChars(word.charAt(t), curr.letter);
		if (compare < 0) {
			curr.left = insertNode(curr.left, word, t);
		} else if (compare > 0) {
			curr.right = insertNode(curr.right, word, t);
		} else {
			if (t + 1 < word.length()) {
				curr.mid = insertNode(curr.mid, word, t + 1);
			} else {
				curr.wordEnd = true;
			}
		}
		return curr;
	}

	private boolean findWord(String toFind) {
		return checkWord(root, splitWord(toFind), 0);
	}

	private boolean checkWord(TTNode curr, char[] word, int t) {
		// Base Case: no value at node
		if (curr == null) {
			return false;
		}
		int compare = compareChars(word[t], curr.letter);
		// Case 1
		if (compare < 0) {
			return checkWord(curr.left, word, t);
		} else if (compare > 0) {
			return checkWord(curr.right, word, t);
		} else {
			if (curr.wordEnd && t == word.length - 1) {
				return true;
			} else if (t == word.length - 1) {
				return false;
			} else {
				return checkWord(curr.mid, word, t + 1);
			}
		}
	}

	private String swappedCheck(String misspelledWord) {
		if (findWord(misspelledWord)) {
			return misspelledWord;
		}

		for (int i = 0; i < misspelledWord.length() - 1; i++) {
			if (findWord(charSwap(misspelledWord, i, i + 1))) {
				return charSwap(misspelledWord, i, i + 1);
			}
		}
		return null;
	}

	public String charSwap(String swap, int i, int j) {
		if (j == swap.length() - 1) {
			return swap.substring(0, i) + swap.charAt(j) + swap.substring(i + 1, j) + swap.charAt(i);
		}
		return swap.substring(0, i) + swap.charAt(j) + swap.substring(i + 1, j) + swap.charAt(i)
				+ swap.substring(j + 1, swap.length());
	}

	private void traverse(TTNode curr, String str, ArrayList<String> aList) {
		if (curr != null) {
			traverse(curr.left, str, aList);

			str = str + curr.letter;
			if (curr.wordEnd) {
				aList.add(str);
			}
			traverse(curr.mid, str, aList);
			str = str.substring(0, str.length() - 1);

			traverse(curr.right, str, aList);
		}
	}

	// TTNode Internal Storage
	// -----------------------------------------------------------

	/*
	 * Internal storage of Dictreenary words as represented using a Ternary Tree
	 * with TTNodes
	 */
	private class TTNode {

		boolean wordEnd;
		char letter;
		TTNode left, mid, right;

		TTNode(char c, boolean w) {
			letter = c;
			wordEnd = w;
		}

	}

}

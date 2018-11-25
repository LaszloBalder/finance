package nl.yasmijn.utils;

public abstract class Smart {
	public static int count(String word, Character ch) {
		int pos = word.indexOf(ch);
		return pos == -1 ? 0 : 1 + count(word.substring(pos + 1), ch);
	}
}

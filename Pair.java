package cpsc433;

import java.util.*;

public class Pair<K,V>
extends Object
implements Map.Entry<K,V>, Comparable
{
	private K key;
	private V value;
	
	public Pair(K key, V value) {}
	
	private int compare(Object x, Object y) {return 0;}
	
	public int compareTo(Object arg0) {return 0;}
	
	public K getKey() {return key;}
	
	public V getValue() {return value;}
	
	public V setValue(V value) {return value;}
}
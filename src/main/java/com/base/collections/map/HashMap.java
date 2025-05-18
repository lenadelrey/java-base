package com.base.collections.map;

import java.util.*;

public class HashMap<K, V> implements Map<K, V>
{
	private Node<K, V>[] table = (Node<K, V>[]) new Node<?, ?>[16];
	private int size;
	private List<Entry<K, V>> cache = new ArrayList<>();

	public static void main(String[] args)
	{
		Map<String, Integer> hashMap = new HashMap<>();
		hashMap.put("Hello 1", 1);
		hashMap.put("Hello 2", 2);
		hashMap.put("Hello 3", 3);
		hashMap.put("Hello 4", 4);
		hashMap.put("Hello 111", 111);
		hashMap.put("Hello 5", 53);
		hashMap.put("Hello 6", 56);
		hashMap.put("Hello 7", 55);

		System.out.println(hashMap);

		hashMap.remove("Hello 6");
		hashMap.remove("Hello 5");
		hashMap.remove("Hello 1");
		hashMap.remove("Hello 55");

		System.out.println(hashMap);
		System.out.println(hashMap.get("Hello 2"));
	}

	@Override
	public V get(K key)
	{
		Node<K, V> node = table[getIndex(key)];

		if (node == null)
		{
			return null;
		}

		while (node != null)
		{
			if (node.key.equals(key))
			{
				return node.value;
			}

			node = node.next;
		}

		return null;
	}

	@Override
	public V put(K key, V value)
	{
		if (size == 0.75 * table.length)
		{
			resize();
		}

		var node = new Node<>(key, value);
		int index = getIndex(key);

		if (table[index] == null)
		{
			table[index] = node;
			cache.add(node);
		}
		else
		{
			Node<K, V> temp = table[index];

			while(temp.next != null)
			{
				if (temp.next.key.equals(key))
				{
					V old = temp.next.getValue();
					temp.next.setValue(value);

					return old;
				}

				temp = temp.next;
			}

			temp.next = node;
			cache.add(node);
		}

		size++;
		return value;
	}

	@Override
	public V remove(K key)
	{
		int index = getIndex(key);

		if (table[index] == null)
		{
			return null;
		}

		Node<K, V> old;
		V oldValue = null;
		Node<K, V> temp = table[index];

		if (temp.next == null)
		{
			old = table[index];
			oldValue = table[index].value;
			table[index] = null;
			cache.remove(old);

			return oldValue;
		}

		while(temp.next != null)
		{
			if(temp.next.key.equals(key))
			{
				Node<K, V> remove = temp.next;
				oldValue = remove.value;

				temp.next = temp.next.next;
				remove.key = null;
				remove.value = null;

				cache.remove(remove);
			}

			temp = temp.next;
		}

		return oldValue;
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public void clear()
	{
		if (table == null || size <= 0)
		{
			return;
		}

		size = 0;
		Arrays.fill(table, null);
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public boolean containsKey(K key)
	{
		for (Entry<K, V> entry : cache)
		{
			if (entry.getKey().equals(key))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean containsValue(V value)
	{
		for (Entry<K, V> entry : cache)
		{
			if (entry.getValue().equals(value))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public Set<K> keySet()
	{
		Set<K> keySet = new HashSet<>();

		for (int i = 0; i < size; i++)
		{
			keySet.add(cache.get(i).getKey());
		}

		return keySet;
	}

	@Override
	public Collection<V> values()
	{
		Set<V> values = new HashSet<>();

		for (int i = 0; i < size; i++)
		{
			values.add(cache.get(i).getValue());
		}

		return values;
	}

	@Override
	public Set<Entry<K, V>> entrySet()
	{
		return new HashSet<>(cache);
	}

	private void resize()
	{
		int newSize = table.length + (table.length / 2);
		Node<K, V>[] newArray = (Node<K, V>[]) new Node<?, ?>[newSize];
		var table = this.table;
		this.table = newArray;

		for (Node<K, V> node : table)
		{
			if (node == null)
			{
				continue;
			}

			var current = node;
			var next = node.next;
			var currentIndex = getIndex(current.getKey());

			while (current != null)
			{
				if (next != null && getIndex(next.getKey()) != currentIndex)
				{
					if (this.table[getIndex(next.getKey())] == null)
					{
						this.table[getIndex(next.getKey())] = next;
					}
					else
					{
						var temp2 = this.table[getIndex(next.getKey())];
						while (temp2 != null)
						{
							if (temp2.next == null)
							{
								temp2.next = next;
								break;
							}
							temp2 = temp2.next;
						}
					}
					current.next = null;
				}

				this.table[getIndex(current.getKey())] = current;
				current = current.next;
			}
		}
	}

	private int getIndex(K key)
	{
		if (key == null)
		{
			return 0;
		}

		int i = key.hashCode();
		int hash = i ^ (i >>> 16);
		return hash & (table.length - 1);
	}

	static class Node<K, V> implements Map.Entry<K, V>{
		private K key;
		private V value;
		private Node<K, V> next;

		Node(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey()
		{
			return key;
		}

		@Override
		public V getValue()
		{
			return value;
		}

		@Override
		public V setValue(V value)
		{
			V old = this.value;
			this.value = value;
			return old;
		}

		@Override
		public String toString() {
			return "Node{" +
					"key=" + key +
					", value=" + value +
					'}';
		}
	}

	@Override
	public String toString() {
		return "HashMap{" +
				"table=" + Arrays.toString(table) +
				", size=" + size +
				", l=" + table.length +
				'}';
	}
}

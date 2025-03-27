package com.base.collections.map;

import java.util.Collection;
import java.util.Set;

public interface Map<K, V>
{
	V get(K key);

	V put(K key, V value);

	V remove(K key);

	int size();

	void clear();

	boolean isEmpty();

	boolean containsKey(K key);

	boolean containsValue(V value);

	Set<K> keySet();

	Collection<V> values();

	Set<Entry<K, V>> entrySet();

	interface Entry<K, V>
	{
		K getKey();

		V getValue();

		V setValue(V value);
	}
}

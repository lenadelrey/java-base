package com.base.collections.list;

import java.util.Collection;

public interface List<T>
{
	void add(T o);

	void add(int index, T o);

	void addAll(Collection<? extends T> list);

	T set(int index, T o);

	T get(int index);

	int size();

	int indexOf(T o);

	T remove(int index);

	boolean isEmpty();

	boolean contains(T o);
}

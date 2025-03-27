package com.base.collections.list;

import java.util.Arrays;
import java.util.Collection;

public class ArrayList<T> implements List<T>
{
	private T[] array;
	private int size = 0;

	public ArrayList()
	{
		this.array = (T[]) new Object[10];
	}

	public ArrayList(int capacity)
	{
		this.array = (T[]) new Object[capacity];
	}

	public static void main(String[] args)
	{
		List<String> arrayList = new ArrayList<>();

		arrayList.add("Hello 1");
		arrayList.add("Hello 2");
		arrayList.add("Hello 3");
		arrayList.add("Hello 4");
		arrayList.add("Hello 5");
		arrayList.add("Hello 6");
		arrayList.add("Hello 7");
		arrayList.add("Hello 8");
		arrayList.add("Hello 9");
		arrayList.add("Hello 10");
		arrayList.add("Hello 11");


		System.out.println(arrayList);
	}

	@Override
	public void add(T o)
	{
		if (o == null)
		{
			return;
		}

		if (array.length == size)
		{
			grow(null);
		}

		array[size++] = o;
		size = size + 1;
	}

	@Override
	public void add(int index, T o)
	{
		if (o == null)
		{
			return;
		}

		if (array.length == size)
		{
			grow(null);
		}

		for (int i = size; i > index; i--)
		{
			array[i] = array[i - 1];
		}

		array[index] = o;
		size = size + 1;
	}

	//todo пересмотреть реализацию, какая-то фигня в цикле
	@Override
	public void addAll(Collection<? extends T> list)
	{
		if (list == null || list.isEmpty())
		{
			throw new RuntimeException("Лист не должен быть пустым.");
		}

		int newArraySize = list.size();

		if (array.length == size)
		{
			grow(newArraySize + size);
		}

		T[] newArray = (T[]) list.toArray();

		for (int i = 0; i < newArray.length; i++)
		{
			if (array[size + i] == null)
			{
				array[size + i] = newArray[i];
			}
		}

		size = size + newArraySize;
	}
/*
	public void trimToSize(T[] array, int size)
	{
		if (array == null)
		{
			return;
		}

		T[] newArray = (T[]) new Object[size];

		for (int i = 0; i < newArray.length; i++)
		{
			newArray[i] = array[i];
		}
		this.array = newArray;
	}*/

	@Override
	public T set(int index, T o)
	{
		T old = array[index];
		array[index] = o;

		return old;
	}

	@Override
	public T get(int index)
	{
		return array[index];
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public int indexOf(T o)
	{
		if (o == null)
		{
			for (int i = 0; i < size; i++)
			{
				if (array[i] == null)
				{
					return i;
				}
			}
		}
		else
		{
			for (int i = 0; i < size; i++)
			{
				if (array[i] == o)
				{
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public T remove(int index)
	{
		T old;

		for (int i = 0; i < size; i++)
		{
			if (i == index)
			{
				old = array[i];

				for (int j = 0; j < size - 1; j++) {
					array[i] = array[i + 1];
				}

				size--;
				return old;
			}
		}

		return null;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public boolean contains(T o)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == o)
			{
				return true;
			}
		}

		return false;
	}

	/*private void grow()
	{
		var newCapacity = array.length + array.length / 2;

		T[] newArray = (T[]) (new Object[newCapacity]);

		for (int i = 0; i < array.length; i++)
		{
			newArray[i] = array[i];
		}

		this.array = newArray;
	}*/
	private void grow(Integer minCapacity)
	{
		int newCapacity = minCapacity == null
				? array.length + array.length / 2
				: minCapacity;

		T[] newArray = (T[]) (new Object[newCapacity]);

		for (int i = 0; i < size; i++)
		{
			newArray[i] = array[i];
		}

		this.array = newArray;
	}

	@Override
	public String toString() {
		return "ArrayList{" +
				"array=" + Arrays.toString(array) +
				", size=" + size +
				'}';
	}
}

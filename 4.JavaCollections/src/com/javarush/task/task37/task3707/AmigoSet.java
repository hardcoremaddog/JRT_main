package com.javarush.task.task37.task3707;

import java.io.*;
;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AmigoSet<E> extends AbstractSet implements Serializable, Cloneable, Set {

	private static final Object PRESENT = new Object();
	private transient HashMap<E, Object> map;

	public AmigoSet() {
		this.map = new HashMap<>();
	}

	public AmigoSet(Collection<? extends E> collection) {
		this.map = new HashMap<>(Math.max((int) (collection.size() / .75f) + 1, 16));

		for (E e : collection) {
			map.put(e, PRESENT);
		}
	}

	public Iterator<E> iterator() {
		return this.map.keySet().iterator();
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public boolean containsAll(Collection c) {
		return super.containsAll(c);
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	public boolean remove(Object o) {
		return super.remove(o);
	}

	@Override
	public boolean add(Object o) {
		return null == map.put((E) o, PRESENT);
	}

	@Override
	public Object clone() {
		AmigoSet copy;
		try {
			copy = (AmigoSet) super.clone();
			copy.map = (HashMap) map.clone();
		} catch (Exception e) {
			throw new InternalError(e);
		}
		return copy;
	}

	private void writeObject(ObjectOutputStream oos) throws Exception {
		oos.defaultWriteObject();

		oos.writeInt(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
		oos.writeFloat(HashMapReflectionHelper.callHiddenMethod(map,"loadFactor"));
		oos.writeInt(map.size());

		for (E e : map.keySet()) oos.writeObject(e);



	}
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();

		int capacity = ois.readInt();
		float loadFactor = ois.readFloat();
		int size = ois.readInt();

		map = new HashMap<>(capacity,loadFactor);

		for (int i = 0; i < size; i++)
		{
			E e = (E) ois.readObject();
			map.put(e,PRESENT);
		}

	}
}
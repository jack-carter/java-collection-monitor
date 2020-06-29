package com.shinobigroup.collections.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 * This class acts as a <a href="https://en.wikipedia.org/wiki/Decorator_pattern">Decorator</a> for instances of {@code java.util.Collection}.
 * It monitors changes to the {@code collection.size()}, and notifies listeners anytime the size changes.
 *
 * Instances of this class should not be considered thread-safe.
 *
 * @author Jack Carter
 * @email  jack.carter@shinobigroup.com
 * @since  Apr 30, 2018
 *
 * @param <T> the type of elements contained in the underlying collection
 */
public class CollectionSizeMonitor<T> implements Collection<T> {

    //
    // Instantiation
    //
    
    static public <T> Collection<T> of(final Collection<T> collection) {
	return new CollectionSizeMonitor<T>(collection);
    }
    
    public CollectionSizeMonitor(final Collection<T> collection) {
	this.collection = Objects.requireNonNull(collection, "'collection' cannot be null");
	this.priorSize = collection.size();
    }
    
    //
    // Listener support
    //
    
    public interface Listener {
	public void sizeChanged(final Collection<?> collection);
    }
    
    public CollectionSizeMonitor<T> addListener(final Listener listener) {
	listeners.add(listener);
	return this;
    }
    
    public CollectionSizeMonitor<T> removeListener(final Listener listener) {
	listeners.remove(listener);
	return this;
    }
    
    private void notifyListeners() {
	if (collection.size() != priorSize) {
		listeners.forEach(listener -> listener.sizeChanged(collection));
		priorSize = collection.size();
	}
    }
    
    //
    // Collection<T> interface
    //
    
    @Override
    public int size() {
	return collection.size();
    }

    @Override
    public boolean isEmpty() {
	return collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
	return collection.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
	return collection.iterator();
    }

    @Override
    public Object[] toArray() {
	return collection.toArray();
    }

    @Override
    public <E> E[] toArray(E[] a) {
	return collection.toArray(a);
    }

    @Override
    public boolean add(T e) {
	boolean result = collection.add(e);
	notifyListeners();
	return result;
    }

    @Override
    public boolean remove(Object o) {
	boolean result = collection.remove(o);
	notifyListeners();
	return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
	return collection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
	boolean result = collection.addAll(c);
	notifyListeners();
	return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
	boolean result = collection.removeAll(c);
	notifyListeners();
	return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
	boolean result = collection.retainAll(c);
	notifyListeners();
	return result;
    }

    @Override
    public void clear() {
	collection.clear();
	notifyListeners();
    }

    //
    // Instance variables
    //
    
    private int priorSize;
    private Collection<T> collection;
    private HashSet<Listener> listeners = new HashSet<Listener>();
}

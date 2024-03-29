package mikera.persistent.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements an iterator that filters on a given predicate.
 * 
 * Concrete implementations should override the abstract 
 * method filter()
 * 
 * @author Mike Anderson
 *
 * @param <T>
 */
public abstract class FilteredIterator<T> implements Iterator<T> {
	private Iterator<T> base; // belonging to us
	private T next;
	private boolean havenext=false;
	
	public FilteredIterator(Iterator<T> baseIterator) {
		base=baseIterator;
		findNext();
	}
	
	public abstract boolean filter(T value);
	
	public boolean hasNext() {
		return havenext;
	}
	
	private boolean findNext() {
		while (base.hasNext()) {
			T t = base.next();
			if (filter(t)) {
				next=t;
				havenext=true;
			}
		}
		return false;
	}

	public T next() {
		if (havenext) {			
			T result=next;
			havenext=findNext();
			return result;
		}
		throw new NoSuchElementException();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}

package mikera.util;

import java.util.*;

/**
 * Immutable small set of integers, stored as a sorted array
 * 
 * Should not contain duplicates, but not enforced
 * 
 * @author Mike
 *
 */
public final class IntSet implements Set<Integer>, Cloneable {
	private final int[] data;
	
	public static final IntSet EMPTY_SET=new IntSet(mikera.util.Arrays.NULL_INTS);

	private static final HashCache<IntSet> cache=new HashCache<IntSet>(400);
	
	private IntSet(int [] values) {
		data =values;
	}
	
	public boolean contains(IntSet a) {
		int[] adata=a.data;
		int[] sdata=data;
		int ai=0;
		int si=0;
		while (ai<adata.length) {
			int needed=adata[ai++];
			while (true) {
				int s=sdata[si++];
				if (s>needed) return false; // not found
				if (s==needed) break;
				if (si>sdata.length) return false;
			}
		}
		return true;
	}
	
	/**
	 * Testing method to check for duplicates, should always be false
	 * 
	 * @return
	 */
	public boolean hasDuplicates() {
		for (int i=0; i<data.length-1; i++) {
			if (data[i]==data[i+1]) return true;
		}
		return false;
	}

	public boolean contains (int v) {
		return findIndex(v,0,data.length)>=0;
	}
	
	public int[] toIntArray() {
		return (int[])data.clone();
	}
	
	public int findIndex(int v) {
		return findIndex(v,0,data.length);
	}

	public int findIndex(int v, int lo, int hi) {
		while (lo<hi) {
			int m=(lo+hi)>>1;
			int dv=data[m];
			if (dv==v) return m;
			if (dv<v) {
				lo = m+1;
			} else {
				hi = m;
			}
		} 
		return -1;
	}
	
	public static IntSet create(int[] data) {
		return create(data,0,data.length);
	}
	
	public static IntSet createMerged(IntSet is, int v) {
		if (is.contains(v)) return is;
		int[] data=is.data;
		int ol=data.length;
		int[] ndata=new int[ol+1];
		int i=0;
		while ((i<ol)) {
			int dv=data[i];
			if (dv>v) {
				break;
			}
			ndata[i++]=dv;
		}
		ndata[i++]=v;
		while (i<=ol) {
			ndata[i]=data[i-1];
			i++;
		}
		return createLocal(ndata);
	}
	
	private static IntSet create(int[] data, int offset, int size) {
		if (size==0) return EMPTY_SET;
		int[] ndata=new int[size];
		System.arraycopy(data, offset, ndata, 0, size);
		java.util.Arrays.sort(ndata);
		return createLocal(ndata);
	}
		
	private static IntSet createLocal(int[] sortedData) {
		return intern(new IntSet(sortedData));
	}
	
	public static IntSet intern(IntSet is) {
		is=cache.cache(is);
		return is;
	}

	public int hashCode() {
		return hashCode(data);
	}
	
	/**
	 * Hash code based on summed hash codes of individual integer values
	 * 
	 * @param data
	 * @return
	 */
	public static int hashCode(int[] data) {
		int result=0;
		for(int i=0; i<data.length; i++) {
			result+=hashCode(data[i]);
		}
		return result;
	}
	
	/**
	 * Custom hash value for a single integer, purpose is to reduce hash collisions for small sets of small integers
	 * 
	 * @param i integer value for which to obtain the hashCode
	 * @return
	 */
	public static int hashCode(int i) {
		return i+(i<<(i&15));
	}
	
	public IntSet clone() {
		return new IntSet(data);
	}
	
	public boolean equals(IntSet is) {
		if (is==this) return true;
		int s=size();
		if (is.size()!=s) return false;
		for (int i=0; i<s; i++) {
			if (data[i]!=is.data[i]) return false;
		}
		return true;
	}
	
	public boolean equals(Object o) {
		if (! (o instanceof IntSet)) return false;
		return equals((IntSet) o);
	}

	/**
	 * Set<Integer> methods
	 */
	public boolean add(Integer e) {
		throw new Error("IntSet is Immutable");
	}

	public boolean addAll(Collection<? extends Integer> c) {
		throw new Error("IntSet is Immutable");
	}

	public void clear() {
		throw new Error("IntSet is Immutable");
	}

	public boolean contains(Object o) {
		if (!(o instanceof Integer)) return false;
		
		return contains (((Integer)o).intValue());
	}

	public boolean containsAll(Collection<?> c) {
   		for (Object o: c) {
			if (!contains(o)) return false;
		}
		return true;
	}

	public boolean isEmpty() {
		return size()==0;
	}

	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			int pos=0;
			
			public boolean hasNext() {
				return pos<size();
			}

			public Integer next() {
				return Integer.valueOf(data[pos++]);
			}

			public void remove() {
				throw new Error("IntSet is Immutable");
			}
		};
	}

	public boolean remove(Object o) {
		throw new Error("IntSet is Immutable");
	}

	public boolean removeAll(Collection<?> c) {
		throw new Error("IntSet is Immutable");
	}

	public boolean retainAll(Collection<?> c) {
		throw new Error("IntSet is Immutable");
	}

	public int size() {
		return data.length;
	}

	public Object[] toArray() {
		int s=size();
		Integer[] ints= new Integer[s];
		for (int i=0; i<s; i++) {
			ints[i]=Integer.valueOf(data[i]);
		}
		return ints;
	}

	public <T> T[] toArray(T[] a) {
		throw new Error("Not supported");
	}


}
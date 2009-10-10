package mikera.test;

import org.junit.*;
import static org.junit.Assert.*;
import mikera.util.*;

public class TestTools {
	@Test public void testSort() {
		Integer[] a=new Integer[100];
		Integer[] b=new Integer[100];
		
		for (int i=0; i<100; i++) {
			a[i]=Rand.d(100);
		}
		
		Tools.mergeSort(a, b, 0, 99);
		
		assertTrue(Arrays.isSorted(a, 0, 99));
	}
	
	@Test public void testCompares() {
		assertEquals(-1, Tools.compareWithNulls(null, 1));
		assertEquals(-1, Tools.compareWithNulls(1, 2));
		assertEquals(1, Tools.compareWithNulls(2, null));
		assertEquals(0, Tools.compareWithNulls(null, null));
		assertEquals(0, Tools.compareWithNulls(1, 1));
		
		assertEquals(0, Tools.compareWithNulls(1, 1));

	}
}
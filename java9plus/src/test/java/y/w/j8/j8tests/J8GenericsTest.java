package y.w.j8.j8tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class J8GenericsTest {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGenerics() {
		List<Number> numbers = new ArrayList<Number>();
		List<Integer> integers = Arrays.asList(1, 2);
		List<Double> doubles = Arrays.asList(1.0, 2.0);
		numbers.addAll(integers);
		numbers.addAll(doubles);

		// integers.addAll(numbers); - this is wrong b/c Number is not a subtype of integer
		List<? extends Number> nums = integers;

		//nums.addAll(doubles);
		// Wrong. This is b/c Double is a subtype of Number but <? extends Number>
		// might be any other subtype of Number

		List<Object> objs = Arrays.<Object>asList(2, 3.14, "four");
		List<Integer> ints = Arrays.asList(5, 6);
		copyT(objs, ints);
	}

	private static <T> void copyT(List<? super T> dst, List<? extends T> src) {
		for (int i = 0; i < src.size(); i++) {
			dst.set(i, src.get(i));
		}
	}

	@Test
	public void testGenericMethods() {
		String stringElement = "stringElement";
		List<String> stringList = new ArrayList<String>();

		String theElement1 = addAndReturn(stringElement, stringList);

		Integer integerElement = new Integer(123);
		List<Integer> integerList = new ArrayList<Integer>();

		Integer theElement2 = addAndReturn(integerElement, integerList);

		String stringElement2 = "stringElement";
		List<Object> objectList = new ArrayList<Object>();

		Object theElement3 = addAndReturn(stringElement, objectList);

		Object objectElement = new Object();
		List<String> stringList2 = new ArrayList<String>();

		// The following line doesn't work because String is not T even though String
		// is subtype of Object. It will work if
		// addAndReturn(T element, Collection<? super T> collection)
		// Object theElement4 = addAndReturn(objectElement, stringList);
	}

	private static <T> T addAndReturn(T element, Collection<T> collection){
		collection.add(element);
		return element;
	}


}

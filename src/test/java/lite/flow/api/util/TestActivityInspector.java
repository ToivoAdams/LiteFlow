package lite.flow.api.util;

import static org.hamcrest.collection.IsArrayContaining.*;
import static org.junit.Assert.*;

import org.junit.Test;

import lite.flow.api.activity.Entry;
import lite.flow.api.activity.Output;
import lite.flow.util.ActivityInspector;
import lite.flow.util.ActivityInspector.InspectResult;

public class TestActivityInspector {

	class VerySimpleComponent {
		public int add(int a, int b) {
			return a+b;
		}
	}

	@Test
	public void testInspectVerySimpleComponent() {
		InspectResult inspectResult = ActivityInspector.inspect(VerySimpleComponent.class);
		assertEquals("first input name should be", 	"a", 	inspectResult.getInputNames()[0]);
		assertEquals("second input name should be", "b", 	inspectResult.getInputNames()[1]);
		assertEquals("output name should be", 		"add", 	inspectResult.outputNames[0]);
	}

	class SimpleComponentWithOutputs {
		
		Output<Number> result = new Output<>();
		
		public void add(int a, int b) {
			result.emit(a+b);
			return;
		}
	}
		
	@Test
	public void testInspectSimpleComponentWithOutputs() {
		InspectResult inspectResult = ActivityInspector.inspect(SimpleComponentWithOutputs.class);
		assertEquals("first input name should be", 	"a", 	inspectResult.getInputNames()[0]);
		assertEquals("second input name should be", "b", 	inspectResult.getInputNames()[1]);
		assertEquals("output name should be", 		"result",inspectResult.outputNames[0]);
	}

	class SimpleComponentWithMultipleEntries {
		
		public int add(int a, int b) {
			return a+b;
		}
		
		public void doSomething(String name) {
			return;
		}
	}

	@Test
	public void testInspectSimpleComponentWithMultipleEntries() {
		InspectResult inspectResult = ActivityInspector.inspect(SimpleComponentWithMultipleEntries.class);
		
		assertThat("Input names should contain name", inspectResult.getInputNames(), hasItemInArray("a"));
		assertThat("Input names should contain name", inspectResult.getInputNames(), hasItemInArray("b"));
		assertThat("Input names should contain name", inspectResult.getInputNames(), hasItemInArray("name"));
		assertEquals("Number of inputs should be", 3, inspectResult.getInputNames().length);

		assertThat("Output names should contain name", inspectResult.outputNames, hasItemInArray("add"));
		assertThat("Output names should contain name", inspectResult.outputNames, hasItemInArray("doSomething"));
		assertEquals("Number of outputs should be", 2, inspectResult.outputNames.length);		
	}

	/**
	 *	When one or more method is marked with Entry annotation, 
	 * only such methods are considered Entry points.
	 * Other public methods without Entry annotation are not Entry points anymore.
	 */
	class SimpleComponentWithEntryAnnotation {
		
		@Entry(outName="result", inNames={"avalue","bvalue"})		
		public int add(int a, int b) {
			return a+b;
		}
		
		public void doSomething(String name) {
			return;
		}
	}

	@Test
	public void testInspectSimpleComponentWithEntryAnnotation() {
		InspectResult inspectResult = ActivityInspector.inspect(SimpleComponentWithEntryAnnotation.class);
		
		assertThat("Input names should contain name", inspectResult.getInputNames(), hasItemInArray("avalue"));
		assertThat("Input names should contain name", inspectResult.getInputNames(), hasItemInArray("bvalue"));
		assertEquals("Number of inputs should be", 2, inspectResult.getInputNames().length);

		assertThat("Output names should contain name", inspectResult.outputNames, hasItemInArray("result"));
		assertEquals("Number of outputs should be", 1, inspectResult.outputNames.length);		
	}


}

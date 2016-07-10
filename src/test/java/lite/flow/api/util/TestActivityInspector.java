package lite.flow.api.util;

import static org.hamcrest.collection.IsArrayContaining.*;
import static org.junit.Assert.*;

import org.junit.Test;

import lite.flow.api.activity.Output;
import lite.flow.api.util.ActivityInspector.InspectResult;

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

}

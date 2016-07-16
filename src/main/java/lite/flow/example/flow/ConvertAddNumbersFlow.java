package lite.flow.example.flow;

import static lite.flow.api.flow.define.FlowBuilder.*;

import lite.flow.api.flow.define.Flow;
import lite.flow.example.component.Adder;
import lite.flow.example.component.StringSplitter;
import lite.flow.example.component.StringToInt;

public class ConvertAddNumbersFlow {

	public static Flow flow = defineFlow("ConvertAddNumbersFlow")
			.component("StringSplitter", StringSplitter.class, 	10,   60)
			.component("ConvertAtoInt", StringToInt.class, 		230,  30)
			.component("ConvertBtoInt", StringToInt.class, 		230, 110)
			.component("Adder", 		Adder.class, 			450,  60)
			
			.connect("StringSplitter", 	"outA", "ConvertAtoInt", "str")
			.connect("StringSplitter", 	"outB", "ConvertBtoInt", "str")
			.connect("ConvertAtoInt", 	"number", "Adder", "a")
			.connect("ConvertBtoInt", 	"number", "Adder", "b")
			
			.flowInput("NumbersString", "StringSplitter", "str")
			.flowOutput("ResultNumber", "Adder", "number")

			.get();
}

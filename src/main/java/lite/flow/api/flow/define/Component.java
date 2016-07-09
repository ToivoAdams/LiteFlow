/**
 * Copyright 2016 ToivoAdams
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lite.flow.api.flow.define;

import static lite.flow.api.util.ActivityInspector.*;


import lite.flow.api.util.ActivityInspector.InputGroup;

public class Component extends Activity {
	public final Class<?> 	clazz;

	public Component(Class<?> clazz, String	name, int x, int y) {
	//	super(name, flat(getInputs(clazz)), getOutputNames(clazz), x, y);
		super(name, inspect(clazz).inputNames, inspect(clazz).outputNames, x, y);
		this.clazz = clazz;
	}

	public int maxNumberOfPorts() {
		if (inputNames.length>outputNames.length)
			return inputNames.length;
		else
			return outputNames.length;
	}

	private static final InspectResult inspect(Class<?> clazz) {
		InputGroup[] inputGroups = getInputs(clazz);
		String[] allInputNames = flat(inputGroups);
		String[] outputNames = getOutputNames(clazz);
		if ((outputNames==null || outputNames.length<1) && inputGroups!=null) {
			// when we don't have any Output defined, entry methods names become output names
			outputNames = new String[inputGroups.length];
			int i = 0;
			for (InputGroup inputGroup : inputGroups) {
				outputNames[i++] = inputGroup.method.getName();
			}
		}
		return new InspectResult(allInputNames, outputNames);
	}
	
	private static String[] flat(InputGroup[] inputs) {
		int totalInputs = 0;
		for (InputGroup inputGroup : inputs) {
			totalInputs += inputGroup.inputNames.length;
		}
		
		int i = 0; 
		String[] allInputNames = new String[totalInputs];
		for (InputGroup inputGroup : inputs)
			for (String name : inputGroup.inputNames)
				allInputNames[i++] = name;

		return allInputNames;
	}

	public static class InspectResult {
		public final String[]		inputNames;
		public final String[]		outputNames;
		public InspectResult(String[] inputNames, String[] outputNames) {
			super();
			this.inputNames = inputNames;
			this.outputNames = outputNames;
		}		
	}
	
	
	
	@Override
	public String toString() {
		return "Component [clazz=" + clazz.getSimpleName() + ", name=" + name + ", inputNames=" + inputNames + ", outputNames=" + outputNames + ", x=" + x
				+ ", y=" + y + "]";
	}
	
	
}

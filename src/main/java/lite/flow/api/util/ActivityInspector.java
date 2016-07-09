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
package lite.flow.api.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import lite.flow.api.activity.Output;


/**
 * 	Discover Component inputs, outputs, parameters, resources, etc.
 * 
 * 
 * 
 * @author ToivoAdams
 *
 */
public class ActivityInspector {
	
	static public String[] getOutputNames(Class<?> clazz) {
		
		ArrayList<Field>  outputs = getOutputs(clazz);
		String[] outputNames = new String[outputs.size()];
		
		for (int i = 0; i < outputNames.length; i++) {
			outputNames[i] = outputs.get(i).getName();
		}
		return outputNames;
	}

	public static class InputGroup {
		public final Method method;
		public final String[] inputNames;
		public InputGroup(Method method, String[] inputNames) {
			super();
			this.method = method;
			this.inputNames = inputNames;
		}		
	}
	
	static public InputGroup[] getInputs(Class<?> clazz) {
		
		ArrayList<Method> entryMethods = getEntryMethods(clazz);
		InputGroup[] inputGroups = new InputGroup[entryMethods.size()];
				
		int i = 0;
		for (Method entryMethod : entryMethods) {
			String[] inputNames = getArgumentNames(entryMethod);
			InputGroup inputGroup = new InputGroup(entryMethod, inputNames);
			inputGroups[i++] = inputGroup;
		}
		return inputGroups;
	}

	static public ArrayList<Field> getOutputs(Class<?> clazz) {
		
		ArrayList<Field> outputs = new ArrayList<>();
		
		for (Field field : clazz.getDeclaredFields()) {
			Class<?> decl = field.getType();
			if (Output.class.isAssignableFrom(decl)) {
				outputs.add(field);
			}
		}
		
		return outputs;
	}
	
	/**
	 *  Component can have multiple Entry methods.
	 * @param clazz
	 * @return
	 */
	static public ArrayList<Method> getEntryMethods(Class<?> clazz) {
		
		ArrayList<Method> methods = new ArrayList<>();
		
		for (Method method : clazz.getDeclaredMethods()) {
			if (Modifier.isPublic(method.getModifiers())) {
				methods.add(method);				
			}
		}

		return methods;
	}
	
	static public String[] getArgumentNames(Method method) {
		String[] names = new String[method.getParameters().length];
		for (int i = 0; i < method.getParameters().length; i++) {
			String name = method.getParameters()[i].getName();
			names[i] = name;
		}
		
		return names;
	}

}

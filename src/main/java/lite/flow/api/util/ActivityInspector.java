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
import org.apache.commons.lang3.reflect.MethodUtils;

import lite.flow.api.activity.Entry;
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
	
	public static final InspectResult inspect(Class<?> clazz) {
		EntryPoint[] entryPoints = getEntryPoints(clazz);
		String[] outputNames = getOutputNames(clazz);
		if ((outputNames==null || outputNames.length<1) && entryPoints!=null) {
			// when we don't have any Output defined, entry methods defines output names
			outputNames = new String[entryPoints.length];
			int i = 0;
			for (EntryPoint entryPoint : entryPoints) {
				outputNames[i++] = entryPoint.outputName;
			}
		}
		return new InspectResult(entryPoints, outputNames);
	}

	public static class InspectResult {
		public final EntryPoint[]	entryPoints;
		public final String[]		outputNames;
		public InspectResult(EntryPoint[] entryPoints, String[] outputNames) {
			super();
			this.entryPoints = entryPoints;
			this.outputNames = outputNames;
		}
		
		public String[] getInputNames() {
			return flat(entryPoints);
		}
	}

	public static class EntryPoint {
		public final Method method;
		public final String[] inputNames;
		public final String outputName;
		public EntryPoint(Method method, String[] inputNames, String outputName) {
			super();
			this.method = method;
			this.inputNames = inputNames;
			this.outputName = outputName;
		}		
	}

	static public EntryPoint[] getEntryPoints(Class<?> clazz) {
		
		EntryPoint[] entryPoints = getEntryAnnotatedMethods(clazz);
		if (entryPoints!=null && entryPoints.length>0)
			return entryPoints;
		
		ArrayList<Method> entryMethods = getEntryMethods(clazz);
		entryPoints = new EntryPoint[entryMethods.size()];
				
		int i = 0;
		for (Method entryMethod : entryMethods) {
			String[] inputNames = getArgumentNames(entryMethod);
			EntryPoint entryPoint = new EntryPoint(entryMethod, inputNames, entryMethod.getName());
			entryPoints[i++] = entryPoint;
		}
		return entryPoints;
	}

	static public String[] getOutputNames(Class<?> clazz) {
		
		ArrayList<Field>  outputs = getOutputs(clazz);
		String[] outputNames = new String[outputs.size()];
		
		for (int i = 0; i < outputNames.length; i++) {
			outputNames[i] = outputs.get(i).getName();
		}
		return outputNames;
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

	protected static String[] flat(EntryPoint[] entryPoints) {
		int totalInputs = 0;
		for (EntryPoint entryPoint : entryPoints) {
			totalInputs += entryPoint.inputNames.length;
		}
		
		int i = 0; 
		String[] allInputNames = new String[totalInputs];
		for (EntryPoint entryPoint : entryPoints)
			for (String name : entryPoint.inputNames)
				allInputNames[i++] = name;

		return allInputNames;
	}

	protected static EntryPoint[] getEntryAnnotatedMethods(Class<?> clazz) {
		Method[] methods = MethodUtils.getMethodsWithAnnotation(clazz, Entry.class);

		EntryPoint[] entryPoints = new EntryPoint[methods.length];
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			Entry annotation = method.getAnnotation(Entry.class);
			EntryPoint entryPoint = new EntryPoint(method, annotation.inNames(), annotation.outName());
			entryPoints[i] = entryPoint;
		}

		return entryPoints;
	}

}

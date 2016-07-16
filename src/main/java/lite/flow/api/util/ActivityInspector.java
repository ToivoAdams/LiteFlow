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

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import lite.flow.api.activity.Entry;
import lite.flow.api.activity.Output;
import lite.flow.api.activity.Parameter;


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
		boolean withoutExplicitOutputPort = false;
		if ((outputNames==null || outputNames.length<1) && entryPoints!=null) {
			// when we don't have any Output defined, entry methods defines output names
			withoutExplicitOutputPort = true;
			outputNames = new String[entryPoints.length];
			int i = 0;
			for (EntryPoint entryPoint : entryPoints) {
				outputNames[i++] = entryPoint.outputName;
			}
		}
		
		FieldInfo[] resources = discoverResources(clazz);
		FieldInfo[] parameters = discoverParameters(clazz);
		
		return new InspectResult(entryPoints, outputNames, withoutExplicitOutputPort, resources, parameters);
	}

	public static class FieldInfo {
		public final String name;
		public final Class<?> type;
		public FieldInfo(String name, Class<?> type) {
			super();
			this.name = name;
			this.type = type;
		}
	}

	public static class InspectResult {
		public final EntryPoint[]	entryPoints;
		public final String[]		outputNames;
		public final boolean 		withoutExplicitOutputPort;
		public final FieldInfo[]	resources;
		public final FieldInfo[]	parameters;
		public InspectResult(EntryPoint[] entryPoints, String[] outputNames, boolean withoutExplicitOutputPort
				, FieldInfo[] resources, FieldInfo[] parameters) {
			super();
			this.entryPoints = entryPoints;
			this.outputNames = outputNames;
			this.withoutExplicitOutputPort = withoutExplicitOutputPort;
			this.resources = resources;
			this.parameters = parameters;
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
			requireNonNull(method, "EntryPoint() inputNames should not be null");
			requireNonNull(inputNames, "EntryPoint() inputNames should not be null");
			requireNonNull(outputName, "EntryPoint() outputName should not be null");
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

	protected static FieldInfo[] discoverParameters(Class<?> clazz) {
		Field[] fields = FieldUtils.getFieldsWithAnnotation(clazz, Parameter.class);
		FieldInfo[] fieldInfos = new FieldInfo[fields.length];
		for (int i = 0; i < fieldInfos.length; i++) {
			Field field = fields[i];
			fieldInfos[i] = new FieldInfo(field.getName(), field.getType());
		}

		return fieldInfos;
	}

	protected static FieldInfo[] discoverResources(Class<?> clazz) {
		Field[] fields = FieldUtils.getFieldsWithAnnotation(clazz, Resource.class);
		FieldInfo[] fieldInfos = new FieldInfo[fields.length];
		for (int i = 0; i < fieldInfos.length; i++) {
			Field field = fields[i];
			fieldInfos[i] = new FieldInfo(field.getName(), field.getType());
		}

		return fieldInfos;
	}


}

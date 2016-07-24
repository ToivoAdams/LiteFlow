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

import static java.util.Objects.requireNonNull;
import static lite.flow.util.ActivityInspector.*;

import java.util.HashMap;
import java.util.Map;


public class Component extends Activity {
	public final Class<?> 	componentClazz;

	public Map<String, Object> parameters = new HashMap<>();
	
	
	public Component(Class<?> componentClazz, String name, int x, int y) {
		super(name, inspect(componentClazz).getInputNames(), inspect(componentClazz).outputNames, x, y);
		this.componentClazz = componentClazz;
	}

	public int maxNumberOfPorts() {
		if (inputNames.length>outputNames.length)
			return inputNames.length;
		else
			return outputNames.length;
	}


	@Override
	public String toString() {
		return "Component [clazz=" + componentClazz.getSimpleName() + ", name=" + name + ", inputNames=" + inputNames + ", outputNames=" + outputNames + ", x=" + x
				+ ", y=" + y + "]";
	}
	
}

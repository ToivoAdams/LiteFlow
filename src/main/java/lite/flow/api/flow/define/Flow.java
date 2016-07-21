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

import java.util.Arrays;
import java.util.Map;

/**
 * 	Used to define Flow.
 * 
 * @author ToivoAdams
 *
 */
public class Flow {

	public final String flowName;

	final public Activity[]		activities;
	final public Connection[]	connections;

	final public InputConnection[]	flowInputs;
	final public OutputConnection[] flowOutputs;

	
	public Flow(String flowName, Activity[] activities, Connection[] connections, InputConnection[] flowInputs, OutputConnection[] flowOutputs) {
		super();
		this.flowName = flowName;
		this.activities = activities;
		this.connections = connections;
		this.flowInputs = flowInputs;
		this.flowOutputs = flowOutputs;
	}

	@Override
	public String toString() {
		return "Flow [flowName=" + flowName + ", activities=" + Arrays.toString(activities) + ", connections="
				+ Arrays.toString(connections) + ", flowInputs=" + Arrays.toString(flowInputs) + ", flowOutputs="
				+ Arrays.toString(flowOutputs) + "]";
	}

}

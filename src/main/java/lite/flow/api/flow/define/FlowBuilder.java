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

import java.util.ArrayList;


/**
 * 	Helps to build Flow.
 * 
 * @author ToivoAdams
 *
 */
public class FlowBuilder {

	public final String flowName;

	final public ArrayList<Activity>	activities = new ArrayList<>();
	final public ArrayList<Connection>	connections = new ArrayList<>();

	final public ArrayList<InputConnection>	flowInputs = new ArrayList<>();
	final public ArrayList<OutputConnection> flowOutputs = new ArrayList<>();
	
	public FlowBuilder(String flowName) {
		super();
		this.flowName = flowName;
	}

	static public FlowBuilder defineFlow(String flowName) {
		return new FlowBuilder(flowName);
	}
	
	/**
	 * Used to define component which is Java class based.
	 * @param componentName
	 * @param clazz
	 * @param x
	 * @param y
	 * @return
	 */
	public FlowBuilder component(String componentName, Class<?> clazz, int x, int y) {
		activities.add(new Component(clazz, componentName, x, y));
		return this;
	}	

	/**
	 * Used to connect 2 activities output and input ports
	 * @param from			from activity
	 * @param fromPort		from port
	 * @param to			to activity
	 * @param toPort		to port
	 * @return
	 */
	public FlowBuilder connect(String from, String fromPort, String to, String toPort) {
		Activity fromActivity = findActivity(from);
		Activity toActivity = findActivity(to);
		connections.add(new Connection(fromActivity, toActivity, fromPort, toPort));
		return this;
	}

	public FlowBuilder flowInput(String inputName, String to, String toPort) {
		Activity toActivity = findActivity(to);
		flowInputs.add(new InputConnection(inputName, toActivity, toPort));
		return this;
	}
	
	public FlowBuilder flowOutput(String outputName, String from, String fromPort) {
		Activity fromActivity = findActivity(from);
		flowOutputs.add(new OutputConnection(outputName, fromActivity, fromPort));
		return this;
	}

	public Flow get() {
		return new Flow(flowName, activities.toArray(new Activity[activities.size()]), connections.toArray(new Connection[connections.size()])
				, flowInputs.toArray(new InputConnection[flowInputs.size()]), flowOutputs.toArray(new OutputConnection[flowOutputs.size()]));
	}	

	private Activity findActivity(String activityName) {
		requireNonNull(activityName, "Activity name cannot be null");

		for (Activity activity : activities)
			if (activityName.equals(activity.name))
					return activity;
		
		throw new IllegalArgumentException("Activity '" + activityName + "' does not exist");
	}

}

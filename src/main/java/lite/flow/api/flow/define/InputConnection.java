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

/**
 * 	Connection between input and activity.
 * For example between flow input and activity in flow.
 * Used only to build flow, don't use directly.
 * 
 * @author ToivoAdams
 *
 */
public class InputConnection {

	public final String		inputName;
	public final Activity 	to;
	public final String		toPort;

	public InputConnection(String inputName, Activity to, String toPort) {
		super();
		this.inputName = inputName;
		this.to = to;
		this.toPort = toPort;
	}
	
}

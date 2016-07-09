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
 * 	Connection between 2 activities.
 * Used only to build flow, don't use directly.
 * 
 * @author ToivoAdams
 *
 */
public class Connection {

	public final Activity	from;
	public final Activity 	to;
	public final String		fromPort;
	public final String		toPort;

	public Connection(Activity from, Activity to, String fromPort, String toPort) {
		super();
		this.from = from;
		this.to = to;
		this.fromPort = fromPort;
		this.toPort = toPort;
	}
	
}

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
 * 	Activity has inputs and produces outputs.
 * Each Activity have unique name in flow, inputs names and outputs names.
 * 
 * 2 main subclasses:
 * 
 *  Component is unbreakable processing unit (processor) 
 *  and use java class which should have transform method for doing actual work.
 *  
 *  SubFlow which contain Flow (instead of java class).
 *  SubFlow can also contain other subflow's (so many level nesting is allowed).
 *
 * 
 */
public abstract class Activity {
	
	public final String			name;
	public final String[]		inputNames;
	public final String[]		outputNames;
	public final int			x;
	public final int			y;
	
	public Activity(String name, String[] inputNames, String[] outputNames,int x, int y) {
		super();
		this.name = name;
		this.inputNames = inputNames;
		this.outputNames = outputNames;
		this.x = x;
		this.y = y;
	}


}

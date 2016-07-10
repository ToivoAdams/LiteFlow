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
package lite.log.api;

/**
 * Encapsulates information about an request. 
 * Includes unique request id.
 * RequestContext is attached to every DataMessage and used to correlate DataMessages.
 * 
 * Might include request sender info.
 * 
 * @author ToivoAdams
 *
 */
public interface RequestContext {
	
	public UniqueId getRequestId();

	/**
	 * 	Creates subordinate.
	 * Used when we must spawn new RequestContext from original RequestContext.
	 * New RequestContext has requestId which has also original RequestContext requestId info.
	 * So we can also check is it created from some other RequestContext.
	 * 
	 * @return
	 */
	public RequestContext getSubRequestContext();
	
	
	
}

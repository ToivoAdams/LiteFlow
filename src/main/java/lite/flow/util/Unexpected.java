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
package lite.flow.util;

import java.io.Serializable;
import java.util.logging.Level;

import lite.flow.api.activity.RequestContext;

/**
 * 	Unexpected situation info.
 * Could be serious (Level.SEVERE, Level.WARNING) or just remark (Level.INFO)
 * 
 * Could be also serious Unexpected event. 
 * 
 * @author ToivoAdams
 *
 */
public class Unexpected implements Serializable {

	private static final long serialVersionUID = 1L;

	public final Level level;
	public final String msg;
	public final String msgCode;
	public final Throwable throwable;
	public final RequestContext 	requestContext;
	
	public Unexpected(Level level, String msg, RequestContext requestContext) {
		this.level = level;
		this.msg = msg;
		this.msgCode = null;
		this.throwable = null;
		this.requestContext = requestContext;
	}

	public Unexpected(Level level, String msg, String msgCode) {
		this.level = level;
		this.msg = msg;
		this.msgCode = msgCode;
		this.throwable = null;
		this.requestContext = null;
	}

	public Unexpected(Level level, String msg, RequestContext requestContext, Throwable throwable) {
		this.level = level;
		this.msg = msg;
		this.msgCode = null;		
		this.throwable = throwable;
		this.requestContext = requestContext;
	}

}

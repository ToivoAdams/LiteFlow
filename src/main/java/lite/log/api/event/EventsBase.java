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
package lite.log.api.event;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import lite.log.api.UniqueId;

abstract public class EventsBase extends LogRecord {

	private static final long serialVersionUID = 1L;

	/** correlated events should have same id, for example task or job start and end events */
	public final UniqueId 		eventCorrelationId;

	public EventsBase(Level level, String msg, UniqueId eventCorrelationId) {
		super(level, msg);
		Objects.requireNonNull(eventCorrelationId, "eventCorrelationId should not be null");
		this.eventCorrelationId = eventCorrelationId;
	}

	
}

package org.prototype.events;

import java.util.LinkedList;

public class EventQueue {
	public LinkedList<Event> events;
	
	public EventQueue() {
		events = new LinkedList<Event>();
	}

	public Event getNextEvent(double time) {
		if(events.peek() != null && events.peek().eventTiming < time) {
			Event e = events.pop();
			
			return e;
		}
		return null;
	}
}

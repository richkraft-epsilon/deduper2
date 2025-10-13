package com.dotomi.interview;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Tasks: 
 * 
 *  1) Implement deduping logic using only event id
 *  2) Dedupe events going into the BusinessLogicProcessor
 *  3) Create another deduping version which dedupes by the same user, event type, and timestamp within a configurable ttl
 *  4) Create a single unit test and describe other scenarios you would test
 *  5) Compose the two deduping logics such that further rules are easily added
 *  6) Make the Processor threadsafe
 *  7) Make the Processor interface asynchronous
 */
public class Main {

	public static void main(String[] args) {
	    System.out.println("Starting...");
	    
		Instant startTime = Instant.parse("2010-01-01T00:00:00Z");
		BusinessLogicProcessor processor = new BusinessLogicProcessor();

		List<Event> events = List.of(
				new Event("abc123", "user1", "click", startTime),
				new Event("def456", "user2", "impression", startTime.plus(Duration.ofMinutes(1))),
				new Event("ghi789", "user1", "impression", startTime.plus(Duration.ofMinutes(5))),
				new Event("abc123", "user1", "click", startTime) // duplicate
		);

		// TODO: Loop through each event to call processEvent method, and print processed and duplicate events
	}

	/**
	 * Bonus points if you can rewrite this class in a single line ;)
	 */
	public static final class Event {

		String eventId;
		String userId;
		String type;
		Instant timestamp;

		Event(String eventId, String userId, String type, Instant timestamp) {
			this.eventId = eventId;
			this.userId = userId;
			this.type = type;
			this.timestamp = timestamp;
		}

		@Override
		public String toString() {
			return String.format("{eventId=%s, userId=%s, type=%s, timestamp=%s}",
					eventId, userId, type, timestamp);
		}
	}

	/**
	 * This class is final and cannot be modified.
	 */
	public static class BusinessLogicProcessor {

        /**
		 * returns true if the event is successfully processed, false if the event is not successfully processed and should be seen again.
		 */
		public boolean processEvent(Event e) {
<<<<<<< HEAD
=======
			if (deduper.isDuplicate(e)) {
				return false;
			}

>>>>>>> 39adab4 (fix description)
			/*
			 * Some abstract black box processing of the event
			 */
			return true;
		}
	}
}

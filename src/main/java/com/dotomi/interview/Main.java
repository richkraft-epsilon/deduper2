package com.dotomi.interview;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Tasks: 
 * 
 *  1) Implement deduping logic using only event id
 *  2) Implement deduping logic using user, type and timestamp
 *  3) Implement deduping logic using user, type and a timestamp within a second
 *  4) Create a single unit test and describe other scenarios you would test
 *  5) Use composition to combine the Dedupers, such that future definitions are easily added
 *  6) Make the Deduper threadsafe
 *  7) Make the Deduper interface asynchronous
 * 
 */
public class Main {

	public static void main(String[] args) {
	    System.out.println("Starting...");
	    
		final Instant startTime = Instant.parse("2010-01-01T00:00:00Z");
		final BusinessLogicProcessor processor = new BusinessLogicProcessor(new StepOneDeduper());

		final List<Event> events = List.of(
				new Event("abc123", "user1", "click", startTime),
				new Event("def456", "user2", "impression", startTime.plus(Duration.ofMinutes(1))),
				new Event("ghi789", "user1", "impression", startTime.plus(Duration.ofMinutes(2))),
				new Event("abc123", "user1", "click", startTime), // duplicate for step 1
				new Event("abc123-1", "user1", "click", startTime), // fuzzy duplicate for step 2
				new Event("abc123-2", "user1", "click", startTime.plus(Duration.ofMillis(1))) // fuzzy duplicate for step 3
		);

		for (Event e : events) {
			final boolean processed = processor.processEvent(e);
			System.out.println(String.format("Event %s processed: %s", e, processed));
		}
	}

	/**
	 * Bonus points if you can rewrite this class in a single line ;)
	 */
	public static final class Event {

		private String eventId;
		private String userId;
		private String type;
		private Instant timestamp;

		Event(String eventId, String userId, String type, Instant timestamp) {
			this.eventId = eventId;
			this.userId = userId;
			this.type = type;
			this.timestamp = timestamp;
		}

		public String getEventId() {
			return eventId;
		}

		public String getUserId() {
			return userId;
		}

		public String getType() {
			return type;
		}

		public Instant getTimestamp() {
			return timestamp;
		}

		@Override
		public String toString() {
			return String.format("{eventId=%s, userId=%s, type=%s, timestamp=%s}",
					eventId, userId, type, timestamp);
		}
	}

	public static class StepOneDeduper {
		/**
		 * returns true if the event is a duplicate, false otherwise
		 */
		public boolean isDuplicate(Event e) {
			return false;
		}
	}

	public static class StepTwoDeduper {
		/**
		 * returns true if the event is a duplicate, false otherwise
		 */
		public boolean isDuplicate(Event e) {
			return false;
		}
	}

	public static class StepThreeDeduper {
		/**
		 * returns true if the event is a duplicate, false otherwise
		 */
		public boolean isDuplicate(Event e) {
			return false;
		}
	}

	/**
	 * This class is final and cannot be modified.
	 */
	public static class BusinessLogicProcessor {

		private final StepOneDeduper deduper;

		public BusinessLogicProcessor(final StepOneDeduper deduper) {
			this.deduper = deduper;
		}

        /**
		 * returns true if the event is successfully processed, false if the event is not successfully processed.
		 */
		public boolean processEvent(Event e) {
			if (deduper.isDuplicate(e)) {
				return false;
			}
			/*
			 * Some abstract black box processing of the event
			 */
			return true;
		}
	}
}

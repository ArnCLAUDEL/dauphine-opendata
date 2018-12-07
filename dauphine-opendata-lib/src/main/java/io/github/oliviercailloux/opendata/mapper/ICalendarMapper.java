package io.github.oliviercailloux.opendata.mapper;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;

import biweekly.ICalendar;
import biweekly.component.VEvent;
import io.github.oliviercailloux.opendata.entity.Lecture;
import io.github.oliviercailloux.opendata.entity.Planning;

/**
 * A mapper class used to convert Planning entities to ICalendar entities
 * 
 * @author GERVRAUD Thomas
 * @author Ouafa BOUCENNA
 *
 */
@ApplicationScoped
public class ICalendarMapper {

	public ICalendarMapper() {
	}

	/**
	 * Convert a planning entity into an ICalendar entity
	 * 
	 * @param planning the planning entity to encode, cannot be null
	 * @return the planning encoded as a ICalendar entity
	 */
	public ICalendar encodePlanningToICalendar(Planning planning) {

		ICalendar ical = new ICalendar();

		planning.getLectures().forEach((lecture) -> {
			ical.addEvent(this.transformLectureToEvent(lecture));
		});

		return ical;
	}

	/**
	 * Convert a lecture entity into an event object
	 * 
	 * @param lecture the lecture entity to encode, cannot be null
	 * @return the lecture encoded as an Event entity
	 */
	public VEvent transformLectureToEvent(Lecture lecture) {
		VEvent event = new VEvent();
		Date start = lecture.getDate();
		event.setDateStart(start);
		event.setLocation(lecture.getRoom());
		event.setDescription(lecture.getCourse().getDescription());
		return event;
	}

}

package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.LectureDao;
import io.github.oliviercailloux.opendata.entity.Lecture;

@Path("lecture")
public class LectureResource extends AbstractResource<Lecture, LectureDao> {

	private static final String LECTURE_NAME = "Lecture";

	public LectureResource(final LectureDao dao) {
		super(dao, LECTURE_NAME);
	}

	public LectureResource() {
		super(LECTURE_NAME);
	}

}

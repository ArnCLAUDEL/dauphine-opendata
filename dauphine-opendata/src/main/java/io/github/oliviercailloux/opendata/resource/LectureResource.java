package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.LectureDao;
import io.github.oliviercailloux.opendata.entity.Lecture;

@Path("lecture")
public class LectureResource extends AbstractResource<Lecture, LectureDao> {

	private static final String RESOURCE_NAME = "Lecture";
	private static final String RESOURCE_PATH = "lecture";

	public LectureResource(final LectureDao dao) {
		super(dao, RESOURCE_NAME, RESOURCE_PATH);
	}

	public LectureResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}

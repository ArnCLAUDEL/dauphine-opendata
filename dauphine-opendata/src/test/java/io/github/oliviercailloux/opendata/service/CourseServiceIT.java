package io.github.oliviercailloux.opendata.service;

import org.junit.Ignore;

//@RunWith(Arquillian.class)
@Ignore
public class CourseServiceIT {
	/*
	 * private static final Random RANDOM = new Random(1L);
	 *
	 * @Inject private CourseService service;
	 *
	 * @Inject private CourseDao dao;
	 *
	 * @Inject private JsonMapper jsonMapper;
	 *
	 * @Deployment public static WebArchive deployWar() { final File[] files =
	 * Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().
	 * resolve() .withTransitivity().asFile(); final WebArchive war =
	 * ShrinkWrap.create(WebArchive.class) .addPackages(true,
	 * "io.github.oliviercailloux.opendata").addAsResource("arquillian.xml")
	 * .addAsResource("META-INF/persistence.xml").addAsLibraries(files); //
	 * System.out.println(war.toString(true)); return war; }
	 *
	 * private Course injectRandomEntity() { final Course course = new
	 * Course("test-course"); course.setIdCourse("id-" + RANDOM.nextLong());
	 * course.setSpecialty(TypeSpecialty.M2_MIAGE_SITN); dao.persist(course); return
	 * course; }
	 *
	 * private List<Course> persistRandomEntities(final int nbEntities) { final
	 * List<Course> courses = Lists.newLinkedList(); for (int i = 0; i < nbEntities;
	 * i++) { courses.add(injectRandomEntity()); } return courses; }
	 *
	 * private void removeRandomEntities(final List<Course> courses) {
	 * courses.stream().map(Course::getIdCourse).forEach(dao::remove); }
	 *
	 * private void checkCoursesInResult(final List<Course> persistedCourses, final
	 * List<Course> returnedCourses) { assertEquals("nb returned entities KO",
	 * persistedCourses.size(), returnedCourses.size());
	 * persistedCourses.stream().forEach(c ->
	 * assertTrue("pesisted course not present in the result",
	 * returnedCourses.stream().anyMatch(c2 -> c2.equals(c)))); }
	 *
	 * private void testGetAll(final int nbEntities) { final List<Course>
	 * persistedCourses = persistRandomEntities(nbEntities); final
	 * Optional<List<Course>> coursesOpt = service.getAll();
	 * assertTrue("empty result", coursesOpt.isPresent()); final List<Course>
	 * returnedCourses = coursesOpt.get(); checkCoursesInResult(persistedCourses,
	 * returnedCourses); removeRandomEntities(persistedCourses); }
	 *
	 * @Test public void testGetAll() { testGetAll(10); }
	 *
	 * /* private void testGetAllAsJson(final int nbEntities) throws IOException {
	 * final List<Course> persistedCourses = persistRandomEntities(nbEntities);
	 * final Optional<String> jsonOpt = service.getAllAsJson();
	 * assertTrue("empty result", jsonOpt.isPresent()); final String json =
	 * jsonOpt.get(); final List<Course> returnedCourses =
	 * jsonMapper.convertJsonArrayToObjectList(json, Course[].class);
	 * checkCoursesInResult(persistedCourses, returnedCourses);
	 * removeRandomEntities(persistedCourses); }
	 *
	 * @Test public void testGetAllAsJson() throws IOException {
	 * testGetAllAsJson(10); }
	 */

}

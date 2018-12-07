package io.github.oliviercailloux.opendata.entity;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class ObjectsXML {

	public ObjectsXML() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void encodingCourse(Course c, String fileName)
			throws NullPointerException, IllegalArgumentException, Exception {
		try {
			SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = sf.newSchema(new File("CDMFR-ext1.0.xsd"));

			// creation the JAXB contexte under the Course classe
			JAXBContext context = JAXBContext.newInstance(Course.class);

			// creation of the marshaller from this contexte
			Marshaller marshaller = context.createMarshaller();

			// chose the UTF-8 for the encoding file
			// marshaller.setProperty("jaxb.encoding", "UTF-8") ;
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setSchema(schema);
			// marshaller.setEventHandler(new MyValidationEventHandler());

			// ask JAXB to format the file so it can be read by the eye
			// marshaller.setProperty("jaxb.formatted.output", true) ;

			// write the XML file on the fileName
			marshaller.marshal(c, new File(fileName));
		} catch (JAXBException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
	}

	public Course decodingCourse() {
		Course c = null;

		return c;
	}

}

package io.github.oliviercailloux.opendata.mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Ziad and Sofian on 07/02/2018. class utility for converting
 * between an object and JSON using Jackson
 */
@ApplicationScoped
public class JsonMapper {

	/**
	 * Jackson object for convert object to string
	 */
	private ObjectMapper mapper = new ObjectMapper();

	private static final Logger LOGGER = Logger.getLogger(JsonMapper.class.getName());

	/**
	 * method to convert object to JSON, we could have used directly jackson but not
	 * to remain dependent on jackson and to add logs, we go through a utility
	 * method work for list
	 * 
	 * @param object
	 * @return JSON (string)
	 * @throws JsonProcessingException
	 */
	public String convertObjectToJson(Object object) throws JsonProcessingException {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			LOGGER.severe("Impossible to cast " + object.toString() + " to JSON ... \n" + e.getMessage());
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * method to convert JSON to Object, we could have used directly jackson but not
	 * to remain dependent on jackson and to add logs, we go through a utility
	 * method
	 * 
	 * @param json
	 * @param className
	 * @param           <T>
	 * @return object from json
	 * @throws IOException
	 */
	public <T> T convertJsonToObject(String json, Class<T> className) throws IOException {
		try {
			return mapper.readValue(json, className);
		} catch (IOException e) {
			LOGGER.severe("Impossible to cast " + className + " JSON to OBJECT ... \n" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * method to convert a json array to an object
	 * 
	 * @param json
	 * @param className
	 * @param           <T>
	 * @return
	 * @throws IOException
	 */
	public <T> List<T> convertJsonArrayToObjectList(String json, Class<T[]> className) throws IOException {
		T[] objects = mapper.readValue(json, className);
		return Arrays.asList(objects);
	}
}

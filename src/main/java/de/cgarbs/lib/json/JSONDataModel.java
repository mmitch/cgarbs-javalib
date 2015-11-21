/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.JSONException;

/**
 * This adapter maps a DataModel to a JSON representation by
 * writing to a file/reading from a file.
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.3.0
 */
public abstract class JSONDataModel extends JSONAdapter
{
	static final String IDENTIFIER = "de.cgarbs.javalib.json.JSONDataModel representation";
	static final String IDENTIFIER_FIELD = "id";

	static final String VERSION = "1";
	static final String VERSION_FIELD = "version";

	static final String ATTRIBUTE_FIELD = "attributes";

	/**
	 * Converts the DataModel to JSON format and writes the JSON data to the
	 * specified file.
	 *
	 * @param model the DataModel to be converted to JSON
	 * @param file the File to be written to
	 * @throws DataException either IO errors or errors during the JSON conversion
	 */
	public static void writeToFile(DataModel model, File file) throws DataException
	{
		PrintWriter out = null;
		try
		{
			out = new PrintWriter(file);
			out.write( convertToJSON(model) );
			out.close();
		}
		catch (FileNotFoundException e)
		{
			throw wrappedAsDataException(DataException.ERROR.IO_ERROR, e);
		}
		catch (JSONException e)
		{
			throw wrappedAsDataException(DataException.ERROR.JSON_CONVERSION_ERROR, e);
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
		}
		if (out.checkError())
		{
			// FIXME: PrintWriter throws no IOExceptions?!  how to find out what happened?
			throw new DataException(
					DataException.ERROR.IO_ERROR,
					"error writing to file"
					);
		}
	}

	/**
	 * Reads JSON data from the specified file and writes all attribute
	 * values from the JSON data to the appropriate attributes in the
	 * given DataModel.
	 *
	 * @param model the DataModel to be written to
	 * @param file the File containing the JSON data to be read
	 * @throws DataException either IO errors or errors during the JSON conversion
	 */
	public static void readFromFile(DataModel model, File file) throws DataException
	{
		StringBuilder json = new StringBuilder();
		try
		{
			for(Scanner sc = new Scanner(file); sc.hasNext(); )
			{
				json.append(sc.nextLine());
			}
		}
		catch (FileNotFoundException e)
		{
			throw wrappedAsDataException(DataException.ERROR.IO_ERROR, e);
		}

		try
		{
			readFromJSON(
					model,
					getJSONParser()
						.parse(
								json.toString(),
								getOrderedContainerFactory()
							   )
					);
		}
		catch (ParseException e)
		{
			throw wrappedAsDataException(DataException.ERROR.JSON_CONVERSION_ERROR, e);
		}
		catch (JSONException e)
		{
			throw wrappedAsDataException(DataException.ERROR.JSON_CONVERSION_ERROR, e);
		}
	}

	/**
	 * Converts the DataModel to JSON
	 * @param model the DataModel to convert
	 * @return JSON representation of the DataModel
	 * @throws JSONException when there is an error during the JSON conversion
	 * @throws DataException when there is an error during value retrieval from the DataModel
	 */
	static String convertToJSON(DataModel model) throws JSONException, DataException
	{
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		for (String key: model.getAttributeKeys())
		{
			attributes.put(key, prepareForJSON(model.getValue(key)));
		}

		Map<String, Object> json = new LinkedHashMap<String, Object>();
		json.put(IDENTIFIER_FIELD, IDENTIFIER);
		json.put(VERSION_FIELD, VERSION);
		json.put(ATTRIBUTE_FIELD, attributes);

		return JSONValue.toJSONString(json);
	}

	/**
	 * Copies the JSON data to the DataModel's attributs.
	 * @param model the DataModel to write to
	 * @param json the parsed JSON data to read
	 * @throws JSONException when there is an error during the JSON conversion
	 * @throws DataException when there is an error setting a value in die DataModel
	 */
	static void readFromJSON(DataModel model, Object json) throws JSONException, DataException
	{
		if (! (json instanceof Map))
		{
			throw newJSONToJavaError("root element is no map");
		}
		Map<String, Object> jsonMap = (Map<String, Object>) json;

		Object identifier = jsonMap.get(IDENTIFIER_FIELD);
		Object version    = jsonMap.get(VERSION_FIELD);
		Object attributes = jsonMap.get(ATTRIBUTE_FIELD);

		// check null
		if (identifier == null)
		{
			throw newJSONToJavaError("identifier ["+IDENTIFIER_FIELD+"] is missing");
		}
		if (version == null)
		{
			throw newJSONToJavaError("version ["+VERSION_FIELD+"] is missing");
		}
		if (attributes == null)
		{
			throw newJSONToJavaError("attributes ["+ATTRIBUTE_FIELD+"] are missing");
		}

		// check values
		if (! IDENTIFIER.equals(identifier))
		{
			throw newJSONToJavaError("wrong identifier ["+IDENTIFIER_FIELD+"]: expected <"+IDENTIFIER+">, but got <"+identifier.toString()+">");
		}
		if (! VERSION.equals(version))
		{
			throw newJSONToJavaError("wrong version ["+VERSION_FIELD+"]: expected <"+VERSION+">, but got <"+version.toString()+">");
		}
		if (! (attributes instanceof Map))
		{
			throw newJSONToJavaError("wrong attributes ["+ATTRIBUTE_FIELD+"]: expected a <Map> but got a <"+attributes.getClass().toString()+">");
		}

		Map<String, Object> attributeMap = (Map<String, Object>) attributes;
		Set<String> validAttributes = model.getAttributeKeys();
		for (String key: attributeMap.keySet())
		{
			if (validAttributes.contains(key))
			{
				model.setValue(key, JSONAdapter.prepareForJava(attributeMap.get(key)));
			}
		}
	}

	/**
	 * Generates a JSONException with {@link JSONException.ERROR#JSON_TO_JAVA}
	 * and a given error text.
	 * @param errorText the error text
	 * @return the freshly constructed JSONException
	 */
	private static JSONException newJSONToJavaError(String errorText)
	{
		return new JSONException(
				JSONException.ERROR.JSON_TO_JAVA,
				errorText
				);
	}

	/**
	 * Wraps another exception in a DataException.
	 *
	 * @param error the DataException error code to use
	 * @param t the original exception
	 * @return the freshly constructed DataException
	 */
	private static DataException wrappedAsDataException(DataException.ERROR error, Throwable t)
	{
		return new DataException(
				error,
				t
				);
	}
}

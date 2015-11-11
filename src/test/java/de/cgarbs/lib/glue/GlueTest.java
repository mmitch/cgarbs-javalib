package de.cgarbs.lib.glue;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.i18n.Resource;

public class GlueTest
{
	final String  GIVEN_STRING  = "some foo bars";
	final Integer GIVEN_INTEGER = -13;
	final Boolean GIVEN_BOOLEAN = true;
	
	Binding stringBinding;
	Binding integerBinding;
	Binding booleanBinding;
	
	DataAttribute stringAttribute;
	DataAttribute integerAttribute;
	DataAttribute booleanAttribute;
	
	Glue<GlueTestDataModel> glue;
	GlueTestDataModel dataModel;
	Map<String,Binding> bindings;
	
	@Before
	public void setUp() throws DataException, GlueException
	{
		final Resource resource = new Resource();
		dataModel = new GlueTestDataModel(resource);

		glue = new Glue<GlueTestDataModel>(dataModel);
		
		bindings = new HashMap<String, Binding>();
		for (final String attributeId: dataModel.getAttributeKeys())
		{
			bindings.put(attributeId, glue.addBinding(attributeId));
		}

		stringBinding  = bindings.get(GlueTestDataModel.STRING_ATTRIBUTE);
		integerBinding = bindings.get(GlueTestDataModel.INTEGER_ATTRIBUTE);
		booleanBinding = bindings.get(GlueTestDataModel.BOOLEAN_ATTRIBUTE);
		
		stringAttribute  = dataModel.getAttribute(GlueTestDataModel.STRING_ATTRIBUTE);
		integerAttribute = dataModel.getAttribute(GlueTestDataModel.INTEGER_ATTRIBUTE);
		booleanAttribute = dataModel.getAttribute(GlueTestDataModel.BOOLEAN_ATTRIBUTE);
	}
	
	@Test
	public void checkCompleteness() throws DataException
	{
		// actual
		final List<DataAttribute> attributesFromGlue = new ArrayList<DataAttribute>();
		for (final Binding binding: glue.bindings)
		{
			attributesFromGlue.add(binding.getAttribute());
		}
		
		// expected
		final List<DataAttribute> attributesFromModel = new ArrayList<DataAttribute>();
		for (final String attributeId: dataModel.getAttributeKeys())
		{
			attributesFromModel.add(dataModel.getAttribute(attributeId));
		}

		// check in both directions, for the case, that there are duplicate entries in one of them
		assertThat(attributesFromGlue, containsInAnyOrder(attributesFromModel.toArray()));
		assertThat(attributesFromModel, containsInAnyOrder(attributesFromGlue.toArray()));
	}
	
	@Test
	public void checkSyncToModel() throws DataException
	{
		stringBinding.setViewValue(String.valueOf(GIVEN_STRING));
		integerBinding.setViewValue(String.valueOf(GIVEN_INTEGER));
		booleanBinding.setViewValue(GIVEN_BOOLEAN);
		
		assertThat(String.valueOf(stringAttribute.getValue()),  is(not(equalTo(String.valueOf(GIVEN_STRING)))));
		assertThat(String.valueOf(integerAttribute.getValue()), is(not(equalTo(String.valueOf(GIVEN_INTEGER)))));
		assertThat((Boolean)      booleanAttribute.getValue(),  is(not(equalTo(GIVEN_BOOLEAN))));
		
		glue.syncToModel();

		assertThat(String.valueOf(stringAttribute.getValue()),  is(equalTo(String.valueOf(GIVEN_STRING))));
		assertThat(String.valueOf(integerAttribute.getValue()), is(equalTo(String.valueOf(GIVEN_INTEGER))));
		assertThat((Boolean)      booleanAttribute.getValue(),  is(equalTo(GIVEN_BOOLEAN)));
	}
	
	@Test
	public void checkSyncToView() throws DataException
	{
		stringAttribute.setValue(GIVEN_STRING);
		integerAttribute.setValue(GIVEN_INTEGER);
		booleanAttribute.setValue(GIVEN_BOOLEAN);
		
		assertThat(String.valueOf(stringBinding.getViewValue()),  is(not(equalTo(String.valueOf(GIVEN_STRING)))));
		assertThat(String.valueOf(integerBinding.getViewValue()), is(not(equalTo(String.valueOf(GIVEN_INTEGER)))));
		assertThat(String.valueOf(booleanBinding.getViewValue()), is(not(equalTo(String.valueOf(GIVEN_BOOLEAN)))));
		
		glue.syncToView();

		assertThat(String.valueOf(stringBinding.getViewValue()),  is(equalTo(String.valueOf(GIVEN_STRING))));
		assertThat(String.valueOf(integerBinding.getViewValue()), is(equalTo(String.valueOf(GIVEN_INTEGER))));
		assertThat(String.valueOf(booleanBinding.getViewValue()), is(equalTo(String.valueOf(GIVEN_BOOLEAN))));
	}
	
}

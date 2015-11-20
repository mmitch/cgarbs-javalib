/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue.type;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.glue.GlueTestDataModel;

public class StringBindingTest extends BaseBindingTest
{
	final String MODEL_GIVEN_VALUE_1 = "tiguar";
	final String MODEL_GIVEN_VALUE_2 = "cheYenne";
	final String MODEL_NULL_VALUE    = null;

	final String VIEW_GIVEN_VALUE_1 = "tiguar";
	final String VIEW_GIVEN_VALUE_2 = "cheYenne";
	final String VIEW_NULL_VALUE    = "";

	@Before
	public void setUp() throws DataException, GlueException
	{
		setUp(GlueTestDataModel.STRING_ATTRIBUTE);
	}

	@Test
	public void checkConstructor()
	{
		assertThat(binding, is(not(nullValue())));
		assertThat(getViewValue(), is(equalTo(VIEW_NULL_VALUE)));
		assertThat(getModelValue(), is(equalTo(MODEL_NULL_VALUE)));
		assertThat(binding.getTxtLabel(), is(equalTo(getLabel())));


		assertThat(binding.getJLabel(), is(instanceOf(JLabel.class)));
		final JLabel jLabel = (JLabel) binding.getJLabel();
		assertThat(jLabel.getText(), is(equalTo(getLabel())));

		assertThat(binding.getJData(), is(instanceOf(JTextField.class)));
		final JTextField jData  = (JTextField) binding.getJData();
		assertThat(jData.getText(), is(VIEW_NULL_VALUE));
	}

	@Test
	public void checkSyncToModel() throws DataException
	{
		binding.setViewValue(VIEW_GIVEN_VALUE_1);
		assertThat(getModelValue(), is(not(equalTo(MODEL_GIVEN_VALUE_1))));
		syncToModel();
		assertThat(getModelValue(), is(equalTo(MODEL_GIVEN_VALUE_1)));

		binding.setViewValue(VIEW_GIVEN_VALUE_2);
		assertThat(getModelValue(), is(not(equalTo(MODEL_GIVEN_VALUE_2))));
		syncToModel();
		assertThat(getModelValue(), is(equalTo(MODEL_GIVEN_VALUE_2)));

		binding.setViewValue(null);
		assertThat(getModelValue(), is(not(equalTo(MODEL_NULL_VALUE))));
		syncToModel();
		assertThat(getModelValue(), is(not(equalTo(MODEL_NULL_VALUE))));
	}

	@Test
	public void checkSyncToView() throws DataException
	{
		dataAttribute.setValue(MODEL_GIVEN_VALUE_1);
		assertThat(getViewValue(), is(not(equalTo(VIEW_GIVEN_VALUE_1))));
		syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_GIVEN_VALUE_1)));

		dataAttribute.setValue(MODEL_GIVEN_VALUE_2);
		assertThat(getViewValue(), is(not(equalTo(VIEW_GIVEN_VALUE_2))));
		syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_GIVEN_VALUE_2)));

		dataAttribute.setValue(null);
		assertThat(getViewValue(), is(not(equalTo(VIEW_NULL_VALUE))));
		syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_NULL_VALUE)));
	}

	private String getModelValue()
	{
		return (String) dataAttribute.getValue();
	}

	private String getViewValue()
	{
		return (String) binding.getViewValue();
	}

}

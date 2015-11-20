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
import javax.swing.JProgressBar;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.glue.GlueTestDataModel;

public class ProgressBarBindingIntTest extends BaseBindingTest
{
	final Integer MODEL_GIVEN_VALUE_1 = Integer.valueOf(GlueTestDataModel.PROGRESSBAR_MAX_VALUE);
	final Integer MODEL_GIVEN_VALUE_2 = Integer.valueOf((GlueTestDataModel.PROGRESSBAR_MIN_VALUE + GlueTestDataModel.PROGRESSBAR_MAX_VALUE) / 2);
	final Integer MODEL_NULL_VALUE    = null;

	final Integer VIEW_GIVEN_VALUE_1 = Integer.valueOf(GlueTestDataModel.PROGRESSBAR_MAX_VALUE);
	final Integer VIEW_GIVEN_VALUE_2 = Integer.valueOf((GlueTestDataModel.PROGRESSBAR_MIN_VALUE + GlueTestDataModel.PROGRESSBAR_MAX_VALUE) / 2);
	final Integer VIEW_NULL_VALUE    = null;

	@Before
	public void setUp() throws DataException, GlueException
	{
		setUp(GlueTestDataModel.PROGRESSBAR_INT_ATTRIBUTE, ProgressBarBinding.class);
	}

	@Test
	public void checkConstructor()
	{
		assertThat(binding, is(not(nullValue())));
		assertThat(getViewValue(), is(equalTo(GlueTestDataModel.PROGRESSBAR_MIN_VALUE)));
		assertThat(getModelValue(), is(equalTo(MODEL_NULL_VALUE)));
		assertThat(binding.getTxtLabel(), is(equalTo(getLabel())));


		assertThat(binding.getJLabel(), is(instanceOf(JLabel.class)));
		final JLabel jLabel = (JLabel) binding.getJLabel();
		assertThat(jLabel.getText(), is(equalTo(getLabel())));

		assertThat(binding.getJData(), is(instanceOf(JProgressBar.class)));
		final JProgressBar jData  = (JProgressBar) binding.getJData();
		assertThat(jData.getValue(), is(GlueTestDataModel.PROGRESSBAR_MIN_VALUE));
		assertThat(getViewPercent(), is(0d));
	}

	@Test
	public void checkSyncToModel() throws DataException
	{
		binding.setViewValue(VIEW_GIVEN_VALUE_1);
		assertThat(getModelValue(), is(not(equalTo(MODEL_GIVEN_VALUE_1))));
		syncToModel();
		assertThat(getModelValue(), is(equalTo(MODEL_GIVEN_VALUE_1)));
		assertThat(getViewPercent(), is(1d));

		binding.setViewValue(VIEW_GIVEN_VALUE_2);
		assertThat(getModelValue(), is(not(equalTo(MODEL_GIVEN_VALUE_2))));
		syncToModel();
		assertThat(getModelValue(), is(equalTo(MODEL_GIVEN_VALUE_2)));
		assertThat(getViewPercent(), is(0.5d));

		// null value is ignored, stays at the last given value
		binding.setViewValue(null);
		assertThat(getModelValue(), is(not(equalTo(MODEL_NULL_VALUE))));
		syncToModel();
		assertThat(getModelValue(), is(equalTo(MODEL_GIVEN_VALUE_2)));
		assertThat(getViewPercent(), is(0.5d));
	}

	@Test
	public void checkSyncToView() throws DataException
	{
		dataAttribute.setValue(MODEL_GIVEN_VALUE_1);
		assertThat(getViewValue(), is(not(equalTo(VIEW_GIVEN_VALUE_1))));
		syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_GIVEN_VALUE_1)));
		assertThat(getViewPercent(), is(1d));

		dataAttribute.setValue(MODEL_GIVEN_VALUE_2);
		assertThat(getViewValue(), is(not(equalTo(VIEW_GIVEN_VALUE_2))));
		syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_GIVEN_VALUE_2)));
		assertThat(getViewPercent(), is(0.5d));

		// null value is ignored, stays at the last given value
		dataAttribute.setValue(null);
		assertThat(getViewValue(), is(not(equalTo(VIEW_NULL_VALUE))));
		syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_GIVEN_VALUE_2)));
		assertThat(getViewPercent(), is(0.5d));
	}

	private Integer getModelValue()
	{
		return (Integer) dataAttribute.getValue();
	}

	private Integer getViewValue()
	{
		return (Integer) binding.getViewValue();
	}

	private double getViewPercent()
	{
		return ((JProgressBar) binding.getJData()).getPercentComplete();
	}

}

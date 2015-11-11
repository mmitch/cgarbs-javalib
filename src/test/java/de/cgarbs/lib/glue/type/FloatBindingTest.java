package de.cgarbs.lib.glue.type;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.glue.GlueTestDataModel;

public class FloatBindingTest extends BaseBindingTest
{
	final NumberFormat nf = NumberFormat.getInstance();
	{
		nf.setMaximumFractionDigits(GlueTestDataModel.FLOAT_DECIMALS);
		nf.setMinimumFractionDigits(GlueTestDataModel.FLOAT_DECIMALS);
	}

	final Float MODEL_GIVEN_VALUE_1 = 33.22f;
	final Float MODEL_GIVEN_VALUE_2 = 4.3f;
	final Float MODEL_NULL_VALUE    = null;
	final Float MODEL_ROUND_VALUE   = -12.7777f;

	final String VIEW_GIVEN_VALUE_1 = nf.format(MODEL_GIVEN_VALUE_1);
	final String VIEW_GIVEN_VALUE_2 = nf.format(MODEL_GIVEN_VALUE_2);
	final String VIEW_NULL_VALUE    = "";
	final String VIEW_ROUND_VALUE   = nf.format(MODEL_ROUND_VALUE);

	@Before
	public void setUp() throws DataException, GlueException
	{
		setUp(GlueTestDataModel.FLOAT_ATTRIBUTE);
	}

	@Test
	public void checkConstructor()
	{
		assertThat(binding, is(not(nullValue())));
		assertThat(getViewValue(), is(equalTo(VIEW_NULL_VALUE)));
		assertThat(getAttributeValue(), is(equalTo(MODEL_NULL_VALUE)));
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
		assertThat(getAttributeValue(), is(not(equalTo(MODEL_GIVEN_VALUE_1))));
		binding.syncToModel();
		assertThat(getAttributeValue(), is(equalTo(MODEL_GIVEN_VALUE_1)));

		binding.setViewValue(VIEW_GIVEN_VALUE_2);
		assertThat(getAttributeValue(), is(not(equalTo(MODEL_GIVEN_VALUE_2))));
		binding.syncToModel();
		assertThat(getAttributeValue(), is(equalTo(MODEL_GIVEN_VALUE_2)));

		binding.setViewValue(VIEW_NULL_VALUE);
		assertThat(getAttributeValue(), is(not(equalTo(MODEL_NULL_VALUE))));
		binding.syncToModel();
		assertThat(getAttributeValue(), is(equalTo(MODEL_NULL_VALUE)));
	}

	@Test
	public void checkSyncToView() throws DataException
	{
		dataAttribute.setValue(MODEL_GIVEN_VALUE_1);
		assertThat(getViewValue(), is(not(equalTo(VIEW_GIVEN_VALUE_1))));
		binding.syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_GIVEN_VALUE_1)));

		dataAttribute.setValue(MODEL_GIVEN_VALUE_2);
		assertThat(getViewValue(), is(not(equalTo(VIEW_GIVEN_VALUE_2))));
		binding.syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_GIVEN_VALUE_2)));

		dataAttribute.setValue(MODEL_NULL_VALUE);
		assertThat(getViewValue(), is(not(equalTo(VIEW_NULL_VALUE))));
		binding.syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_NULL_VALUE)));
	}

	@Test
	public void checkRounding() throws DataException
	{
		final Double beforeRounding = Double.valueOf(MODEL_ROUND_VALUE.doubleValue());

		dataAttribute.setValue(beforeRounding);
		binding.syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_ROUND_VALUE)));
		binding.syncToModel();

		final Double afterRounding = Double.valueOf(getAttributeValue().doubleValue());

		assertThat(afterRounding, is(not(equalTo(beforeRounding))));
		assertThat(afterRounding, is(closeTo(beforeRounding, Math.pow(0.1, GlueTestDataModel.FLOAT_DECIMALS))));
	}

	private String getViewValue()
	{
		return (String) binding.getViewValue();
	}

	private Float getAttributeValue()
	{
		return (Float) dataAttribute.getValue();
	}

}

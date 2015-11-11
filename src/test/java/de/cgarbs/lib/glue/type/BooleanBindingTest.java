package de.cgarbs.lib.glue.type;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.glue.GlueTestDataModel;

public class BooleanBindingTest extends BaseBindingTest
{
	final Boolean MODEL_DEFAULT_VALUE = false;
	final Boolean MODEL_GIVEN_VALUE_1 = true;
	final Boolean MODEL_GIVEN_VALUE_2 = false;
	final Boolean MODEL_NULL_VALUE    = null;

	final Boolean VIEW_DEFAULT_VALUE = false;
	final Boolean VIEW_GIVEN_VALUE_1 = true;
	final Boolean VIEW_GIVEN_VALUE_2 = false;
	final Boolean VIEW_NULL_VALUE    = false;

	@Before
	public void setUp() throws DataException, GlueException
	{
		setUp(GlueTestDataModel.BOOLEAN_ATTRIBUTE);
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

		assertThat(binding.getJData(), is(instanceOf(JCheckBox.class)));
		final JCheckBox jData  = (JCheckBox) binding.getJData();
		assertThat(jData.isSelected(), is(VIEW_NULL_VALUE));
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
//		assertThat(getAttributeValue(), is(not(equalTo(MODEL_DEFAULT_VALUE)))); conflicts because a boolean only has two values
		binding.syncToModel();
		assertThat(getAttributeValue(), is(not(equalTo(MODEL_NULL_VALUE))));
//		assertThat(getAttributeValue(), is(equalTo(MODEL_DEFAULT_VALUE))); conflicts because a boolean only has two values
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
//		assertThat(getViewValue(), is(not(equalTo(VIEW_NULL_VALUE)))); conflicts because a boolean only has two values
//		assertThat(getViewValue(), is(not(equalTo(VIEW_DEFAULT_VALUE)))); conflicts because a boolean only has two values
		binding.syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_NULL_VALUE)));
	}

	private Boolean getAttributeValue()
	{
		return (Boolean) dataAttribute.getValue();
	}

	private Boolean getViewValue()
	{
		return (Boolean) binding.getViewValue();
	}

}

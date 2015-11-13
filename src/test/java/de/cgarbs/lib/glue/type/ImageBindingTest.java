package de.cgarbs.lib.glue.type;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JLabel;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.glue.GlueTestDataModel;

public class ImageBindingTest extends BaseBindingTest
{
	final Icon MODEL_GIVEN_VALUE_1 = null;
	final Icon MODEL_GIVEN_VALUE_2 = null;
	final Icon MODEL_NULL_VALUE    = null;

	final Icon VIEW_GIVEN_VALUE_1 = null;
	final Icon VIEW_GIVEN_VALUE_2 = null;
	final Icon VIEW_NULL_VALUE    = null;

	@Before
	public void setUp() throws DataException, GlueException
	{
		setUp(GlueTestDataModel.IMAGE_ATTRIBUTE, ImageBinding.class);
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

		assertThat(binding.getJData(), is(instanceOf(JLabel.class)));
		final JLabel jData  = (JLabel) binding.getJData();
		assertThat(jData.getIcon(), is(VIEW_NULL_VALUE));
	}

	@Test
	public void checkSyncToModel() throws DataException
	{
		binding.setViewValue(VIEW_GIVEN_VALUE_1);
		assertThat(getAttributeValue(), is(not(equalTo(MODEL_GIVEN_VALUE_1))));
		syncToModel();
		assertThat(getAttributeValue(), is(equalTo(MODEL_GIVEN_VALUE_1)));

		binding.setViewValue(VIEW_GIVEN_VALUE_2);
		assertThat(getAttributeValue(), is(not(equalTo(MODEL_GIVEN_VALUE_2))));
		syncToModel();
		assertThat(getAttributeValue(), is(equalTo(MODEL_GIVEN_VALUE_2)));

		binding.setViewValue(null);
		assertThat(getAttributeValue(), is(not(equalTo(MODEL_NULL_VALUE))));
		syncToModel();
		assertThat(getAttributeValue(), is(equalTo(MODEL_NULL_VALUE)));
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

	private Icon getViewValue()
	{
		return (Icon) binding.getViewValue();
	}

	private Icon getAttributeValue()
	{
		return (Icon) dataAttribute.getValue();
	}

}

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

import de.cgarbs.lib.data.DataModel;
import de.cgarbs.lib.data.type.IntAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.glue.GlueTestDataModel;
import de.cgarbs.lib.i18n.Resource;

public class IntBindingTest
{
	final String GIVEN_LABEL = "some label";

	final Integer MODEL_GIVEN_VALUE_1 = 33;
	final Integer MODEL_GIVEN_VALUE_2 = 4;
	final Integer MODEL_NULL_VALUE    = null;

	final String VIEW_GIVEN_VALUE_1 = "33";
	final String VIEW_GIVEN_VALUE_2 = "4";
	final String VIEW_NULL_VALUE    = "";

	DataModel dataModel;
	IntAttribute dataAttribute;
	Resource resource;
	IntBinding binding;

	@Before
	public void setUp() throws DataException
	{
		resource = new Resource();
		dataModel = new GlueTestDataModel(resource);

		dataAttribute = (IntAttribute) dataModel.getAttribute(GlueTestDataModel.INTEGER_ATTRIBUTE);

		binding = new IntBinding(
				dataAttribute,
				resource,
				GIVEN_LABEL
				);
	}

	@Test
	public void checkConstructor()
	{
		assertThat(binding, is(not(nullValue())));
		assertThat(getViewValue(), is(equalTo(VIEW_NULL_VALUE)));
		assertThat(getAttributeValue(), is(equalTo(MODEL_NULL_VALUE)));
		assertThat(binding.getTxtLabel(), is(equalTo(GIVEN_LABEL)));


		assertThat(binding.getJLabel(), is(instanceOf(JLabel.class)));
		final JLabel jLabel = (JLabel) binding.getJLabel();
		assertThat(jLabel.getText(), is(equalTo(GIVEN_LABEL)));

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

		binding.setViewValue(null);
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

		dataAttribute.setValue(null);
		assertThat(getViewValue(), is(not(equalTo(VIEW_NULL_VALUE))));
		binding.syncToView();
		assertThat(getViewValue(), is(equalTo(VIEW_NULL_VALUE)));
	}

	private String getViewValue()
	{
		return (String) binding.getViewValue();
	}

	private Integer getAttributeValue()
	{
		return (Integer) dataAttribute.getValue();
	}

}

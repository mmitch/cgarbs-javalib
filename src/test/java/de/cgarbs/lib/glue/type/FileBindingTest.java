/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue.type;

import static de.cgarbs.lib.FileUtils.createFileFrom;
import static de.cgarbs.lib.hamcrest.File.sameFileAs;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.exception.ValidationError;
import de.cgarbs.lib.glue.GlueTestDataModel;

public class FileBindingTest extends BaseBindingTest
{
	final File MODEL_GIVEN_VALUE_1 = createFileFrom("someFile.txt");
	final File MODEL_GIVEN_VALUE_2 = createFileFrom("some", "other", "file.txt");
	final File MODEL_NULL_VALUE    = null;

	final File VIEW_GIVEN_VALUE_1  = createFileFrom("someFile.txt");
	final File VIEW_GIVEN_VALUE_2  = createFileFrom("some", "other", "file.txt");
	final File VIEW_NULL_VALUE     = null;

	JTextField bindingTextField;
	FileBinding fileBinding;

	@Before
	public void setUp() throws DataException, GlueException
	{
		setUp(GlueTestDataModel.FILE_ATTRIBUTE);
		fileBinding = (FileBinding) binding;
		bindingTextField = fileBinding.jTextField;
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

		assertThat(binding.getJData(), is(instanceOf(JPanel.class)));
		assertThat(bindingTextField.getText(), is(""));
	}

	@Test
	public void checkSyncToModel() throws DataException
	{
		binding.setViewValue(VIEW_GIVEN_VALUE_1);
		assertThat(getModelValue(), is(not(sameFileAs(MODEL_GIVEN_VALUE_1))));
		syncToModel();
		assertThat(getModelValue(), is(sameFileAs(MODEL_GIVEN_VALUE_1)));

		binding.setViewValue(VIEW_GIVEN_VALUE_2);
		assertThat(getModelValue(), is(not(sameFileAs(MODEL_GIVEN_VALUE_2))));
		syncToModel();
		assertThat(getModelValue(), is(sameFileAs(MODEL_GIVEN_VALUE_2)));

		binding.setViewValue(null);
		assertThat(getModelValue(), is(not(sameFileAs(MODEL_NULL_VALUE))));
		syncToModel();
		assertThat(getModelValue(), is(sameFileAs(MODEL_NULL_VALUE)));
	}

	@Test
	public void checkSyncToView() throws DataException
	{
		dataAttribute.setValue(MODEL_GIVEN_VALUE_1);
		assertThat(getViewValue(), is(not(sameFileAs(VIEW_GIVEN_VALUE_1))));
		syncToView();
		assertThat(getViewValue(), is(sameFileAs(VIEW_GIVEN_VALUE_1)));

		dataAttribute.setValue(MODEL_GIVEN_VALUE_2);
		assertThat(getViewValue(), is(not(sameFileAs(VIEW_GIVEN_VALUE_2))));
		syncToView();
		assertThat(getViewValue(), is(sameFileAs(VIEW_GIVEN_VALUE_2)));

		dataAttribute.setValue(null);
		assertThat(getViewValue(), is(not(sameFileAs(VIEW_NULL_VALUE))));
		syncToView();
		assertThat(getViewValue(), is(sameFileAs(VIEW_NULL_VALUE)));
	}

	@Test
	public void checkValidationError() throws ValidationError
	{
		final Color originalBackground = bindingTextField.getBackground();

		fileBinding.setValidationError("FOO");
		assertThat(bindingTextField.getBackground(), is(not(equalTo(originalBackground))));
		assertThat(binding.getJData().getToolTipText(), is(equalTo("FOO")));
		final Color errorBackground = bindingTextField.getBackground();

		fileBinding.setValidationError(null);
		assertThat(bindingTextField.getBackground(), is(equalTo(originalBackground)));
		assertThat(binding.getJData().getToolTipText(), is(nullValue()));

		fileBinding.setViewValue(null);
		try {
			fileBinding.validate();
			fail("no exception thrown");
		}
		catch (ValidationError e)
		{
			assertThat(e.getError(), is(equalTo(ValidationError.ERROR.NULL_NOT_ALLOWED)));
		}
		assertThat(bindingTextField.getBackground(), is(equalTo(errorBackground)));

		fileBinding.setViewValue(new File("foo"));
		fileBinding.validate();
		assertThat(bindingTextField.getBackground(), is(equalTo(originalBackground)));

	}

	private File getModelValue()
	{
		return (File) dataAttribute.getValue();
	}

	private File getViewValue()
	{
		return (File) binding.getViewValue();
	}

}

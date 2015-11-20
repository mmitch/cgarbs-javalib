/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.ui.layout;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.glue.Binding;
import de.cgarbs.lib.glue.Glue;
import de.cgarbs.lib.i18n.Resource;
import de.cgarbs.lib.ui.UiTestDataModel;

public abstract class BaseLayoutTest
{
	final static int GROUP_COUNT = 3;
	final static String GROUP_1 = "Group 1";
	final static String GROUP_2 = "Group 2";
	final static String GROUP_3 = "Group 3";

	final static int BINDING_COUNT = 6;

	Container container;

	Binding binding1;
	Binding binding2;
	Binding binding3;
	Binding binding4;
	Binding binding5;
	Binding binding6;

	void setUp() throws DataException, GlueException
	{
		Resource resource = new Resource();

		UiTestDataModel dataModel = new UiTestDataModel(resource);

		Glue<UiTestDataModel> glue = new Glue<UiTestDataModel>(dataModel);

		binding1 = glue.addBinding(UiTestDataModel.STRING_1);
		binding2 = glue.addBinding(UiTestDataModel.STRING_2);
		binding3 = glue.addBinding(UiTestDataModel.STRING_3);
		binding4 = glue.addBinding(UiTestDataModel.STRING_4);
		binding5 = glue.addBinding(UiTestDataModel.STRING_5);
		binding6 = glue.addBinding(UiTestDataModel.STRING_6);

		binding1.setViewValue(theValue(UiTestDataModel.STRING_1));
		binding2.setViewValue(theValue(UiTestDataModel.STRING_2));
		binding3.setViewValue(theValue(UiTestDataModel.STRING_3));
		binding4.setViewValue(theValue(UiTestDataModel.STRING_4));
		binding5.setViewValue(theValue(UiTestDataModel.STRING_5));
		binding6.setViewValue(theValue(UiTestDataModel.STRING_6));
	}

	protected JViewport getViewportFromScrollpane(Component component)
	{
		assertThat(component, is(instanceOf(JScrollPane.class)));
		JScrollPane scrollpane = (JScrollPane) component;

		assertThat(scrollpane.getComponents().length, is(equalTo(3)));
		assertThat(scrollpane.getComponent(0), is(instanceOf(JViewport.class)));
		JViewport viewport = (JViewport) scrollpane.getComponent(0);
		return viewport;
	}

	protected static String theLabel(String attributeId)
	{
		return "LBL_" + UiTestDataModel.MODEL_NAME + "." + attributeId;
	}

	protected static String theValue(String attributeId)
	{
		return "Value=" + attributeId;
	}
}

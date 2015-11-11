package de.cgarbs.lib.ui.layout;

import java.awt.Container;

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
	}
}

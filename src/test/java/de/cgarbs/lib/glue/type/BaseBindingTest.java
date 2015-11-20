/*
 * Copyright 2015 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.glue.type;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.glue.Binding;
import de.cgarbs.lib.glue.Glue;
import de.cgarbs.lib.glue.GlueTestDataModel;
import de.cgarbs.lib.i18n.Resource;

public abstract class BaseBindingTest
{
	Glue<GlueTestDataModel> glue;
	Binding binding;
	DataAttribute dataAttribute;

	private void setUpInternal(String attributeId) throws DataException, GlueException
	{
		Resource resource = new Resource();
		GlueTestDataModel dataModel = new GlueTestDataModel(resource);
		dataAttribute = dataModel.getAttribute(attributeId);
		glue = new Glue<GlueTestDataModel>(dataModel);
	}

	void setUp(String attributeId) throws DataException, GlueException
	{
		setUpInternal(attributeId);
		binding = glue.addBinding(attributeId);
	}

	void setUp(String attributeId, Class<? extends Binding> bindingClass) throws DataException, GlueException
	{
		setUpInternal(attributeId);
		binding = glue.addBinding(attributeId, bindingClass);
	}

	String getLabel()
	{
		return "LBL_" + binding.getAttribute().getAttributeName();
	}

	void syncToModel() throws DataException
	{
		glue.syncToModel();
	}

	void syncToView()
	{
		glue.syncToView();
	}
}

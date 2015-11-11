package de.cgarbs.lib.glue.type;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.GlueException;
import de.cgarbs.lib.glue.Binding;
import de.cgarbs.lib.glue.Glue;
import de.cgarbs.lib.glue.GlueTestDataModel;
import de.cgarbs.lib.i18n.Resource;

public class BaseBindingTest
{
	Glue<GlueTestDataModel> glue;
	Binding binding;
	DataAttribute dataAttribute;

	void setUp(String attributeId) throws DataException, GlueException
	{
		Resource resource = new Resource();
		GlueTestDataModel dataModel = new GlueTestDataModel(resource);

		glue = new Glue<GlueTestDataModel>(dataModel);
		binding = glue.addBinding(attributeId);
		dataAttribute = binding.getAttribute();
	}

	String getLabel()
	{
		return "LBL_" + binding.getAttribute().getAttributeName();
	}
}

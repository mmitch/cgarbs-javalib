package de.cgarbs.lib.glue.type;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.data.type.IntegerAttribute;
import de.cgarbs.lib.glue.TextFieldBinding;
import de.cgarbs.lib.i18n.Resource;

/**
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.2.0
 */
public class IntegerBinding extends TextFieldBinding
{
	public IntegerBinding(DataAttribute attribute, Resource resource, String label)
	{
		super(attribute, resource, label);
	}

	@Override
	public void syncToView()
	{
		setViewValue(((IntegerAttribute)attribute).getFormattedValue());
	}
}

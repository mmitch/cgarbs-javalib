package de.cgarbs.lib.glue.type;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.data.type.IntAttribute;
import de.cgarbs.lib.data.type.IntegerAttribute;
import de.cgarbs.lib.i18n.Resource;

/**
 *
 * @author Christian Garbs <mitch@cgarbs.de>
 * @since 0.1.0
 * @deprecated use {@link IntegerBinding} instead
 *
 */
public class IntBinding extends IntegerBinding
{
	public IntBinding(DataAttribute attribute, Resource resource, String label)
	{
		super(attribute, resource, label);
	}

	@Override
	public void syncToView()
	{
		setViewValue(((IntAttribute)attribute).getFormattedValue());
	}
}

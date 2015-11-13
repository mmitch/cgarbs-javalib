package de.cgarbs.lib.hamcrest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class File
{
	public static Matcher<java.io.File> sameFileAs(final java.io.File file)
	{
		return new BaseMatcher<java.io.File>() {

			@Override
			public void describeTo(Description description)
			{
				description.appendText("absolute path is ").appendValue(getAbsolutePathSafe(file));
			}

			@Override
			public void describeMismatch(final Object item, Description mismatchDescription)
			{
				mismatchDescription.appendText(" was ").appendValue(getAbsolutePathSafe((java.io.File) item));
			}

			@Override
			public boolean matches(Object item)
			{
				if (item == null)
				{
					return item == file;
				}
				return ((java.io.File) item).getAbsolutePath().equals(getAbsolutePathSafe(file));
			}

			private String getAbsolutePathSafe(java.io.File file)
			{
				if (file == null)
				{
					return null;
				}
				return file.getAbsolutePath();
			}
		};
	}
}

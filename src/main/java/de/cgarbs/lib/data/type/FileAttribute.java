/*
 * Copyright 2014 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package de.cgarbs.lib.data.type;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.cgarbs.lib.data.DataAttribute;
import de.cgarbs.lib.exception.DataException;
import de.cgarbs.lib.exception.ValidationError;

public class FileAttribute extends DataAttribute
{
	private File value;

	private final boolean mustExist;
	private final boolean mustRead;
	private final boolean mustWrite;
	private final FileFilter[] fileFilters;

	// Builder pattern start
	public static class Builder extends DataAttribute.Builder<Builder>
	{
		public FileAttribute build()
		{
			return new FileAttribute(this);
		}

		@Override
		protected Builder getThis()
		{
			return this;
		}

		public Builder setMustExist(boolean mustExist)
		{
			this.mustExist = mustExist;
			return this;
		}

		public Builder setMustRead(boolean mustRead)
		{
			this.mustRead = mustRead;
			return this;
		}

		public Builder setMustWrite(boolean mustWrite)
		{
			this.mustWrite = mustWrite;
			return this;
		}

		public Builder addFileFilter(final String name, final String... extensions)
		{
			this.filefilters.add(
					new FileNameExtensionFilter(name, extensions)
					);
			return this;
		}

		private boolean mustExist = false;
		private boolean mustRead  = false;
		private boolean mustWrite = false;
		private final List<FileFilter> filefilters = new ArrayList<FileFilter>();
	}

	public static Builder builder()
	{
		return new Builder();
	}

	private FileAttribute(final Builder builder)
	{
		super(builder);
		this.mustExist   = builder.mustExist;
		this.mustRead    = builder.mustRead;
		this.mustWrite   = builder.mustWrite;
		this.fileFilters = builder.filefilters.toArray(new FileFilter[0]);
	}
	// Builder pattern end

	@Override
	public Object getValue()
	{
		return value;
	}

	public void validate(final Object value) throws ValidationError
	{
		final File file = (File) convertType(value);

		super.validate(file);

		if (file != null)
		{
			boolean exists    = file.exists();
			boolean writable  = file.canWrite();
			boolean readable  = file.canRead();
			final String name = file.getAbsolutePath();

			if (mustExist && ! exists)
			{
				throw new ValidationError(
						this,
						"file does not exist: " + name,
						ValidationError.ERROR.FILE_NOT_EXISTING,
						name
						);
			}

			if (mustRead && ! readable)
			{
				throw new ValidationError(
						this,
						"file not readable: " + name,
						ValidationError.ERROR.FILE_NOT_READABLE,
						name
						);
			}

			if (mustWrite && exists && ! writable)
			{
				throw new ValidationError(
						this,
						"file not writable: " + name,
						ValidationError.ERROR.FILE_NOT_WRITABLE,
						name
						);
			}
		}
	}

	public FileFilter[] getFileFilters()
	{
		return fileFilters;
	}

	@Override
	public boolean isDirty()
	{
		boolean dirty = super.isDirty();
		// consider relative and absolute paths equal, if they match
		if (dirty && cleanValue != null && value != null)
		{
			dirty = ! ((File)cleanValue).getAbsoluteFile().equals(value.getAbsoluteFile());
		}
		return dirty;
	}

	@Override
	protected void setValueInternal(final Object newValue) throws DataException
	{
		value = (File) newValue;
	}

	@Override
	protected Object convertType(final Object newValue) throws ValidationError
	{
		if (newValue == null)
		{
			return null;
		}
		else if (newValue instanceof File)
		{
			return new File(((File) newValue).getPath());
		}
		else if (newValue instanceof String)
		{
			return new File((String) newValue);
		}
		else if (newValue instanceof URI)
		{
			return new File((URI) newValue);
		}
		else
		{
			throw new ValidationError(
					this,
					"wrong type: " + newValue.getClass() + " != " + File.class,
					ValidationError.ERROR.NUMBER_FORMAT // FIXME this is not number format error!
					);
		}
	}

	public boolean getMustWrite()
	{
		return mustWrite;
	}

	public boolean getMustRead()
	{
		return mustRead;
	}

	public boolean getMustExist()
	{
		return mustExist;
	}
}

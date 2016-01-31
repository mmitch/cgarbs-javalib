cgarbs-javalib
==============

[![Build Status](https://travis-ci.org/mmitch/cgarbs-javalib.svg?branch=master)](https://travis-ci.org/mmitch/cgarbs-javalib)
[![Coverage Status](https://codecov.io/github/mmitch/cgarbs-javalib/coverage.svg?branch=master)](https://codecov.io/github/mmitch/cgarbs-javalib?branch=master)
[![GPL 3+](https://img.shields.io/badge/license-GPL%203%2B-blue.svg)](http://www.gnu.org/licenses/gpl-3.0-standalone.html)


about
-----

My own Java framework, mostly a Model-View-Binding for Swing.

The project homepage is at <https://github.com/mmitch/cgarbs-javalib>


features
--------

Current features are:

* define data models containing fields of different types (text,
  numerals etc.)
* define simple validations for the fields (minimum/maximum text
  lengths, minimum/maximum values, null/notnull)
* generate Swing widgets that are bound to the data model fields
  (mostly TextFields, but there is more: a color picker, CheckBoxes)
* grouping of widgets
* different layouts for the widgets (single/dual columns, tabbed, ...)
* move data between the GUI and the model via method call
* validate the model showing errors directly in the GUI widgets
  (eg. "text too long")
* store/load the whole model to/from a file
* i18n/l10n support for labels and error messages


dependencies
------------

- for using the framework in your projects:
  - at least Java 6

- for building:
  - Gradle build environment
  - bash and perl for extended build tools (both optional)


examples
--------

Take a look at my [knittr][1] project for a real-world example of how
to use this framework.

[1]: <https://github.com/mmitch/knittr>


building with Gradle
--------------------

Building via ``build.gradle`` should be straightforward.

There are some additional build targets available:

* ``version`` shows the current version number that would be used when
  invoking the ``publish`` target

* ``fixit`` runs a bash script to fix line breaks and indentation on
  empty lines.

* ``checkl10n`` checks the .property files for missing translations

* ``publishDropbox`` copies the generated jar my Dropbox to sync it to
  my wife's computer - this propably is of no use to you :-)


copyright
---------
cgarbs-javalib - my own Java framework, mostly a Model-View-Binding for Swing  
Copyright (C) 2015  Christian Garbs <mitch@cgarbs.de>  
Licensed under GNU GPL v3 (or later)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

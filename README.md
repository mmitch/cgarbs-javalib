cgarbs-javalib
==============
[![Build Status](https://travis-ci.org/mmitch/cgarbs-javalib.svg?branch=master)](https://travis-ci.org/mmitch/cgarbs-javali)
[![Coverage Status](https://codecov.io/github/mmitch/cgarbs-javalib/coverage.svg?branch=master)](https://codecov.io/github/mmitch/cgarbs-javalib?branch=master)
[![GPL 3+](https://img.shields.io/github/license/mmitch/cgarbs-javalib.svg)](http://www.gnu.org/licenses/gpl-3.0-standalone.html)

my own Java framework, mostly a Model-View-Binding for Swing

features
--------

* define data models containing fields of different types (text, numerals etc.)
* define simple validations for the fields (minimum/maximum text lengths, minimum/maximum values, null/notnull)
* generate Swing widgets that are bound to the data model fields (mostly TextFields, but there is more like a Color picker)
* grouping of widgets
* different layouts for the widgets (single/dual columns, tabbed, ...)
* move data between the GUI and the model via method call
* validate the model showing errors directly in the GUI widgets (eg. "text too long")
* store/load the whole model to/from a file
* i18n/l10n support for labels and error messages

copyright
---------
cgarbs-javalib - my own Java framework, mostly a Model-View-Binding for Swing  
Copyright (C) 2015  Christian Garbs <<mitch@cgarbs.de>>  
Licensed under GNU GPL v3 (or later)

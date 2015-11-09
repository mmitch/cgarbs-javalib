#!/bin/bash

echo
echo checking cgarbs-javalib:
./check-l10n.pl ../src/de/cgarbs/lib/i18n/resource/*

echo
echo checking Guesser demo:
./check-l10n.pl ../src-demos/guesser/Guesser*.properties

echo

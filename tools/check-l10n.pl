#!/usr/bin/perl -w
use strict;

# check for missing localizations
#
# give all relevant files as parameter-globs

my @files;
foreach my $glob (@ARGV) {
    push @files, glob $glob;
}

my $allkeys = {};
my $filekeys = {};

foreach my $file (@files)
{
    open FILE, '<', $file or die "can't open `$file': $!";
    while (my $line = <FILE>)
    {
	if ($line =~ /^\s*([^#]+?)=/)
	{
	    $allkeys->{$1}++;
	    $filekeys->{$file}->{$1}++;
	}
    }
    close FILE or die "can't close file `$file': $!";
}

my @keys = keys %{$allkeys};
printf "total keys: %d\n", scalar @keys;

print "missing keys:\n";
my $missing = 0;
foreach my $key (@keys)
{
    my @files;
    foreach my $file (@files)
    {
	if (not exists $filekeys->{$file}->{$key})
	{
	    push @files, $file;
	}
    }
    if (@files)
    {
	printf "%s = { %s }\n", $key, join(' ', @files);
	$missing++;
    }
}

exit ($missing > 0);

#!/bin/bash

PATH_TO_CODE_BASE=`pwd`

MAIN_CLASS="ar.edu.itba.pod.tpe1.client.emergencyCare.EmergencyCareClient"

java $* -cp 'lib/jars/*'  $MAIN_CLASS

#!/bin/bash
# You can use this to build the thing without Maven
# -- Lubomir Rintel <lubo.rintel@gooddata.com>

if [ -z "$JIRA_HOME" ]
then
	echo Set JIRA_HOME, pretty please
	exit 1
fi

find -name '*.class' -delete
find -name '*.java' -print0 |xargs -0 javac -source 1.5 -target 1.5 -classpath \
	$(find $JIRA_HOME \( -name '*.jar' -o -name classes \) -print0 |sed 's/\x00/:/g')
jar -cf KrbCrowdAuthenticator.jar -C src/main/java com/gooddata

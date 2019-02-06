#!/bin/bash

# This script allows full automatic launch (script used for testing delivery + SonarQube analysis).

magentafat='\033[1;35m';
neutral='\033[0;m';

# Compilation by Maven (with the plugin JaCoCo, to generate code coverage reports)
compilation()
{
	echo -e "\n\n${magentafat}COMPILATION...${neutral}";
	mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package;
	sleep 1;
}

# Analysis by Maven (with the plugin (for) SonarQube...)
analysis()
{
	echo -e "\n\n${magentafat}ANALYSIS...${neutral}";
	mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.5.0.1254:sonar;
	sleep 1;
}

# Execution by Maven...
execution()
{
	echo -e "\n\n${magentafat}EXECUTION...${neutral}";
	mvn exec:java;
	sleep 1;
}

if [[ -n $* ]]; then
	if [[ $1 =~ ^('--skip'|'-s')$ ]]; then
		if [[ -n $2 ]]; then
			if [[ $2 =~ ^('C'|'A'|'E')$ ]]; then
				if [[ $3 =~ ^('C'|'A'|'E'|'')$ ]]; then
					if [[ -n $3 ]] && [[ -n $4 ]]; then
						echo -e "\n\n[${magentafat}WARNING${neutral}] The 3rd value not taken into account.";
					fi
					if  [[ $2 != 'C' ]] && [[ $3 != 'C' ]]; then
						compilation;
					fi
					if  [[ $2 != 'A' ]] && [[ $3 != 'A' ]]; then
						analysis;
					fi
					if  [[ $2 != 'E' ]] && [[ $3 != 'E' ]]; then
						execution;
					fi
				else
					echo "Unexpected second value for the 'skip' option; enter 'C' (for skiping the compilation), 'A' (for skiping the analysis) or 'E' (for skiping the execution).";
				fi
			else
				echo "Unexpected value for the 'skip' option; enter 'C' (for skiping the compilation), 'A' (for skiping the analysis) or 'E' (for skiping the execution).";
			fi

		else
			echo "No value for the 'skip' option; enter 'C' (for skiping the compilation), 'A' (for skiping the analysis) or 'E' (for skiping the execution).";
		fi
	else
		if [[ $1 =~ ^('--help'|'-h')$ ]]; then
			echo -e "For skiping one or two operations, enter (as an argument) '--skip' (or '-s') followed by at least one letter (when you would to skip several operations, separate the letters by space; for example:\n${magentafat}'--skip C E' has the effect of skiping the compilation and the execution (in other words, only the analysis is launched)${neutral}.";
		else
			echo -e "Unknown '$*'; the allowed options are: '--skip' and '--help'.";
		fi
	fi
else
	compilation;
 	analysis;
	execution;
fi

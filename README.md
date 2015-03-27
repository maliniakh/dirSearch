### Directory Search
Simple showcase application, written according to TDD methodology. The appliction is run from CLI and searches for files that fullfil given criteria. 
It comes with _search.sh_ shell script file. 

The application makes use of the following tools / libraries:
* Filesystem API (traversing directories, matching files) introduced in Java 7 
* Maven
* JUnit
* Mockito

#### Running

If there is a need to: <br/>
_chmod +x search.sh_

Type from CLI:<br/>
_./search.sh_ to display usage.<br/><br/>
The script takes care of building, i.e. if there is no jar file, _mvn install_ command is run.

#### Requirements

Java 7 or newer<br/>
Maven, version 2.x.x should be sufficient.

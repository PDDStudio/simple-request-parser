#simple-request-parser
Simple CLI tool to parse icon requests made from the iconmanager-client android library.

**Note: This is just a temporary repository to provide this tool for the Pinbadge-Team, this repository will be merged with the iconmanager once it's done.**

##Dependencies
- Maven
- Java Runtime & Compiler 1.7 / 1.8

##Building the tool
1. grep a local copy of this repo
2. switch into the project's root dir (where the pom.xml is located)
3. start building by running `mvn package`
4. if the build was success, you can find a file called `simple-request-parser-1.0.jar` in the `target` output dir.

##Using
1. extract the request zipfile somewhere on your system (but remember the place, you'll need it again)
2. For displaying all informations included in the request you can call `java -jar simple-request-parser-1.0.jar -p /absolute/location/to/extracted/zipfolder`
3. For a filtered overview you can call `java -jar simple-request-parser-1.0.jar -p /absolute/location/to/extracted/zipfolder --advanced`

##
*For other things you might want to check the help by calling `java -jar simple-request-parser-1.0.jar --help`*


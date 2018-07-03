# Building Application

## Installing in Maven

Since the java sdk is not hosted (yet), it will have to be added as a local dependency.
Ensure that you have the the `namara-<version>.jar` file installed somewhere.

Then install in project with

```bash
$ mvn install:install-file -DlocalRepositoryPath=[NAME OF REPO FOLDER] \
-DcreateChecksum=true -Dpackaging=jar \
-Dfile=[ABSOLUTE PATH TO JAR FILE] \
-DgroupId=com.thinkdata -DartifactId=namara \
-Dversion=[version]
```

Then ensure that you have added the generated folder as a repository in your `pom.xml` file, such as in this example.

Add the jar file as a dependency in `pom.xml`

```xml
<dependency>
    <groupId>com.thinkdata</groupId>
    <artifactId>namara</artifactId>
    <version>[VERSION]</version>
</dependency>
```

# Development

Please consult [the javadoc](https://thinkdata-works.github.io/namara-java-sdk/) for full usage.
Tiny License Framework for Java
=====================
It's an open source (Apache license) tiny license framework in Java, with zero dependencies and a quick learning curve.

How to use
-------------------
```java
LicenseManager licenseManager = LicenseManager.getInstance();
License license = licenseManager.getLicense();
licenseManager.isValidLicense(license);
```

In `private.key` file exists the private key used to create the license.  
In `public.key` file exists the public key used to validate the license.  
The public key (public.key) is delivered with your application jar.  

To create a new license, you must create a template file (`template.dat`) that contains all license's components
and to run `LicenseTool`.  
If the private key or the public key doesn't exists then licensius will create these files for you. The license will
be a `license.dat` file.  

In `example` folder you have an example for license template (input) and and its associated license (output).  

The content for `template.dat` can be (see example folder):
```
expirationDate=9/12/08
companyName=HomeOffice
emailAddress=office@home.ro
```

In this case the content for `license.dat` (the license) is:
```
#License file
#Fri Feb 13 14:57:14 EET 2015
expirationDate=9/12/08
companyName=HomeOffice
emailAddress=office@home.ro
signature=MCwCFBEzxwxCPSrWwYj1lsfyDDMKEhMmAhRtDi2klZeDojMK3HBF7xaC3OBj9A\=\=
```
Using Maven
-------------------
In your pom.xml you must define the dependencies to Licensius artifacts with:

```xml
<dependency>
    <groupId>ro.fortsoft.licensius</groupId>
    <artifactId>licensius</artifactId>
    <version>${licensius.version}</version>
</dependency>    
```

where ${licensius.version} is the last licensius version.

You may want to check for the latest released version using [Maven Search](http://search.maven.org/#search%7Cga%7C1%7Clicensius)

How to build
-------------------
Requirements: 
- [Git](http://git-scm.com/) 
- JDK 7 (test with `java -version`)
- [Apache Maven 3](http://maven.apache.org/) (test with `mvn -version`)

Steps:
- create a local clone of this repository (with `git clone https://github.com/decebals/licensius.git`)
- go to project's folder (with `cd licensius`) 
- build the artifacts (with `mvn clean package` or `mvn clean install`)

After above steps a folder _licensius/target_ is created and all goodies are in that folder.

Versioning
------------
Licensius will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

License
--------------
Copyright 2015 Decebal Suiu

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.


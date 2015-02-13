Tiny License Framework for Java
=====================

In `private.key` file exists the private key used to create the license.
In `public.key` file exists the public key used to validate the license.
The public key (public.key) is delivered with licensius.jar.
To create a new license, you must create a template file (template.dat) that contains all license's components
and to run `LicenseTool`.

How to use
-------------------
```java
LicenseManager licenseManager = LicenseManager.getInstance();
License license = licenseManager.getLicense();
licenseManager.isValidLicense(license);
```

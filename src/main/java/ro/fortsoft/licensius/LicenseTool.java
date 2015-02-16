/*
 * Copyright (C) 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.fortsoft.licensius;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Decebal Suiu
 */
public class LicenseTool {

    public static final String TEMPLATE_FILE = "template.dat";
    public static final String PRIVATE_KEY_FILE = "private.key";

    public static void main(String[] args) {
        try {
            new LicenseTool().createLicense();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateLicense(Properties features, OutputStream output, String privateKeyFile) throws LicenseException {
        LicenseGenerator.generateLicense(features, output, privateKeyFile);
    }

    public void createLicense() throws Exception {
        if (!existsKeyFiles()) {
            generateKeys();
        }

        Properties properties = new OrderedProperties();
        properties.load(new FileInputStream(TEMPLATE_FILE));

        LicenseGenerator.generateLicense(properties, "private.key");

        System.out.println("License generated in '" + LicenseManager.LICENSE_FILE + "' file.");
    }

    private boolean existsKeyFiles() {
        return existsPrivateKeyFile() && existsPublicKeyFile();
    }

    private boolean existsPrivateKeyFile() {
        File privateKeyFile = new File(PRIVATE_KEY_FILE);
        boolean exists = privateKeyFile.exists() && privateKeyFile.isFile();

        return exists;
    }

    private boolean existsPublicKeyFile() {
        File publicKeyFile = new File(LicenseManager.PUBLIC_KEY_FILE);
        boolean exists = publicKeyFile.exists() && publicKeyFile.isFile();

        return exists;
    }

    private void generateKeys() throws LicenseException {
        KeyGenerator.createKeys(LicenseManager.PUBLIC_KEY_FILE, PRIVATE_KEY_FILE);
    }

}

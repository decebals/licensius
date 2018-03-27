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

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

/**
 * @author Decebal Suiu
 */
public class LicenseManager {

    public static String LICENSE_FILE = "license.dat";
    public static String PUBLIC_KEY_FILE = "public.key";
    public static final String SIGNATURE = "signature";

    private static LicenseManager licenseManager = new LicenseManager();

    public static LicenseManager getInstance() {
        return licenseManager;
    }

    public boolean isValidLicense(License license) throws LicenseNotFoundException, LicenseException {
        return !license.isExpired();
    }

    public License getLicense() throws LicenseNotFoundException, LicenseException {
        if (!new File(LICENSE_FILE).exists()) {
            throw new LicenseNotFoundException();
        }

        License license;
        try {
            license = loadLicense();
        } catch (Exception e) {
            throw new LicenseException(e);
        }

        return license;
    }

    private License loadLicense() throws Exception {
        Properties features = PropertiesUtils.loadProperties(LICENSE_FILE);
        if (!features.containsKey(SIGNATURE)) {
            throw new LicenseException("Missing signature");
        }

        String signature = (String) features.remove(SIGNATURE);
        String encoded = features.toString();

        PublicKey publicKey = readPublicKey(PUBLIC_KEY_FILE);

        if (!verify(encoded.getBytes(), signature, publicKey)) {
            throw new LicenseException();
        }

        return new License(features);
    }

    private PublicKey readPublicKey(String uri) throws Exception {
        byte[] bytes;
        File file = new File(uri);
        if (file.exists() && file.isFile()) {
            bytes = IoUtils.getBytesFromFile(uri);
        } else {
            bytes = IoUtils.getBytesFromResource(uri);
        }

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");

        return keyFactory.generatePublic(keySpec);
    }

    private boolean verify(byte[] message, String signature, PublicKey publicKey) throws Exception {
        Signature dsa = Signature.getInstance("SHA/DSA");
        dsa.initVerify(publicKey);
        dsa.update(message);

        // TODO use java.util.Base64 from java 8
        byte[] decoded = DatatypeConverter.parseBase64Binary(signature);

        return dsa.verify(decoded);
    }

}

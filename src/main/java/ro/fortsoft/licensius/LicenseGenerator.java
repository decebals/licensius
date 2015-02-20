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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Properties;

/**
 * @author Decebal Suiu
 */
public class LicenseGenerator {

    public static String generateLicense(Properties features, OutputStream output, String privateKeyFile) throws LicenseException {
        try {
            PrivateKey privateKey = readPrivateKey(privateKeyFile);

            String encoded = features.toString();
            String signature = sign(encoded.getBytes(), privateKey);

            Properties properties = new OrderedProperties();
            properties.putAll(features);
            properties.setProperty(LicenseManager.SIGNATURE, signature);
            properties.store(output, "License file");

            return signature;
        } catch (Exception e) {
            throw new LicenseException(e);
        }
    }

    public static String generateLicense(Properties features, String privateKeyFile) throws LicenseException {
        OutputStream output = null;
        try {
            output = new FileOutputStream(LicenseManager.LICENSE_FILE);
            String license = generateLicense(features, output, privateKeyFile);

            return license;
        } catch (FileNotFoundException e) {
            throw new LicenseException(e);
        } finally {
            IoUtils.close(output);
        }
    }

    private static PrivateKey readPrivateKey(String uri) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(IoUtils.getBytesFromFile(uri));
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);

        return key;
    }

    private static String sign(byte[] message, PrivateKey privateKey) throws Exception {
        Signature dsa = Signature.getInstance("SHA/DSA");
        dsa.initSign(privateKey);
        dsa.update(message);

        byte[] signature = dsa.sign();

        // TODO use java.util.Base64 from java 8
        String encoded = DatatypeConverter.printBase64Binary(signature);

        return encoded;
    }

}

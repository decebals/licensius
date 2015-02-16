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

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

/**
 * @author Decebal Suiu
 */
class KeyGenerator {

    public static void createKeys(String publicUri, String privateUri) throws LicenseException {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            keyGen.initialize(1024, new SecureRandom());
            KeyPair keyPair = keyGen.generateKeyPair();

            IoUtils.writeFile(publicUri, keyPair.getPublic().getEncoded());
            IoUtils.writeFile(privateUri, keyPair.getPrivate().getEncoded());
        } catch (Exception e) {
            throw new LicenseException(e);
        }
    }

}

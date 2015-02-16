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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Decebal Suiu
 */
public class PropertiesUtils {

    public static Properties loadProperties(String propertiesFile) throws IOException {
        InputStream input = null;
        try {
            input = new FileInputStream(propertiesFile);
            Properties properties = new OrderedProperties();
            properties.load(input);

            return properties;
        } finally {
            IoUtils.close(input);
        }
    }

}

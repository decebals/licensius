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

import java.util.Calendar;
import java.util.Date;

/**
 * @author Decebal Suiu
 */
class DateUtils {

    /**
     * Get number of days between two dates
     *
     * @param first  first date
     * @param second second date
     * @return number of days if first date less than second date,
     * 0 if first date is bigger than second date,
     * 1 if dates are the same
     */
    public static int getNumberOfDays(Date first, Date second) {
        int compare = first.compareTo(second);
        if (compare > 0) {
            return 0;
        } else if (compare == 0) {
            return 1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(first);

        int firstDay = calendar.get(Calendar.DAY_OF_YEAR);
        int firstYear = calendar.get(Calendar.YEAR);
        int firstDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);

        calendar.setTime(second);

        int secondDay = calendar.get(Calendar.DAY_OF_YEAR);
        int secondYear = calendar.get(Calendar.YEAR);

        int result = 0;

        // if dates in the same year
        if (firstYear == secondYear) {
            result = secondDay - firstDay + 1;
        } else {
            // days from the first year
            result += firstDays - firstDay + 1;

            // add days from all years between the two dates years
            for (int i = firstYear + 1; i < secondYear; i++) {
                calendar.set(i, 0, 0);
                result += calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            }

            // days from last year
            result += secondDay;
        }

        return result;
    }

}

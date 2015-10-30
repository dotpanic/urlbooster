/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.u8t.urlbooster.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTags {

    public static enum Charset { LOWERCASE_ONLY, UPPERCASE_ONLY, UPPER_LOWER, UPPER_DIGIT, LOWER_DIGIT, UPPER_LOWER_DIGIT, DIGIT_ONLY };

    private static final char[] LOWERCASE_TAB = new char [] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                                                             'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                                                             'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] UPPERCASE_TAB = new char [] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                                                             'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                                                             'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] DIGITS_TAB = new char [] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String getString(final int length, final Charset cs) {
        String builtString = "";
        char[] fullCharacterSet = new char[] {};

        switch (cs) {
        case LOWERCASE_ONLY:
            fullCharacterSet = LOWERCASE_TAB;
            break;
        case UPPERCASE_ONLY:
            fullCharacterSet = UPPERCASE_TAB;
            break;
        case DIGIT_ONLY:
            fullCharacterSet = DIGITS_TAB;
            break;
        case UPPER_LOWER:
            fullCharacterSet = concat(LOWERCASE_TAB, UPPERCASE_TAB);
            break;
        case UPPER_DIGIT:
            fullCharacterSet = concat(UPPERCASE_TAB, DIGITS_TAB);
            break;
        case LOWER_DIGIT:
            fullCharacterSet = concat(LOWERCASE_TAB, DIGITS_TAB);
            break;
        case UPPER_LOWER_DIGIT:
            fullCharacterSet = concat(concat(LOWERCASE_TAB, UPPERCASE_TAB), DIGITS_TAB);
            break;
        }

        for (int i = 0; i < length; i++) {
            builtString += fullCharacterSet[(int) (Math.random() * fullCharacterSet.length)];
        }

        return builtString;
    }

    private static char[] concat(final char[] firstTab, final char[] secondTab) {
        char[] builtTab = new char[firstTab.length + secondTab.length];
        System.arraycopy(firstTab, 0, builtTab, 0, firstTab.length);
        System.arraycopy(secondTab, 0, builtTab, firstTab.length, secondTab.length);

        return builtTab;
    }

    public static String replaceRandomStringGenerationTag(final String stringToProcess, final String regex, final Charset charset) {

        String tempString = stringToProcess;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tempString);

        while (matcher.find()) {
            String subStringToProcess = matcher.group();
            int lenghtIndex = subStringToProcess.indexOf(':') + 1;
            String length = subStringToProcess.substring(lenghtIndex, subStringToProcess.indexOf('>', lenghtIndex));
            tempString = matcher.replaceFirst(getString(new Integer(length).intValue(), charset));
            matcher.reset(tempString);
        }
        return tempString;
    }
    
    public static String replaceDateTimeTag(final String stringToProcess, final String tag, final String dateTimeFormat) {

        String tempString = stringToProcess;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
        String formattedDate = formatter.format(date);
        tempString = tempString.replaceAll(tag, formattedDate);
        
        return tempString;
    }
    
    public static String replaceDateTimeTags(final String stringToProcess) {

        String tempString = stringToProcess;

        tempString = replaceDateTimeTag(tempString, "<yy>", "yy");
        tempString = replaceDateTimeTag(tempString, "<yyyy>", "yyyy");
        tempString = replaceDateTimeTag(tempString, "<year>", "yyyy");
        
        tempString = replaceDateTimeTag(tempString, "<mm>", "MM");
        tempString = replaceDateTimeTag(tempString, "<month>", "mm");
        
        tempString = replaceDateTimeTag(tempString, "<dd>", "dd");
        tempString = replaceDateTimeTag(tempString, "<day>", "dd");
        
        tempString = replaceDateTimeTag(tempString, "<hh>", "hh");
        tempString = replaceDateTimeTag(tempString, "<hour>", "hh");
        
        tempString = replaceDateTimeTag(tempString, "<mm>", "mm");
        tempString = replaceDateTimeTag(tempString, "<minutes>", "mm");
        
        tempString = replaceDateTimeTag(tempString, "<ss>", "ss");
        tempString = replaceDateTimeTag(tempString, "<seconds>", "ss");
        
        tempString = replaceDateTimeTag(tempString, "<ts>", "yyyyMMdd");
        tempString = replaceDateTimeTag(tempString, "<timestamp>", "yyyyMMdd");
        
        tempString = replaceDateTimeTag(tempString, "<fts>", "yyyyMMdd");
        tempString = replaceDateTimeTag(tempString, "<fulltimestamp>", "yyyyMMddhhmmss");
        
        return tempString;
    }

    public static String replaceRandomStringGenerationTag(final String stringToProcess, final String regex, final String[] userSet) {

        String tempString = stringToProcess;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tempString);

        while (matcher.find()) {
            String subStringToProcess = matcher.group();
            int lenghtIndex = subStringToProcess.indexOf(':') + 1;
            String length = subStringToProcess.substring(lenghtIndex, subStringToProcess.indexOf('>', lenghtIndex));

            String builtString = "";
            for (int i = 0; i < new Integer(length).intValue(); i++) {
                builtString += userSet[(int) (Math.random() * userSet.length)];
            }

            tempString = matcher.replaceFirst(builtString);
            matcher.reset(tempString);
        }
        return tempString;
    }
    
    public static String substituteTags(final String stringToProcess) {
        
        String tempString = stringToProcess;
        
        // Date/Time Tags
        tempString = replaceDateTimeTags(stringToProcess);
        
        // String generation Tags
        tempString = replaceRandomStringGenerationTag(tempString, "<rsld:\\d*>", StringTags.Charset.LOWER_DIGIT);
        tempString = replaceRandomStringGenerationTag(tempString, "<randomStringLowerDigit:\\d*>", StringTags.Charset.LOWER_DIGIT);

        tempString = replaceRandomStringGenerationTag(tempString, "<rslo:\\d*>", StringTags.Charset.LOWERCASE_ONLY);
        tempString = replaceRandomStringGenerationTag(tempString, "<randomStringLowerOnly:\\d*>", StringTags.Charset.LOWERCASE_ONLY);

        tempString = replaceRandomStringGenerationTag(tempString, "<rsud:\\d*>", StringTags.Charset.UPPER_DIGIT);
        tempString = replaceRandomStringGenerationTag(tempString, "<randomStringUpperDigit:\\d*>", StringTags.Charset.UPPER_DIGIT);

        tempString = replaceRandomStringGenerationTag(tempString, "<rsul:\\d*>", StringTags.Charset.UPPER_LOWER);
        tempString = replaceRandomStringGenerationTag(tempString, "<randomStringUpperLower:\\d*>", StringTags.Charset.UPPER_LOWER);

        tempString = replaceRandomStringGenerationTag(tempString, "<rsuld:\\d*>", StringTags.Charset.UPPER_LOWER_DIGIT);
        tempString = replaceRandomStringGenerationTag(tempString, "<randomStringUpperLowerDigit:\\d*>", StringTags.Charset.UPPER_LOWER_DIGIT);

        tempString = replaceRandomStringGenerationTag(tempString, "<rsuo:\\d*>", StringTags.Charset.UPPERCASE_ONLY);
        tempString = replaceRandomStringGenerationTag(tempString, "<randomStringUpperOnly:\\d*>", StringTags.Charset.UPPERCASE_ONLY);
        
        tempString = replaceRandomStringGenerationTag(tempString, "<rsdo:\\d*>", StringTags.Charset.UPPERCASE_ONLY);
        tempString = replaceRandomStringGenerationTag(tempString, "<randomStringDigitsOnly:\\d*>", StringTags.Charset.UPPERCASE_ONLY);

        // User defined set
        Pattern pattern = Pattern.compile("<rs(\\(.*?\\))+:\\d*>");
        Matcher matcher = pattern.matcher(tempString);

        // Cette chaine contiendra le tag une fois formaté correctement
        String subTagProcessed = "";

        // on itère sur les tags de type <rs(a)(b):n>
        while (matcher.find()) {

            // lorsque l'on trouve un tel tag, on extrait les éléments utilisateurs de type (a)
            Pattern subPattern = Pattern.compile("\\(.*?\\)");
            Matcher subMatcher = subPattern.matcher(matcher.group());

            String[] userSet = new String[] {};

            // Pour chaque élément utilisateur
            while (subMatcher.find()) {

                // On le rajoute à la liste des éléments utilisateurs
                String[] newSet = new String[userSet.length + 1];
                System.arraycopy(userSet, 0, newSet, 0, userSet.length);
                newSet[newSet.length - 1] = subMatcher.group().substring(1, subMatcher.group().length() - 1);
                userSet = newSet;

                // Et on supprime l'élément utilisateur
                subTagProcessed = subMatcher.replaceFirst("");
                subMatcher.reset(subTagProcessed);
            }

            // On traite ensuite le tag comme un tag générique en passant la liste des éléments utilisateur
            subTagProcessed = StringTags.replaceRandomStringGenerationTag(subTagProcessed, "<rs:\\d*>", userSet);
            tempString = matcher.replaceFirst(subTagProcessed);
            matcher.reset(tempString);
        }
        
        return tempString;
    }
}

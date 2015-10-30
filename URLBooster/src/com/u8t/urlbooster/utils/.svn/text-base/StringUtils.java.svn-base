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

public abstract class StringUtils {

    private static final int DEFAULT_LINE_LENGTH = 60;
    
    private StringUtils() { }

    /**
     * Méthode renvoyant une chaine centrée, entourée de part et d'autre d'un caractère
     * spécifié. La chaîne ne 'touchera' pas les caractères de complétion : un caractère
     * blanc est ajouté de part et d'autre de la chaine.
     */
    public static String getCenteredString(final String s, final int lineLength, final char completionChar) {
        String returnString = "";

        if (s.length() > 0) {
        
            // La ligne sera entourée de deux charactères blancs
            int stringLength = s.length() + 2;
    
            if ((stringLength) >= lineLength)
                return s;
    
            int surroundingChars = lineLength - stringLength;
            int rightSurroundingChars = surroundingChars / 2;
            int leftSurroundingChars = surroundingChars - rightSurroundingChars;
    
            returnString = fill(completionChar, leftSurroundingChars) + " " + s + " " + fill(completionChar, rightSurroundingChars);
        
        } else
            returnString = fill(completionChar, lineLength);

        return returnString;
    }

    public static String getCenteredString(final String s, final int lineLength) {
        return getCenteredString(s, lineLength, ' ');
    }
    
    public static String getCenteredString(final String s, final char completionChar) {
        return getCenteredString(s, DEFAULT_LINE_LENGTH, completionChar);
    }
    
    public static String getCenteredString(final String s) {
        return getCenteredString(s, DEFAULT_LINE_LENGTH);
    }

    public static String fill(final char c, final int length) {

        if (length <= 0) {
            return "";
        }

        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = c;
        }

        return new String(chars);
    }
}

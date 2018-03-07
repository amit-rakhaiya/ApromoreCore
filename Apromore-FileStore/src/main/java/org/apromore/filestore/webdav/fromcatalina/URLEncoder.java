/*
 * Copyright © 2009-2018 The Apromore Initiative.
 *
 * This file is part of "Apromore".
 *
 * "Apromore" is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * "Apromore" is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.
 * If not, see <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */
package org.apromore.filestore.webdav.fromcatalina;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.BitSet;

/**
 * 
 * This class is very similar to the java.net.URLEncoder class.
 * 
 * Unfortunately, with java.net.URLEncoder there is no way to specify to the
 * java.net.URLEncoder which characters should NOT be encoded.
 * 
 * This code was moved from DefaultServlet.java
 * 
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 */
public class URLEncoder {
    protected static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    // Array containing the safe characters set.
    protected BitSet _safeCharacters = new BitSet(256);

    public URLEncoder() {
        for (char i = 'a'; i <= 'z'; i++) {
            addSafeCharacter(i);
        }
        for (char i = 'A'; i <= 'Z'; i++) {
            addSafeCharacter(i);
        }
        for (char i = '0'; i <= '9'; i++) {
            addSafeCharacter(i);
        }
    }

    public void addSafeCharacter(char c) {
        _safeCharacters.set(c);
    }

    public String encode(String path) {
        int maxBytesPerChar = 10;
        // int caseDiff = ('a' - 'A');
        StringBuilder rewrittenPath = new StringBuilder(path.length());
        ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(buf, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
            writer = new OutputStreamWriter(buf);
        }

        for (int i = 0; i < path.length(); i++) {
            int c = (int) path.charAt(i);
            if (_safeCharacters.get(c)) {
                rewrittenPath.append((char) c);
            } else {
                // convert to external encoding before hex conversion
                try {
                    writer.write((char) c);
                    writer.flush();
                } catch (IOException e) {
                    buf.reset();
                    continue;
                }
                byte[] ba = buf.toByteArray();
                for (int j = 0; j < ba.length; j++) {
                    // Converting each byte in the buffer
                    byte toEncode = ba[j];
                    rewrittenPath.append('%');
                    int low = (int) (toEncode & 0x0f);
                    int high = (int) ((toEncode & 0xf0) >> 4);
                    rewrittenPath.append(HEXADECIMAL[high]);
                    rewrittenPath.append(HEXADECIMAL[low]);
                }
                buf.reset();
            }
        }
        return rewrittenPath.toString();
    }
}

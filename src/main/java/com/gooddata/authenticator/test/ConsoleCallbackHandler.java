/* 
* Copyright (c) 2012, GoodData Corporation. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are permitted provided
* that the following conditions are met:
*
*     * Redistributions of source code must retain the above copyright notice, this list of conditions and
*       the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
*        and the following disclaimer in the documentation and/or other materials provided with the distribution.
*     * Neither the name of the GoodData Corporation nor the names of its contributors may be used to endorse
*        or promote products derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
* OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
* AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
* DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.gooddata.authenticator.test;

import javax.security.auth.callback.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple console callback handler for use with SASL. Used with GssExample.java.
 * @author Zdenek Svoboda
 * @version 1.0
 */
public class ConsoleCallbackHandler implements CallbackHandler {

    /**
     * Handle the SASL callback
     *
     * @param callbacks - SASL callbacks
     * @throws java.io.IOException
     * @throws UnsupportedCallbackException
     */
    public void handle(Callback[] callbacks)
            throws java.io.IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                NameCallback cb = (NameCallback) callbacks[i];
                cb.setName(getInput(cb.getPrompt()));

            } else if (callbacks[i] instanceof PasswordCallback) {
                PasswordCallback cb = (PasswordCallback) callbacks[i];

                String pw = getInput(cb.getPrompt());
                char[] passwd = new char[pw.length()];
                pw.getChars(0, passwd.length, passwd, 0);

                cb.setPassword(passwd);
            } else {
                throw new UnsupportedCallbackException(callbacks[i]);
            }
        }
    }

    /**
     * A reader from Standard Input. In real world apps, this would
     * typically be a TextComponent or similar widget.
     *
     * @param prompt - prompt
     * @return console input
     * @throws IOException
     */
    private String getInput(String prompt) throws IOException {
        System.out.print(prompt);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        return in.readLine();
    }

}
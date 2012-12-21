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

package com.gooddata.authenticator;

import com.atlassian.jira.security.login.JiraSeraphAuthenticator;
import org.apache.log4j.Logger;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.security.Principal;


/**
 * A JIRA KRB5 authenticator
 *
 * @author Zdenek Svoboda
 * @version 1.0
 */
public class KrbCrowdAuthenticator extends JiraSeraphAuthenticator {

    /**
     * Logger
     */
    private final static Logger log = Logger.getLogger(KrbCrowdAuthenticator.class);

    /**
     * Authentication context
     */
    private final static String AUTHENTICATION_CONTEXT = "GOODDATA-JIRA";

    /** The configuration files krb5.conf and gss.conf must be placed to the JIRA_HOME/conf */
    static {
        System.setProperty("java.security.krb5.conf", "conf/krb5.conf");
        System.setProperty("java.security.auth.login.config", "conf/gss.conf");
    }

    /**
     * Authenticates user.
     *
     * @param user     user - authenticated user
     * @param password - authentication password
     * @return true/false - authentication decision
     */
    @Override
    protected boolean authenticate(Principal user, String password) {
        String username = user.getName();
        LoginContext lc = null;
        try {
            lc = new LoginContext(AUTHENTICATION_CONTEXT, new StringCallbackHandler(username, password));
            lc.login();
            log.info("Kerberos authentication attempt PASSED!");
            return true;

        } catch (LoginException le) {
            log.error("Kerberos authentication attempt FAILED. Reason:" + le);
        }
        return false;
    }

}

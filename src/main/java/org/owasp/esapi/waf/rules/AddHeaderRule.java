/**
 * OWASP Enterprise Security API (ESAPI)
 *
 * This file is part of the Open Web Application Security Project (OWASP)
 * Enterprise Security API (ESAPI) project. For details, please see
 * <a href="http://www.owasp.org/index.php/ESAPI">http://www.owasp.org/index.php/ESAPI</a>.
 *
 * Copyright (c) 2009 - The OWASP Foundation
 *
 * The ESAPI is published by OWASP under the BSD license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 *
 * @author Arshan Dabirsiaghi <a href="http://www.aspectsecurity.com">Aspect Security</a>
 * @created 2009
 */
package org.owasp.esapi.waf.rules;

import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.owasp.esapi.waf.actions.Action;
import org.owasp.esapi.waf.actions.DoNothingAction;
import org.owasp.esapi.waf.internal.InterceptingHTTPServletResponse;

/**
 * This is the Rule subclass executed for &lt;add-header&gt; rules.
 * @author Arshan Dabirsiaghi
 *
 */
public class AddHeaderRule extends Rule {

    private String header;
    private String value;
    private Pattern path;
    private List<Object> exceptions;

    public AddHeaderRule(String id, String header, String value, Pattern path, List<Object> exceptions) {
        setId(id);
        this.header = header;
        this.value = value;
        this.path = path;
        this.exceptions = exceptions;
    }

    public Action check(
            HttpServletRequest request,
            InterceptingHTTPServletResponse response,
            HttpServletResponse httpResponse) {

        DoNothingAction action = new DoNothingAction();

        if ( path.matcher(request.getRequestURI()).matches() ) {

            for(int i=0;i<exceptions.size();i++) {

                Object o = exceptions.get(i);

                if ( o instanceof String ) {
                    if ( request.getRequestURI().equals((String)o)) {
                        action.setFailed(false);
                        action.setActionNecessary(false);
                        return action;
                    }
                } else if ( o instanceof Pattern ) {
                    if ( ((Pattern)o).matcher(request.getRequestURI()).matches() ) {
                        action.setFailed(false);
                        action.setActionNecessary(false);
                        return action;
                    }
                }

            }


            action.setFailed(true);
            action.setActionNecessary(false);

            if ( response != null ) {
                response.setHeader(header, value);
            } else {
                httpResponse.setHeader(header, value);
            }

        }

        return action;
    }

}

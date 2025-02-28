/**
 * OWASP Enterprise Security API (ESAPI)
 *
 * This file is part of the Open Web Application Security Project (OWASP)
 * Enterprise Security API (ESAPI) project. For details, please see
 * <a href="http://www.owasp.org/index.php/ESAPI">http://www.owasp.org/index.php/ESAPI</a>.
 *
 * Copyright (c) 2007 - The OWASP Foundation
 *
 * The ESAPI is published by OWASP under the BSD license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 *
 * @author Jeff Williams <a href="http://www.aspectsecurity.com">Aspect Security</a>
 * @created 2007
 */
package org.owasp.esapi;

import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.owasp.esapi.errors.AuthenticationException;
import org.owasp.esapi.errors.AuthenticationHostException;
import org.owasp.esapi.errors.EncryptionException;

/**
 * The User interface represents an application user or user account. There is quite a lot of information that an
 * application must store for each user in order to enforce security properly. There are also many rules that govern
 * authentication and identity management.
 * <P>
 * A user account can be in one of several states. When first created, a User should be disabled, not expired, and
 * unlocked. To start using the account, an administrator should enable the account. The account can be locked for a
 * number of reasons, most commonly because they have failed login for too many times. Finally, the account can expire
 * after the expiration date has been reached. The User must be enabled, not expired, and unlocked in order to pass
 * authentication.
 *
 * @author <a href="mailto:jeff.williams@aspectsecurity.com?subject=ESAPI question">Jeff Williams</a> at <a href="http://www.aspectsecurity.com">Aspect Security</a>
 * @author Chris Schmidt (chrisisbeef .at. gmail.com) <a href="http://www.digital-ritual.com">Digital Ritual Software</a>
 * @since June 1, 2007
 */

public interface User extends Principal, Serializable {
    /**
     * @return the locale
     */
    Locale getLocale();

    /**
     * @param locale the locale to set
     */
    void setLocale(Locale locale);

    /**
     * Adds a role to this user's account.
     *
     * @param role
     *         the role to add
     *
     * @throws AuthenticationException
     *         the authentication exception
     */
    void addRole(String role) throws AuthenticationException;

    /**
     * Adds a set of roles to this user's account.
     *
     * @param newRoles
     *         the new roles to add
     *
     * @throws AuthenticationException
     *         the authentication exception
     */
    void addRoles(Set<String> newRoles) throws AuthenticationException;

    /**
     * Sets the user's password, performing a verification of the user's old password, the equality of the two new
     * passwords, and the strength of the new password.
     *
     * @param oldPassword
     *         the old password
     * @param newPassword1
     *         the new password
     * @param newPassword2
     *         the new password - used to verify that the new password was typed correctly
     *
     * @throws AuthenticationException
     *         if newPassword1 does not match newPassword2, if oldPassword does not match the stored old password, or if the new password does not meet complexity requirements
     * @throws EncryptionException
     */
    void changePassword(String oldPassword, String newPassword1, String newPassword2) throws AuthenticationException, EncryptionException;

    /**
     * Disable this user's account.
     */
    void disable();

    /**
     * Enable this user's account.
     */
    void enable();

    /**
     * Gets this user's account id number.
     *
     * @return the account id
     */
    long getAccountId();

    /**
     * Gets this user's account name.
     *
     * @return the account name
     */
    String getAccountName();

    /**
     * Gets the CSRF token for this user's current sessions.
     *
     * @return the CSRF token
     */
    String getCSRFToken();

    /**
     * Returns the date that this user's account will expire.
     *
     * @return Date representing the account expiration time.
     */
    Date getExpirationTime();

    /**
     * Returns the number of failed login attempts since the last successful login for an account. This method is
     * intended to be used as a part of the account lockout feature, to help protect against brute force attacks.
     * However, the implementor should be aware that lockouts can be used to prevent access to an application by a
     * legitimate user, and should consider the risk of denial of service.
     *
     * @return the number of failed login attempts since the last successful login
     */
    int getFailedLoginCount();

    /**
     * Returns the last host address used by the user. This will be used in any log messages generated by the processing
     * of this request.
     *
     * @return the last host address used by the user
     */
    String getLastHostAddress();

    /**
     * Returns the date of the last failed login time for a user. This date should be used in a message to users after a
     * successful login, to notify them of potential attack activity on their account.
     *
     * @return date of the last failed login
     *
     * @throws AuthenticationException
     *         the authentication exception
     */
    Date getLastFailedLoginTime() throws AuthenticationException;

    /**
     * Returns the date of the last successful login time for a user. This date should be used in a message to users
     * after a successful login, to notify them of potential attack activity on their account.
     *
     * @return date of the last successful login
     */
    Date getLastLoginTime();

    /**
     * Gets the date of user's last password change.
     *
     * @return the date of last password change
     */
    Date getLastPasswordChangeTime();

    /**
     * Gets the roles assigned to a particular account.
     *
     * @return an immutable set of roles
     */
    Set<String> getRoles();

    /**
     * Gets the screen name (alias) for the current user.
     *
     * @return the screen name
     */
    String getScreenName();

    /**
     * Adds a session for this User.
     *
     * @param s
     *             The session to associate with this user.
     */
    void addSession( HttpSession s );

    /**
     * Removes a session for this User.
     *
     * @param s
     *             The session to remove from being associated with this user.
     */
    void removeSession( HttpSession s );

    /**
     * Returns a Set containing the sessions associated with this User.
     * @return The Set of sessions for this User.
     */
    Set getSessions();

    /**
     * Increment failed login count.
     */
    void incrementFailedLoginCount();

    /**
     * Checks if user is anonymous.
     *
     * @return true, if user is anonymous
     */
    boolean isAnonymous();

    /**
     * Checks if this user's account is currently enabled.
     *
     * @return true, if account is enabled
     */
    boolean isEnabled();

    /**
     * Checks if this user's account is expired.
     *
     * @return true, if account is expired
     */
    boolean isExpired();

    /**
     * Checks if this user's account is assigned a particular role.
     *
     * @param role
     *         the role for which to check
     *
     * @return true, if role has been assigned to user
     */
    boolean isInRole(String role);

    /**
     * Checks if this user's account is locked.
     *
     * @return true, if account is locked
     */
    boolean isLocked();

    /**
     * Tests to see if the user is currently logged in.
     *
     * @return true, if the user is logged in
     */
    boolean isLoggedIn();

    /**
     * Tests to see if this user's session has exceeded the absolute time out based
      * on ESAPI's configuration settings.
     *
     * @return true, if user's session has exceeded the absolute time out
     */
    boolean isSessionAbsoluteTimeout();

    /**
      * Tests to see if the user's session has timed out from inactivity based
      * on ESAPI's configuration settings.
      *
      * A session may timeout prior to ESAPI's configuration setting due to
      * the servlet container setting for session-timeout in web.xml. The
      * following is an example of a web.xml session-timeout set for one hour.
      *
      * <session-config>
      *   <session-timeout>60</session-timeout>
      * </session-config>
      *
      * @return true, if user's session has timed out from inactivity based
      *               on ESAPI configuration
      */
     boolean isSessionTimeout();

    /**
     * Lock this user's account.
     */
    void lock();

    /**
     * Login with password.
     *
     * @param password
     *         the password
     * @throws AuthenticationException
     *         if login fails
     */
    void loginWithPassword(String password) throws AuthenticationException;

    /**
     * Logout this user.
     */
    void logout();

    /**
     * Removes a role from this user's account.
     *
     * @param role
     *         the role to remove
     * @throws AuthenticationException
     *         the authentication exception
     */
    void removeRole(String role) throws AuthenticationException;

    /**
     * Returns a token to be used as a prevention against CSRF attacks. This token should be added to all links and
     * forms. The application should verify that all requests contain the token, or they may have been generated by a
     * CSRF attack. It is generally best to perform the check in a centralized location, either a filter or controller.
     * See the verifyCSRFToken method.
     *
     * @return the new CSRF token
     *
     * @throws AuthenticationException
     *         the authentication exception
     */
    String resetCSRFToken() throws AuthenticationException;

    /**
     * Sets this user's account name.
     *
     * @param accountName the new account name
     */
    void setAccountName(String accountName);

    /**
     * Sets the date and time when this user's account will expire.
     *
     * @param expirationTime the new expiration time
     */
    void setExpirationTime(Date expirationTime);

    /**
     * Sets the roles for this account.
     *
     * @param roles
     *         the new roles
     *
     * @throws AuthenticationException
     *         the authentication exception
     */
    void setRoles(Set<String> roles) throws AuthenticationException;

    /**
     * Sets the screen name (username alias) for this user.
     *
     * @param screenName the new screen name
     */
    void setScreenName(String screenName);

    /**
     * Unlock this user's account.
     */
    void unlock();

    /**
     * Verify that the supplied password matches the password for this user. This method
     * is typically used for "reauthentication" for the most sensitive functions, such
     * as transactions, changing email address, and changing other account information.
     *
     * @param password
     *         the password that the user entered
     *
     * @return true, if the password passed in matches the account's password
     *
     * @throws EncryptionException
     */
    boolean verifyPassword(String password) throws EncryptionException;

    /**
     * Set the time of the last failed login for this user.
     *
     * @param lastFailedLoginTime the date and time when the user just failed to login correctly.
     */
    void setLastFailedLoginTime(Date lastFailedLoginTime);

    /**
     * Set the last remote host address used by this user.
     *
     * @param remoteHost The address of the user's current source host.
     */
    void setLastHostAddress(String remoteHost) throws AuthenticationHostException;

    /**
     * Set the time of the last successful login for this user.
     *
     * @param lastLoginTime the date and time when the user just successfully logged in.
     */
    void setLastLoginTime(Date lastLoginTime);

    /**
     * Set the time of the last password change for this user.
     *
     * @param lastPasswordChangeTime the date and time when the user just successfully changed his/her password.
     */
    void setLastPasswordChangeTime(Date lastPasswordChangeTime);

    /**
     * Returns the hashmap used to store security events for this user. Used by the
     * IntrusionDetector.
     */
    HashMap getEventMap();


    /**
     * The ANONYMOUS user is used to represent an unidentified user. Since there is
     * always a real user, the ANONYMOUS user is better than using null to represent
     * this.
     */
    final User ANONYMOUS = new User() {

        private static final long serialVersionUID = -1850916950784965502L;

        private String csrfToken = "";
        private Set<Object> sessions = new HashSet<Object>();
        private Locale locale = null;

        /**
         * {@inheritDoc}
         */
        public void addRole(String role) throws AuthenticationException {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void addRoles(Set newRoles) throws AuthenticationException {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void changePassword(String oldPassword, String newPassword1,
                String newPassword2) throws AuthenticationException,
                EncryptionException {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void disable() {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void enable() {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public long getAccountId() {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        public String getAccountName() {
            return "Anonymous";
        }

        /**
         * Alias method that is equivalent to getAccountName()
         *
         * @return the name of the current user's account
         */
        public String getName() {
            return getAccountName();
        }

        /**
         * {@inheritDoc}
         */
        public String getCSRFToken() {
            return csrfToken;
        }

        /**
         * {@inheritDoc}
         */
        public Date getExpirationTime() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public int getFailedLoginCount() {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        public Date getLastFailedLoginTime() throws AuthenticationException {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public String getLastHostAddress() {
            return "unknown";
        }

        /**
         * {@inheritDoc}
         */
        public Date getLastLoginTime() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public Date getLastPasswordChangeTime() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public Set<String> getRoles() {
            return new HashSet<String>();
        }

        /**
         * {@inheritDoc}
         */
        public String getScreenName() {
            return "Anonymous";
        }

        /**
         * {@inheritDoc}
         */
        public void addSession(HttpSession s)  {
        }

        /**
         * {@inheritDoc}
         */
        public void removeSession(HttpSession s)  {
        }

        /**
         * {@inheritDoc}
         */
        public Set getSessions()  {
            return sessions;
        }

        /**
         * {@inheritDoc}
         */
        public void incrementFailedLoginCount() {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public boolean isAnonymous() {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        public boolean isEnabled() {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        public boolean isExpired() {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        public boolean isInRole(String role) {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        public boolean isLocked() {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        public boolean isLoggedIn() {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        public boolean isSessionAbsoluteTimeout() {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        public boolean isSessionTimeout() {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        public void lock() {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void loginWithPassword(String password)
                throws AuthenticationException {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void logout() {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void removeRole(String role) throws AuthenticationException {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public String resetCSRFToken() throws AuthenticationException {
            csrfToken = ESAPI.randomizer().getRandomString(8, EncoderConstants.CHAR_ALPHANUMERICS);
            return csrfToken;
        }

        /**
         * {@inheritDoc}
         */
        public void setAccountName(String accountName) {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void setExpirationTime(Date expirationTime) {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void setRoles(Set roles) throws AuthenticationException {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void setScreenName(String screenName) {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void unlock() {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public boolean verifyPassword(String password) throws EncryptionException {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void setLastFailedLoginTime(Date lastFailedLoginTime) {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
        public void setLastLoginTime(Date lastLoginTime) {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         * {@inheritDoc}
         */
         public void setLastHostAddress(String remoteHost) {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

         /**
         * {@inheritDoc}
         */
        public void setLastPasswordChangeTime(Date lastPasswordChangeTime) {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }

        /**
         *  {@inheritDoc}
         */
        public HashMap getEventMap() {
            throw new RuntimeException("Invalid operation for the anonymous user");
        }
         /**
         * @return the locale
         */
        public Locale getLocale() {
            return locale;
        }

        /**
         * @param locale the locale to set
         */
        public void setLocale(Locale locale) {
            this.locale = locale;
        }
    };
}

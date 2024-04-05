package org.owasp.esapi.configuration.consts;
import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;

/**
 * Enum used for initialization of esapi configuration files.
 *
 * @since 2.2
 */
public enum EsapiConfiguration {

    OPSTEAM_ESAPI_CFG("org.owasp.esapi.opsteam", 1),
    DEVTEAM_ESAPI_CFG("org.owasp.esapi.devteam", 2);

    /**
     * Key of system property pointing to path esapi to configuration file.
     */
    @RUntainted String configName;

    /**
     * Priority of configuration (higher numer - higher priority).
     */
    int priority;

    EsapiConfiguration(@RUntainted String configName, int priority) {
        this.configName = configName;
        this.priority = priority;
    }

    public @RUntainted String getConfigName() {
        return configName;
    }

    public int getPriority() {
        return priority;
    }
}

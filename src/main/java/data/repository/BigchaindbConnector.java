package data.repository;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.bigchaindb.builders.BigchainDbConfigBuilder;

/**
 * Handle the interaction with the BlockchainDB sources.
 * */
@Configuration
@ConfigurationProperties(prefix = "bigchaindb")
public class BigchaindbConnector {
    
    private static String base_url, app_id, app_key;
    
    public static void setConfig() {
        BigchainDbConfigBuilder
                .baseUrl(base_url) //or use http://testnet.bigchaindb.com
                .addToken("app_id", app_id)
                .addToken("app_key", app_key)
                .setup();
        
    }
}

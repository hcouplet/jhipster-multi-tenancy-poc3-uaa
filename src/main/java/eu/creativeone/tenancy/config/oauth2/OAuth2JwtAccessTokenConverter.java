package eu.creativeone.tenancy.config.oauth2;

import eu.creativeone.config.UaaProperties;
import eu.creativeone.tenancy.security.OAuth2AuthenticationTenant;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.Map;

/**
 * This bean generates an token enhancer, which manages the exchange between JWT acces tokens and Authentication
 * in both directions.
 *
 * @return an access token converter configured with the authorization server's public/private keys
 */
public class OAuth2JwtAccessTokenConverter extends JwtAccessTokenConverter
{
    private OAuth2JwtAccessTokenConverter() {}

    public OAuth2JwtAccessTokenConverter(UaaProperties uaaProperties)
    {
        super();
        KeyPair keyPair = new KeyStoreKeyFactory(
            new ClassPathResource(uaaProperties.getKeyStore().getName()), uaaProperties.getKeyStore().getPassword().toCharArray())
            .getKeyPair(uaaProperties.getKeyStore().getAlias());
        setKeyPair(keyPair);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        return converter;
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map)
    {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        OAuth2AuthenticationTenant oAuth2AuthenticationTenant = new OAuth2AuthenticationTenant(authentication.getOAuth2Request(), authentication, map);
        return oAuth2AuthenticationTenant;
    }
}

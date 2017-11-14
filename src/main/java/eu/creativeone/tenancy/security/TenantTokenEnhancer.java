package eu.creativeone.tenancy.security;

import eu.creativeone.tenancy.user.TenantUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Adds the standard "tid" claim to tokens so we know the tenant to who the user belong
 */
@Component
public class TenantTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        addClaims((DefaultOAuth2AccessToken) accessToken, authentication);
        return accessToken;
    }

    private void addClaims(DefaultOAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken token = accessToken;
        Map<String, Object> additionalInformation = token.getAdditionalInformation();
        if(additionalInformation.isEmpty()) {
            additionalInformation = new LinkedHashMap<String, Object>();
        }

        //add "tid" claim with tenantID
        String tentantId = null;
        if (authentication.getPrincipal() instanceof TenantUser)
        {
            tentantId = ((TenantUser) authentication.getPrincipal()).getTenantId();
        }
        additionalInformation.put("tid",tentantId);
        token.setAdditionalInformation(additionalInformation);
    }
}

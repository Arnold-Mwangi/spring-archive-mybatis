package com.kirigwi.springarchive.authorisation.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

import java.io.InputStream;

@Configuration
public class GoogleOAuthConfig {

    @Value("classpath:google_oauth2/oauth_client.json")
    private Resource clientSecretResource;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() throws Exception {
        return new InMemoryClientRegistrationRepository(googleClientRegistration());
    }

    @Bean
    public ClientRegistration googleClientRegistration() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = clientSecretResource.getInputStream()) {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            JsonNode webNode = jsonNode.get("web");

            String clientId = webNode.get("client_id").asText();
            String clientSecret = webNode.get("client_secret").asText();
            String redirectUri = webNode.get("redirect_uris").get(0)
                    .asText(); // Read from JSON

            return ClientRegistration.withRegistrationId("google")
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .authorizationGrantType(
                            AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri(
                            redirectUri) // Use the actual redirect URI from JSON
                    .scope("openid", "profile", "email")
                    .authorizationUri(
                            "https://accounts.google.com/o/oauth2/auth")
                    .tokenUri("https://oauth2.googleapis.com/token")
                    .userInfoUri(
                            "https://openidconnect.googleapis.com/v1/userinfo")
                    .userNameAttributeName(IdTokenClaimNames.SUB)
                    .clientName("Google")
                    .build();
        }
    }
}

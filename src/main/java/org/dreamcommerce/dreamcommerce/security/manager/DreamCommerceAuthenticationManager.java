package org.dreamcommerce.dreamcommerce.security.manager;

import lombok.AllArgsConstructor;
import org.dreamcommerce.dreamcommerce.security.exception.InvalidAuthenticationMethod;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component // Add to the context
public class DreamCommerceAuthenticationManager implements AuthenticationManager {
    private final List<AuthenticationProvider> authenticationProviders;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationProvider authenticationProvider = getAuthenticationProviderFor(authentication);

        Authentication authenticationResult = authenticationProvider.authenticate(authentication);
        return authenticationResult;
    }

    private AuthenticationProvider getAuthenticationProviderFor(Authentication authentication) {
        return  authenticationProviders.stream()
                    .filter((provider) -> provider.supports(authentication.getClass()))
                .findFirst()
                .orElseThrow(()-> new InvalidAuthenticationMethod("Authentication not supported"));
    }
}

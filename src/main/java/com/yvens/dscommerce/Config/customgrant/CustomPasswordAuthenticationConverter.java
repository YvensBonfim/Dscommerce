package com.yvens.dscommerce.Config.customgrant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class CustomPasswordAuthenticationConverter implements AuthenticationConverter {

    @Nullable
    @Override
    public Authentication convert(HttpServletRequest request) {
        
        // 1. Verifica se o Grant Type é 'password'
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!"password".equals(grantType)) {
            return null;
        }
        
        MultiValueMap<String, String> parameters = getParameters(request);
        
        // 2. Validação básica de parâmetros obrigatórios usando Strings diretas
        // O OAuth2ParameterNames não possui as constantes USERNAME e PASSWORD por padrão
        validateParameter(parameters, "username");
        validateParameter(parameters, "password");
        
        // Validação de Scope (opcional, mas deve ser único se presente)
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (StringUtils.hasText(scope) && parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
        }

        Set<String> requestedScopes = null;
        if (StringUtils.hasText(scope)) {
            requestedScopes = new HashSet<>(
                    Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }
        
        // 3. Extração de parâmetros adicionais (tudo exceto grant_type e scope)
        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) &&
                !key.equals(OAuth2ParameterNames.SCOPE)) {
                additionalParameters.put(key, value.get(0));
            }
        });
        
        // 4. Obtém o Client (o principal autenticado pelo filtro anterior)
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        
        if (clientPrincipal == null) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
        }
        
        return new CustomPasswordAuthenticationToken(clientPrincipal, requestedScopes, additionalParameters);
    }

    private void validateParameter(MultiValueMap<String, String> parameters, String parameterName) {
        String value = parameters.getFirst(parameterName);
        if (!StringUtils.hasText(value) || parameters.get(parameterName).size() != 1) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
        }
    }

    private static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            for (String value : values) {
                parameters.add(key, value);
            }
        });
        return parameters;
    }
}
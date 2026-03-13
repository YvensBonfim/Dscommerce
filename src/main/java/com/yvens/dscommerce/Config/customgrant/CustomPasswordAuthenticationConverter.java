package com.yvens.dscommerce.Config.customgrant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        
        //Verifica se o Grant Type é 'password'
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!"password".equals(grantType)) {
            return null;
        }
        
        MultiValueMap<String, String> parameters = getParameters(request);
        
        // Validação obrigatória de username e password
        validateParameter(parameters, "username");
        validateParameter(parameters, "password");
        
        // Validação de Scope (opcional, mas deve ser único se presente)
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        List<String> scopeList = parameters.get(OAuth2ParameterNames.SCOPE);
        if (StringUtils.hasText(scope) && scopeList != null && scopeList.size() != 1) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
        }

        Set<String> requestedScopes = null;
        if (StringUtils.hasText(scope)) {
            requestedScopes = new HashSet<>(
                    Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }
        
        // Extração de parâmetros adicionais
        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) &&
                !key.equals(OAuth2ParameterNames.SCOPE)) {
                
                // Uso de Objects.requireNonNull para conformidade com @NonNull
                // e garantia de que o valor não é nulo antes de pegar o índice 0
                if (value != null && !value.isEmpty()) {
                    additionalParameters.put(key, Objects.requireNonNull(value.get(0)));
                }
            }
        });
        
      // Obtém o Client Principal do contexto de segurança
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        
        if (clientPrincipal == null) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
        }
        
        return new CustomPasswordAuthenticationToken(clientPrincipal, requestedScopes, additionalParameters);
    }

    private void validateParameter(MultiValueMap<String, String> parameters, String parameterName) {
        String value = parameters.getFirst(parameterName);
        List<String> values = parameters.get(parameterName);
        
        // Validação robusta para evitar Null Pointer e avisos de Type Safety
        if (!StringUtils.hasText(value) || values == null || values.size() != 1) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
        }
    }

    private static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        
        parameterMap.forEach((key, values) -> {
            if (values != null) {
                for (String value : values) {
                    parameters.add(key, value);
                }
            }
        });
        return parameters;
    }
}
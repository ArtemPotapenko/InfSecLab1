package ru.itmo.inf_sec.lab1.filter;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Глобальный фильтр, экранирующий все строки в JSON-ответах.
 */
@ControllerAdvice
public class XssSanitizationAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType,
            Class<? extends HttpMessageConverter<?>> converterType,
            ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Map<?, ?> map) {
            return sanitizeMap(map);
        }
        if (body instanceof String str) {
            return StringEscapeUtils.escapeHtml4(str);
        }
        return body;
    }

    private Map<String, Object> sanitizeMap(Map<?, ?> map) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> String.valueOf(e.getKey()),
                        e -> sanitizeValue(e.getValue())
                ));
    }

    private Object sanitizeValue(Object value) {
        if (value instanceof String str) {
            return StringEscapeUtils.escapeHtml4(str);
        } else if (value instanceof Map<?, ?> nested) {
            return sanitizeMap(nested);
        }
        return value;
    }
}


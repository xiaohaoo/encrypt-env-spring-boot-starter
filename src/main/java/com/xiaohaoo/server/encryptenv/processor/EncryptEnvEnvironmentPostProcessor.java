/*
 * Copyright (c) 2022 xiaohao & xiaohaoo
 */

package com.xiaohaoo.server.encryptenv.processor;

import com.xiaohaoo.server.encryptenv.encrypt.AesSecretHandler;
import com.xiaohaoo.server.encryptenv.encrypt.SecretHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaohao
 * @version 1.0
 * @date 2022/1/18 6:56 PM
 */
public class EncryptEnvEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private final static Pattern pattern = Pattern.compile("\\{enc:(.*)}");

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        final String key = environment.getProperty("enc-key");
        if (Objects.nonNull(key)) {
            MutablePropertySources propertySources = environment.getPropertySources();
            for (PropertySource<?> propertySource : propertySources) {
                if (propertySource instanceof OriginTrackedMapPropertySource) {
                    if (propertySource.getSource() instanceof Map<?, ?> source) {
                        Map<String, String> decEnvPropertySource = processEnv(environment, source);
                        if (Objects.nonNull(decEnvPropertySource)) {
                            MapPropertySource mapPropertySource = new MapPropertySource(String.format("%s-dec-env", propertySource.getName()),
                                Collections.unmodifiableMap(decEnvPropertySource));
                            propertySources.addFirst(mapPropertySource);
                        }
                    }
                }
            }
        }
    }

    private Map<String, String> processEnv(ConfigurableEnvironment environment, Map<?, ?> source) {
        final String prefix = "enc:";
        SecretHandler secretHandler = new AesSecretHandler(environment.getProperty("enc-key"));
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<?, ?> entry : source.entrySet()) {
            String key = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            if (value.startsWith(prefix)) {
                map.put(key, secretHandler.decrypt(value.replaceAll(prefix, "")));
            } else {
                Matcher matcher = pattern.matcher(value);
                if (matcher.find()) {
                    String newValue = matcher.replaceFirst(secretHandler.decrypt(matcher.group(1)));
                    map.put(key, newValue);
                }
            }
        }
        return map.isEmpty() ? null : map;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
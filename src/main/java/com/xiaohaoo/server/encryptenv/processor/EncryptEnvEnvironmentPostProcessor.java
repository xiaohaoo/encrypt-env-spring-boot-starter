package com.xiaohaoo.server.encryptenv.processor;

import com.xiaohaoo.server.encryptenv.encrypt.AesSecretResolve;
import com.xiaohaoo.server.encryptenv.encrypt.SecretResolve;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xiaohao
 * @version 1.0
 * @date 2022/1/18 6:56 PM
 */
public class EncryptEnvEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private final String PREFIX = "enc:";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        final String key = environment.getProperty("enc-key");
        if (Objects.nonNull(key)) {
            try {
                MutablePropertySources propertySources = environment.getPropertySources();
                SecretResolve secretResolve = new AesSecretResolve(key);
                for (PropertySource<?> propertySource : propertySources) {
                    if (propertySource instanceof OriginTrackedMapPropertySource) {
                        if (propertySource.getSource() instanceof Map) {
                            Map<String, OriginTrackedValue> source = (Map<String, OriginTrackedValue>) propertySource.getSource();

                            Map<String, Object> decEnvPropertySource = new HashMap<>();

                            for (Map.Entry<String, OriginTrackedValue> entry : source.entrySet()) {
                                processEnv(entry.getKey(), String.valueOf(entry.getValue()), decEnvPropertySource, secretResolve);
                            }

                            MapPropertySource mapPropertySource = new MapPropertySource(String.format("%s-dec-env", propertySource.getName()),
                                Collections.unmodifiableMap(decEnvPropertySource));

                            propertySources.addFirst(mapPropertySource);
                        }
                    }
                }
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
                e.printStackTrace();
            }
        }

    }

    public void processEnv(String key, String value, Map<String, Object> map, SecretResolve secretResolve) throws NoSuchPaddingException,
        IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (value.startsWith(PREFIX)) {
            String decrypt = secretResolve.decrypt(value.replace(PREFIX, ""));
            map.put(key, decrypt);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
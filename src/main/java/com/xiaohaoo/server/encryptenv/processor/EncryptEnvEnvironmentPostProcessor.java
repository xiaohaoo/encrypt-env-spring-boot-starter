package com.xiaohaoo.server.encryptenv.processor;

import com.xiaohaoo.server.encryptenv.encrypt.AesSecretResolve;
import com.xiaohaoo.server.encryptenv.encrypt.SecretResolve;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xiaohao
 * @version 1.0
 * @date 2022/1/18 6:56 PM
 */
public class EncryptEnvEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            SecretResolve secretResolve = new AesSecretResolve(environment.getProperty("enc-key"));
            MutablePropertySources propertySources = environment.getPropertySources();
            for (PropertySource<?> propertySource : propertySources) {
                if (propertySource instanceof OriginTrackedMapPropertySource) {
                    Map<String, OriginTrackedValue> source =
                        new LinkedHashMap<>((Map<String, OriginTrackedValue>) propertySource.getSource());

                    for (Map.Entry<String, OriginTrackedValue> entry : source.entrySet()) {
                        processEnv(entry, secretResolve);
                    }
                    OriginTrackedMapPropertySource originTrackedMapPropertySource =
                        new OriginTrackedMapPropertySource(propertySource.getName(),
                            Collections.unmodifiableMap(source));
                    propertySources.addFirst(originTrackedMapPropertySource);
                }
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }

        System.out.println(environment.getProperty("spring.datasource.username"));
    }

    public void processEnv(Map.Entry<String, OriginTrackedValue> entry, SecretResolve secretResolve) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        final String value = entry.getValue().toString();
        if (value.startsWith("enc:")) {
            System.out.println(value);
            String decrypt = secretResolve.decrypt(value.replace("enc:", ""));
            OriginTrackedValue originTrackedValue = OriginTrackedValue.of(decrypt, entry.getValue().getOrigin());
            entry.setValue(originTrackedValue);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
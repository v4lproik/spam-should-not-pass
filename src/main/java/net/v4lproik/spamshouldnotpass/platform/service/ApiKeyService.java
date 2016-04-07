package net.v4lproik.spamshouldnotpass.platform.service;

import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class ApiKeyService {

    public String generate(){
        SecureRandom rand = new SecureRandom();
        KeyGenerator generator = null;
        try {
            generator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("[ApiKeyService] Error getting generator when generating api key", e);
        }
        generator.init(rand);
        generator.init(256);

        return generator.generateKey().toString();
    }
}

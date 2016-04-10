package net.v4lproik.spamshouldnotpass.platform.services;

import net.v4lproik.spamshouldnotpass.platform.models.PasswordBuilder;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyService {

    public String generate(){
        final PasswordBuilder builder = new PasswordBuilder()
                .lowercase(13)
                .uppercase(13)
                .specials(3)
                .digits(3)
                .shuffle();

        return builder.build();
    }
}

package com.moonbright.infrastructure.util;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@ApplicationScoped
public class JsonUtility {
    private static final Jsonb jsonb;

    static {
        jsonb = JsonbBuilder.create();
    }

    public  Jsonb getJsonb() {
        return jsonb;
    }
    @PreDestroy
    public void closeJsonb()  {
        try {
            jsonb.close();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

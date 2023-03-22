package com.lavrentieva.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class HibernateUtil {
    private static EntityManagerFactory managerFactory;

    private HibernateUtil() {
    }

    public static EntityManager getEntityManager() {
        return Optional.ofNullable(managerFactory)
                .or(() -> {
                    managerFactory = Persistence.createEntityManagerFactory("persistence");
                    return Optional.of(managerFactory);
                })
                .map(EntityManagerFactory::createEntityManager)
                .orElseThrow();
    }
}

package org.gym;

import org.gym.config.AppConfig;
import org.gym.memory.InMemoryStorage;
import org.gym.memory.StorageInitialization;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, InMemoryStorage.class, StorageInitialization.class);

        InMemoryStorage inMemoryStorage = context.getBean(InMemoryStorage.class);
        StorageInitialization storageInitialization = context.getBean(StorageInitialization.class);

        storageInitialization.initializeStorageWithData();

        System.out.println(inMemoryStorage.getStorageMap().toString());

        context.close();
    }
}

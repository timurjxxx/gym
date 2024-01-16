package org.gym.dao;

import org.gym.memory.InMemoryStorage;
import org.gym.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class UserDAO {
    private final String nameSpace = "User";
    private final InMemoryStorage storage;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);


    @Autowired

    public UserDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public User get(Long userId) {
        return (User) storage.get(nameSpace, userId);
    }

    public User save(User newUser) {
        return (User) storage.save(nameSpace, newUser.getId(), newUser);
    }

    public void delete(Long userId) {
        storage.delete(nameSpace, userId);
    }


    public Map<Long, Object> getAll() {
        return storage.getAll(nameSpace);

    }

    public String findByUsername(String username) {

        LOGGER.info("Find user by uer name");
        for (Map<Long, Object> innerMap : storage.getStorageMap().values()) {
            for (Object obj : innerMap.values()) {
                if (obj instanceof User) {
                    User user = (User) obj;
                    String userUsername = user.getUserName();
                    if (userUsername != null && userUsername.equals(username)) {
                        return username = userUsername;
                    }
                }
            }
        }
        return null;
    }


}
package org.gym.dao;

import org.gym.memory.InMemoryStorage;
import org.gym.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class UserDAO {
    private final InMemoryStorage storage;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);


    @Autowired

    public UserDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public User get(String  nameSpace,Long userId) {
        return (User) storage.get(nameSpace, userId);
    }

    public User save(String  nameSpace,User newUser) {

        return (User) storage.save(nameSpace, newUser);
    }

    public void delete(String  nameSpace,Long id) {
        storage.deleteById(nameSpace, id);
    }

    public Optional<User> findByUserName(String  nameSpace,String userName) {
        List<User> userList = storage.getAll(nameSpace)
                .stream()
                .filter(obj -> obj instanceof User)
                .map(obj -> (User) obj)
                .filter(user -> userName.equals(user.getUserName()))
                .collect(Collectors.toList());

        if (!userList.isEmpty()) {
            LOGGER.debug("User with username '{}' found in collection {}", userName, nameSpace);

            return Optional.of(userList.get(0));
        } else {
            LOGGER.debug("User with username '{}' not found in collection {}", userName, nameSpace);
            return Optional.empty();
        }
    }

    public User update(String  nameSpace,Long id, User newUser) {
        return (User) storage.update(nameSpace, id, newUser);
    }



}
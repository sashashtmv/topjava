package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    {
        save(new User(1, "User", "user@gmail.com", "password", Role.ROLE_USER));
        save(new User(2, "User", "user@gmail.com", "password", Role.ROLE_ADMIN));
    }

    @Override
    public void delete(int id) {
        log.info("delete {}", id);
        if(repository.containsKey(id)){
            repository.remove(id);
        }
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if(user.isNew()){
            user.setId(counter.incrementAndGet());

            repository.put(user.getId(),user);
        }
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public Collection<User> getAll() {
        log.info("getAll");
        return repository.values();
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);

        return getAll().stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }
}

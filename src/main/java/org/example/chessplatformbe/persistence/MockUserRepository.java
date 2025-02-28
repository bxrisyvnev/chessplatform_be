package org.example.chessplatformbe.persistence;

import org.example.chessplatformbe.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class MockUserRepository implements IUserRepository {
    private final List<User> users = new ArrayList<>();
    private Long idCounter = 1L;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public Optional<User> findById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idCounter++);
        } else {
            deleteById(user.getId()); // Remove existing user before saving updated data
        }
        users.add(user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}

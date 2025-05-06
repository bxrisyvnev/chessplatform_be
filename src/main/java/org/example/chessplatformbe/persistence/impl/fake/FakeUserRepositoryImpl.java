package org.example.chessplatformbe.persistence.impl.fake;

import org.example.chessplatformbe.domain.Admin;
import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.example.chessplatformbe.persistence.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeUserRepositoryImpl implements UserRepository {

    private static Integer nextId = 1;
    private final List<User> savedUsers;

    public FakeUserRepositoryImpl() {
        this.savedUsers = new ArrayList<User>();
        //add users to list
    }

    @Override
    public Optional<User> findById(Integer id) throws InvalidUserException {
        return this.savedUsers.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public User save(User user) {
        user.setId(nextId++);
        this.savedUsers.add(user);
        return user;
    }

    @Override
    public void delete(User user) {
        this.savedUsers.remove(user);
    }
}

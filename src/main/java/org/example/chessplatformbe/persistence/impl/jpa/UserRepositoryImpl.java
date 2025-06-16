package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.domain.User;
import org.example.chessplatformbe.exceptions.InvalidUserException;
import org.example.chessplatformbe.mapper.UserMapper;
import org.example.chessplatformbe.persistence.UserRepository;
import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUser jpaUser;

    @Autowired
    public UserRepositoryImpl(JpaUser jpaUser) {
        this.jpaUser = jpaUser;
    }


    @Override
    public Optional<User> findById(Integer id){
        Optional<UserEntity> entityOptional = this.jpaUser.findById(id);

        if (entityOptional.isEmpty()) {
            throw  new IllegalArgumentException(String.valueOf(id));
        }

        User country = UserMapper.entityToObject(entityOptional.get());
        return Optional.of(country);
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.objectToEntity(user);
        UserEntity newEntity = this.jpaUser.save(entity);
        return UserMapper.entityToObject(newEntity);
    }

    @Override
    public void delete(User user) {
        UserEntity entity = UserMapper.objectToEntity(user);
        this.jpaUser.delete(entity);
    }

    @Override
    public User findByUsername(String username) throws InvalidUserException {
        UserEntity userEntity = jpaUser.findByUsername(username);
        if (userEntity == null) {
            throw new InvalidUserException(username);
        }
        return UserMapper.entityToObject(userEntity);
    }

    @Override
    public Double getAverageCommentsByUser(Integer userId) {
        return jpaUser.findAverageCommentsByUser(userId);
    }
}

package org.example.chessplatformbe.persistence.impl.jpa;

import org.example.chessplatformbe.domain.Stream;
import org.example.chessplatformbe.mapper.StreamMapper;
import org.example.chessplatformbe.persistence.StreamRepository;
import org.example.chessplatformbe.persistence.impl.jpa.entity.StreamEntity;
import org.example.chessplatformbe.persistence.impl.jpa.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StreamRepositoryImpl implements StreamRepository {


    private final JpaStream jpaStream;
    private final JpaUser jpaUser;

    @Autowired
    public StreamRepositoryImpl(JpaStream jpaStream, JpaUser jpaUser) {
        this.jpaStream = jpaStream;
        this.jpaUser = jpaUser;
    }

    @Override
    public Optional<Stream> findById(Integer id) {
        Optional<StreamEntity> entityOptional = this.jpaStream.findById(id);

        if (entityOptional.isEmpty()) {
            throw  new IllegalArgumentException(String.valueOf(id));
        }

        Stream stream = StreamMapper.entityToObject(entityOptional.get());
        return Optional.of(stream);
    }

    @Override
    public Stream save(Stream stream) {
        UserEntity userEntity = jpaUser.findById(stream.getStreamerId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + stream.getStreamerId() + " not found"));

        StreamEntity streamEntity = StreamMapper.objectToEntity(stream, userEntity);
        StreamEntity saved = jpaStream.save(streamEntity);
        return StreamMapper.entityToObject(saved);
    }

    @Override
    public void delete(Stream stream) {
        jpaStream.deleteById(stream.getId());
    }

    @Override
    public Page<Stream> getStreamPage(Pageable pageable) {
        Page<StreamEntity> entityPage = jpaStream.findAll(pageable);

        List<Stream> streams = entityPage.getContent()
                .stream()
                .map(StreamMapper::entityToObject)
                .toList();

        return new PageImpl<>(streams, pageable, entityPage.getTotalElements());
    }
}

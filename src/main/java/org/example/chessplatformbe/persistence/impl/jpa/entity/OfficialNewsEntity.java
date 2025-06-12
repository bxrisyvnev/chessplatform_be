package org.example.chessplatformbe.persistence.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "officialnews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficialNewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String link;

    private LocalDateTime publishedDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String author;
}

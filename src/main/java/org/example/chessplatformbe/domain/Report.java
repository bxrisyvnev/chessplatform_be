package org.example.chessplatformbe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private Integer id;

    private String description;

    private String type;

    private LocalDateTime dateTime;

    private Integer userId;
}

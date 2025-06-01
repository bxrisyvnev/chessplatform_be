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
public class Stream {

    private Integer id;

    private String name;

    private LocalDateTime creationDateTime;

    private String streamUrl;

    private Integer streamerId;
}

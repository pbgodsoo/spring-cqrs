package com.example.board_query.api.model;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class BoardEvent {

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    @Builder
    public static class Create{
        private String uuid;
        private String eventType;
        private LocalDateTime issueAt;
        private Long idx;
        private String title;
        private String contents;

        public static Create from(Board entity) {
            return Create.builder()
                    .uuid(UUID.randomUUID().toString())
                    .eventType("CREATE")
                    .issueAt(LocalDateTime.now())
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .build();
        }

        public Board toEntity(){
            return Board.builder()
                    .idx(this.idx)
                    .title(this.title)
                    .contents(this.contents)
                    .build();
        }
    }
}
package com.example.board_command.api.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class BoardEvent {
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
    }
}
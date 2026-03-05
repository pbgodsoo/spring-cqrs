package com.example.board_query.api;

import com.example.board_query.api.model.Board;
import com.example.board_query.api.model.BoardDto;
import com.example.board_query.api.model.BoardEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    // 카프카로부터 메시지를 받아서 DB에 데이터 저장
    @KafkaListener(topics = "board", groupId = "board-group-1",
            // 내가 받는 Dto의 타입이 어떤 타입인지 지정
            properties = "spring.json.value.default.type:com.example.board_query.api.model.BoardEvent.Create")
    public void consume(
            @Header(KafkaHeaders.RECEIVED_KEY) Long key,
            @Payload BoardEvent.Create event
    ) {
        log.debug("MessageConsumer - consume : {}={}", key, event.toString());

        boardRepository.save(event.toEntity());
    }


    public BoardDto.PageRes list(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        // 페이징 처리 ⭕, 페이지 번호가 필요하다 => Page 반환
        // 페이징 처리 ⭕, 페이지 번호가 필요없다. => Slice 반환
        Page<Board> result = boardRepository.findAll(pageRequest);

        return BoardDto.PageRes.from(result);
    }

    public BoardDto.ReadRes read(Long idx) {
        Board board = boardRepository.findById(idx).orElseThrow();
        return BoardDto.ReadRes.from(board);
    }
}
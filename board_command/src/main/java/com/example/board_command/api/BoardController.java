package com.example.board_command.api;

import com.example.board_command.api.model.BoardDto;
import com.example.board_core.common.model.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RequestMapping("/board")
@RestController
@RequiredArgsConstructor
@Tag(name="게시판 기능")
public class BoardController {
    private final BoardService boardService;

    @Operation(summary = "게시글 등록", description = "제목, 내용을 입력해서 게시글을 작성하는 기능")
    @PostMapping("/reg")
    public ResponseEntity register(
            @RequestHeader(name="X-User-Idx") Long userIdx,
            @RequestHeader(name="X-User-Name") String userName,
            @RequestBody BoardDto.RegReq dto) {

        BoardDto.RegRes result = boardService.register(userIdx, userName, dto);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @PutMapping("/update/{idx}")
    public ResponseEntity update(@PathVariable Long idx, @RequestBody BoardDto.RegReq dto) {
        BoardDto.RegRes result = boardService.update(idx, dto);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity update(@PathVariable Long idx) {
        boardService.delete(idx);
        return ResponseEntity.ok(BaseResponse.success("성공"));
    }
}
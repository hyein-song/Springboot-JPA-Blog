package com.cos.blog.service;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.backoff.BackOff;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void 글쓰기(Board board, User user){
        board.setUser(user);
        board.setCount(0);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id){
        return boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
        });
    }

    @Transactional
    public void 글삭제하기(int id){
         boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard){
        //수정하려면 영속화를 시켜야됨
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
        }); // 영속화 완료

        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수 종료 시 (Service가 종료될떼) 트랜잭션 종료된다. 이때 더티체킹이되서 자동업데이트가 됨 flush

    }

    @Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto){
//        User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
//            return new IllegalArgumentException("댓글 쓰기 실패 : 유저 아이디를 찾을 수 없습니다.");
//        }); // 영속화 완료
//
//        Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
//            return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 아이디를 찾을 수 없습니다.");
//        }); // 영속화 완료

        // 방법1
//        Reply reply = Reply.builder()
//                .user(user)
//                .content(replySaveRequestDto.getContent())
//                .build();

        // 방법2
//        Reply reply = new Reply();
//        reply.update(user,board,replySaveRequestDto.getContent());

//        replyRepository.save(reply);

        //ReplyRepository에 함수를 만들었을 때
        replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());

    }

    @Transactional
    public void 댓글삭제(int replyId){
        replyRepository.deleteById(replyId);
    }

}

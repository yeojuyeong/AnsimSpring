package com.ansim.controller;

import com.ansim.dto.BoardDTO;
import com.ansim.service.BoardService;
import com.ansim.util.Page;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.logging.Logger;

@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
public class BoardController {

    // 의존성 주입
    private final BoardService service;
    private static final Logger LOGGER = Logger.getLogger(BoardController.class.getName());

    // 안심 동행 게시물 페이지
    @GetMapping("/restapi/list")
    public Map<String, Object> getList(@RequestParam("page") int pageNum,
                        @RequestParam(name = "keyword", defaultValue = "", required = false) String keyword,
                                       @RequestParam("user_id") String user_id) throws Exception {

//        System.out.println("yourMethod started");

        int postNum = 6; // 한 화면에 보여지는 게시물 행의 갯수
        int startPoint = (pageNum - 1) * postNum + 1; //페이지 시작 게시물 번호
        int endPoint = pageNum * postNum;
        int pageListCount = 10; // 화면 하단에 보여지는 페이지리스트의 페이지 갯수
        int totalCount = service.findGetTotalCount(keyword); // 전체 게시물 갯수

        Page page = new Page();

        Map<String, Object> response = new HashMap<>();
        response.put("role", service.findRole(user_id));
        response.put("list", service.findList(startPoint, endPoint, keyword));
        response.put("page", pageNum);
        response.put("keyword", keyword);
        response.put("pageList", page.getPageList(pageNum, postNum, pageListCount, totalCount, keyword));
        //        response.put("totalElement", service.findGetTotalCount(keyword)); 이게 뭐에 필요한 거였더라?


        return response;

    }

    //게시물 등록 화면 보기 (user_id 받아와서, 출발지, 목적지는 화면에 값이 이미 나와있게 되도록 추후에 추가 예정)
    @GetMapping("/restapi/write")
    public Map<String, String> getWriteDetails(@RequestParam("user_id") String user_id) throws Exception {

        Map<String, String> response = new HashMap<>();
        response.put("role", service.findRole(user_id));
        response.put("stored_file_nm", service.findFile(user_id));

        return response;

//        // 출발지, 목적지 가져오기 추후에 추가 예정
//        model.addAttribute("points", service.findWrite(user_id));
    }

    //첨부파일 없는 게시물 등록 하기
//    @ResponseBody 리액트는 기본적으로 json으로 받아서 이게 필요가 없나...? 아님 잭슨 관련해서 뭔가 이슈가 있나? 나중에 찾아서 공부해보기.
    @PostMapping("/restapi/write")
    public String postWriteAdd(BoardDTO board) throws Exception {
        System.out.println(board.getMem_cnt());
        service.addWrite(board);
        return "{\"message\":\"GOOD\"}";
    }

    //게시물 내용 보기
    @GetMapping("/restapi/view")
    public Map<String, Object> getViewDetails(@RequestParam("seqno") int seqno, @RequestParam("page") int pageNum,
                               @RequestParam(name = "keyword", defaultValue = "", required = false) String keyword,
                                              @RequestParam("user_id") String user_id
                                ) throws Exception {

        // 클라이언트에 있는 user_id 쿠키를 리액트에서 받기 때문에 굳이 컨트롤러에서 넣어줄 필요 없음.

        Map<String, Object> response = new HashMap<>();
        response.put("cookie_stored_file_nm", service.findFile(user_id));
        response.put("role", service.findRole(user_id));
        response.put("view", service.findView(seqno));
        response.put("applicant_list", service.findApplicantList(seqno));
        response.put("page", pageNum);
        response.put("keyword", keyword);
        response.put("pre_seqno", service.findPre_seqno(seqno, keyword));
        response.put("next_seqno", service.findNext_seqno(seqno, keyword));

        System.out.println(service.findApplicantList(seqno));

        // 조회수 증가 (String 비교할때는 equals 사용)
        if (!user_id.equals(service.findView(seqno).get("user_id"))) {
            service.modifyHitno(seqno);
        }

        return response;
    }

    // 동행 신청
    @PostMapping("/restapi/view")
    public String postViewAddDetails(@RequestParam("post_no") int post_no,
                                     @RequestParam("applicant") String applicant,
                                     @RequestParam("writer") String writer) {

        try {
            service.addApplication(post_no, applicant, writer);
            return "{\"message\":\"GOOD\"}";
        } catch (DataIntegrityViolationException ex) {
            return "{\"message\":\"EXISTED\"}";
        }
    }

    // 동행 신청 수락
    @PostMapping("/restapi/accept")
    public String postAcceptModify(@RequestParam("post_no") int post_no,
                                   @RequestParam("applicant") String applicant,
                                     @RequestParam("writer") String writer) throws Exception {

        if ("Y".equals(service.findAccepted(post_no, applicant, writer))) {
            return "{\"message\":\"CLICKED\"}";
        } else {
            service.modifyAccept(post_no, applicant, writer);
            service.modifyAnsim_cnt_A(applicant);
            service.modifyAnsim_cnt_W(writer);
            return "{\"message\":\"GOOD\"}";
        }
    }

    // 동행 신청 거절
    @PostMapping("/restapi/deny")
    public String postDenyModify(@RequestParam("post_no") int post_no,
                                 @RequestParam("applicant") String applicant,
                                 @RequestParam("writer") String writer) throws Exception {

        if ("N".equals(service.findAccepted(post_no, applicant, writer))) {
            return "{\"message\":\"CLICKED\"}";
        } else {
            service.modifyDeny(post_no, applicant, writer);
            return "{\"message\":\"GOOD\"}";
        }
    }

    // 게시물 삭제하기
    @Transactional
    @GetMapping("/restapi/delete")
    public String getDelete(@RequestParam("seqno")int seqno) throws Exception {
        // transaction 시작
        service.removeBoard(seqno); // 게시물 행 삭제
        // transaction 끝
        return "{\"message\":\"GOOD\"}";
    }

    // 게시물 수정 페이지 보기

    @GetMapping("/restapi/modify")
    public Map<String, Object> getModify(@RequestParam("seqno") int seqno, @RequestParam("page") int pageNum,
                          @RequestParam(name="keyword", defaultValue="", required=false) String keyword,
                                         @RequestParam("user_id") String user_id
                          ) throws Exception {


        Map<String, Object> response = new HashMap<>();
        response.put("cookie_stored_file_nm", service.findFile(user_id));
        response.put("role", service.findRole(user_id));
        response.put("view", service.findView(seqno));
        response.put("page", pageNum);
        response.put("keyword", keyword);

        return response;
    }

    // 게시물 수정 하기
//    @ResponseBody
    // @Transactional // 작동이 안되면(에러 발생 시) 원복 시켜주는 주석!!
    @ResponseBody
    @PostMapping("/restapi/modify")
    public String postModify( BoardDTO board, @RequestParam(name="page") int pageNum,
                             @RequestParam(name="keyword", defaultValue="", required=false) String keyword) throws Exception{

        System.out.println("postModify in!");

//        LOGGER.info("postModify in!");
//        LOGGER.info("Received board: " + board);
//        LOGGER.info("Received board: " + board.getSeqno());
//        LOGGER.info("Received board: " + board.getTitle());
//        LOGGER.info("Received board: " + board.getDeparture());
//        LOGGER.info("Received board: " + board.getDestination());
//        LOGGER.info("Received board: " + board.getMeeting_time());
//        LOGGER.info("Received board: " + board.getMem_cnt());
//        LOGGER.info("Received board: " + board.getGender());
//        LOGGER.info("Received board: " + board.getContent());

        System.out.println(board);
        System.out.println(board.getSeqno());
        System.out.println(board.getTitle());
        System.out.println(board.getDeparture());
        System.out.println(board.getDestination());
        System.out.println(board.getMeeting_time());
        System.out.println(board.getMem_cnt());
        System.out.println(board.getGender());
        System.out.println(board.getSound());
        System.out.println(board.getContent());

        service.modifyBoard(board);

        LOGGER.info("Received board: " + board);
        LOGGER.info("Received board: " + board.getSeqno());
        LOGGER.info("Received board: " + board.getTitle());
        LOGGER.info("Received board: " + board.getDeparture());
        LOGGER.info("Received board: " + board.getDestination());
        LOGGER.info("Received board: " + board.getMeeting_time());
        LOGGER.info("Received board: " + board.getMem_cnt());
        LOGGER.info("Received board: " + board.getGender());
        LOGGER.info("Received board: " + board.getContent());

        System.out.println(board);
        System.out.println(board.getSeqno());
        System.out.println(board.getTitle());
        System.out.println(board.getDeparture());
        System.out.println(board.getDestination());
        System.out.println(board.getMeeting_time());
        System.out.println(board.getMem_cnt());
        System.out.println(board.getGender());
        System.out.println(board.getSound());
        System.out.println(board.getContent());


        return "{\"message\":\"GOOD\"}";

    }

}

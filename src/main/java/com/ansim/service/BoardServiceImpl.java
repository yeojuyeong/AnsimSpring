package com.ansim.service;

import com.ansim.dto.BoardDTO;
import com.ansim.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardMapper mapper;

    // role 구하기
    @Override
    public String findRole(String user_id) throws Exception {
        return mapper.selectRole(user_id);
    }

    // 안심 동행 게시물 목록 보기
    @Override
    public List<Map<String, Object>> findList(int startPoint, int endPoint, String keyword) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("startPoint", startPoint);
        data.put("endPoint", endPoint);
        data.put("keyword", keyword);
        return mapper.selectList(data);
    }

    // 전체 안심 동행 게시물 갯수 계산
    @Override
    public int findGetTotalCount(String keyword) throws Exception {
        return mapper.selectGetTotalCount(keyword);
    }

//    // 위도, 경도로부터 주소를 얻는 메서드
//        @Override
//    public String findGetAddress(double latitude, double longitude) throws Exception {
//
//        String url = "https://api2.sktelecom.com/tmap/geo/reversegeocoding?version=1&lat=" + latitude + "&lon=" + longitude + "&appKey=" + "QuIlY0cSaw4hpu6Azq3vt2AsnCESGrtx447GZ90D";
//
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(url, String.class);
//
//        // JSON 응답에서 주소 부분을 파싱합니다.
//        // 이 부분은 실제 응답 형식에 따라 수정해야 합니다.
//
//        return result;
//    }

    //게시물 조회수 증가
    @Override
    public void modifyHitno(int seqno)throws Exception {
        mapper.updateHitno(seqno);
    }

    // 출발지 목적지 표기
    @Override
    public List<BoardDTO> findWrite(String user_id) throws Exception {
        return mapper.selectWrite(user_id);
    }

    // 프로필 사진 여부 확인
    @Override
    public String findFile(String user_id) throws Exception {
        return mapper.selectFile(user_id);
    }

    // 게시물 등록하기
    @Override
    public void addWrite(BoardDTO board) throws Exception {
        mapper.insertWrite(board);
    }

    // 게시물 내용 보기
    @Override
    public Map<String, Object> findView(int seqno) throws Exception {
        return mapper.selectView(seqno);
    }

    //동행 신청 리스트
    @Override
    public List<Map<String, Object>> findApplicantList(int seqno) throws Exception {
        return mapper.selectApplicantList(seqno);
    }

    // 게시물 내용 이전보기
    @Override
    public int findPre_seqno(int seqno, String keyword) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("seqno", seqno);
        data.put("keyword", keyword);
        return mapper.selectPre_seqno(data);
    }
    // 게시물 내용 다음보기
    @Override
    public int findNext_seqno(int seqno, String keyword) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("seqno", seqno);
        data.put("keyword", keyword);
        return mapper.selectNext_seqno(data);
    }

    // 동행 신청
    @Override
    public void addApplication(int post_no, String applicant, String writer) {
        Map<String, Object> data = new HashMap<>();
        data.put("post_no", post_no);
        data.put("applicant", applicant);
        data.put("writer", writer);
        mapper.insertApplication(data);
    }

    // 수락 여부 확인
    @Override
    public String findAccepted(int post_no, String applicant, String writer) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("post_no", post_no);
        data.put("applicant", applicant);
        data.put("writer", writer);

        return mapper.selectAccepted(data);
    }

    // 동행 신청 수락
    @Override
    public void modifyAccept(int post_no, String applicant, String writer) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("post_no", post_no);
        data.put("applicant", applicant);
        data.put("writer", writer);

        mapper.updateAccept(data);
    }

    // 동행 신청 수락 : 신청자 동행 포인트 + 1
    @Override
    public void modifyAnsim_cnt_A(String applicant) throws Exception {
        mapper.updateAnsim_cnt_A(applicant);
    }

    // 동행 신청 수락 : 작성자 동행 포인트 + 1
    @Override
    public void modifyAnsim_cnt_W(String writer) throws Exception {
        mapper.updateAnsim_cnt_W(writer);
    }

    // 동행 신청 거부
    @Override
    public void modifyDeny(int post_no, String applicant, String writer) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("post_no", post_no);
        data.put("applicant", applicant);
        data.put("writer", writer);

        mapper.updateDeny(data);
    }

    //게시물 수정
    @Override
    public void modifyBoard(BoardDTO board)throws Exception {
        mapper.updateBoard(board);
    }

    //게시물 삭제하기
    @Override
    public void removeBoard(int seqno) throws Exception {
        mapper.deleteBoard(seqno);
    }

}

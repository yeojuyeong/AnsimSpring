package com.ansim.service;

import com.ansim.dto.BoardDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface BoardService {

    // role 구하기
    public String findRole(String user_id) throws Exception;

    // 안심 동행 게시물 목록 보기
    public List<Map<String, Object>> findList(int startPoint, int endPoint, String keyword) throws Exception;

    // 전체 안심 동행 게시물 갯수 계산
    public int findGetTotalCount(String keyword) throws Exception;

//    // 위도, 경도로부터 주소를 얻는 메서드
//    public String findGetAddress(double latitude, double longitude) throws Exception;

    //게시물 조회수 증가
    public void modifyHitno(int seqno)throws Exception;

    // 출발지 목적지 표기
    public List<BoardDTO> findWrite(String user_id) throws Exception;

    // 프로필 사진 여부 확인
    public String findFile(String user_id) throws Exception;

    //게시물 등록 하기
    public void addWrite(BoardDTO board)throws Exception;

    //게시물 상세 내용 보기
    public Map<String, Object> findView(int seqno)throws Exception;

    //동행 신청 리스트
    public List<Map<String, Object>> findApplicantList(int seqno)throws Exception;

    //게시물 내용 이전 보기
    public int findPre_seqno(int seqno, String keyword)throws Exception;

    //게시물 내용 다음 보기
    public int findNext_seqno(int seqno, String keyword)throws Exception;

    // 동행 신청
    public void addApplication(int post_no, String applicant, String writer);

    // 수락 여부 확인
    public String findAccepted(int post_no, String applicant, String writer) throws Exception;

    // 동행 신청 수락
    public void modifyAccept(int post_no, String applicant, String writer) throws Exception;

    // 동행 신청 수락 : 신청자 동행 포인트 + 1
    public void modifyAnsim_cnt_A(String applicant) throws Exception;

    // 동행 신청 수락 : 작성자 동행 포인트 + 1
    public void modifyAnsim_cnt_W(String writer) throws Exception;

    // 동행 신청 거부
    public void modifyDeny(int post_no, String applicant, String writer) throws Exception;

    //게시물 수정
    public void modifyBoard(BoardDTO board)throws Exception;

    //게시물 삭제하기
    public void removeBoard(int seqno)throws Exception;

}

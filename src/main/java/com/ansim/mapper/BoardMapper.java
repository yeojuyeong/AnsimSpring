package com.ansim.mapper;

import com.ansim.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    // role 구하기
    public String selectRole(String user_id) throws Exception;

    // 안심 동행 게시물 목록 보기
    public List<Map<String, Object>> selectList(Map<String, Object> data) throws Exception;

    // 전체 안심 동행 게시물 갯수 계산
    public int selectGetTotalCount(String keyword) throws Exception;

    //게시물 조회수 증가
    public void updateHitno(int seqno)throws Exception;

    // 출발지 목적지 표기
    public List<BoardDTO> selectWrite(String user_id) throws Exception;

    // 프로필 사진 여부 확인
    public String selectFile(String user_id) throws Exception;

    //게시물 등록 하기
    public void insertWrite(BoardDTO board)throws Exception;

    //게시물 상세 내용 보기
    public Map<String, Object> selectView(int seqno)throws Exception;

    //동행 신청 리스트
    public List<Map<String, Object>> selectApplicantList(int seqno)throws Exception;

    //게시물 내용 이전 보기
    public int selectPre_seqno(Map<String, Object> data)throws Exception;

    //게시물 내용 다음 보기
    public int selectNext_seqno(Map<String, Object> data)throws Exception;

    // 동행 신청
    public void insertApplication(Map<String, Object> data);

    // 수락 여부 확인
    public String selectAccepted(Map<String, Object> data) throws Exception;

    // 동행 신청 수락
    public void updateAccept(Map<String, Object> data) throws Exception;

    // 동행 신청 수락 : 신청자 동행 포인트 + 1
    public void updateAnsim_cnt_A(String applicant) throws Exception;

    // 동행 신청 수락 : 작성자 동행 포인트 + 1
    public void updateAnsim_cnt_W(String writer) throws Exception;

    // 동행 신청 거부
    public void updateDeny(Map<String, Object> data) throws Exception;

    //게시물 수정
    public void updateBoard(BoardDTO board)throws Exception;

    //게시물 삭제하기
    public void deleteBoard(int seqno)throws Exception;

}

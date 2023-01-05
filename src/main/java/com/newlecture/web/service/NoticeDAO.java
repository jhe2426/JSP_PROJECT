package com.newlecture.web.service;

import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;

public interface NoticeDAO {
	
	//게시글 공개, 비공개
	abstract public int pubNoticeAll(int[] oids, int[] cids);
	abstract public int pubNoticeAll(List<String> oids, List<String> cids);
	abstract public int pubNoticeAll(String oidsCSV, String cidsCSV);
	
	//게시판 글쓰기 정보 입력
	abstract public int insertNotice(Notice notice);

	
	//게시판 전체 목록
	abstract public List<NoticeView> getNoticeList();	
	abstract public List<NoticeView> getNoticeList(int page);
	abstract public List<NoticeView> getNoticeList(String field, String query ,int page);	
	abstract public List<NoticeView> getNoticePubList(String field, String query, int page);
	//게시판 갯수
	abstract public int getNoticeCount();
	abstract public int getNoticeCount(String field, String query);
	
	//현재 게시물 페이지
	abstract public Notice getNotice(int id);
	
	//다음 게시물 페이지
	abstract public Notice getNextNotice(int id);
	
	//이전 게시물 페이지
	abstract public Notice getPrevNotice(int id);
	
	//게시물 삭제
	abstract public int deleteNoticeAll(int[] ids);
	
}

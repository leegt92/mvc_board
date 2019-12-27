package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;
import edu.bit.dto.BDto;

public class BContentCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//해당 번호에 있는 게시판에있는 글 가져와야함 넘어올때 키벨류형태로 넘어옴
		//글번호 먼저 가져와야함
		
		String bId = request.getParameter("bId");
		//<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}
		//에서 bId를 받아야 함.
		
		//{content_view.bHit}
		String bHit = request.getParameter("bHit");
		
		BDao dao = new BDao();
		BDto dto = dao.contentView(bId);
		
		//BDto객체 = 게시판에 대한 한개의 로
		request.setAttribute("content_view", dto);
	}

}

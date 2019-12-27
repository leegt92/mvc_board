package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;
import edu.bit.dto.BDto;

public class BModifyCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//아이디 이름 타이틀 콘텐츠
		//겟파라미터할때 받아오는곳은 content_view임.
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		BDao dao = new BDao();
		//4개 dao에 넘겨줌
		dao.modify(bId, bName, bTitle, bContent);
		//리퀘스트셋어트리뷰트 할 필요 없음. 업뎃만 하고 보여주는것.
	}

}

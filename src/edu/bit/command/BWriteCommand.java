package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;

public class BWriteCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//request 안에는 맨 처음 요청한것들이 들어있다. 박스안에 글 집어넣은것들.
		String bName = request.getParameter("bName");//request.getParameter를 써서 request에서 자료를 뽑아옴
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		BDao dao = new BDao();
		dao.write(bName, bTitle, bContent);//DAO를 써서 request객체에서 받아온 데이터들을 DB에 넣어다.
		//저장이 되면서 BController으로 돌아감
	}

}

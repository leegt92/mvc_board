package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;

public class BDeleteCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String bId = request.getParameter("bId");
		//리퀘스트.겟파람으로 겟방식으로 bId를 가져옴.
		BDao dao = new BDao();
		//DAO생성
		dao.delete(bId);
		//DAO안의 객체의 함수에 위에서 얻은 bId를 넣음.
	}

}

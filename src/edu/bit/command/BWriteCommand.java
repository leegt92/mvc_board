package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;

public class BWriteCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//request �ȿ��� �� ó�� ��û�Ѱ͵��� ����ִ�. �ڽ��ȿ� �� ��������͵�.
		String bName = request.getParameter("bName");//request.getParameter�� �Ἥ request���� �ڷḦ �̾ƿ�
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		BDao dao = new BDao();
		dao.write(bName, bTitle, bContent);//DAO�� �Ἥ request��ü���� �޾ƿ� �����͵��� DB�� �־��.
		//������ �Ǹ鼭 BController���� ���ư�
	}

}

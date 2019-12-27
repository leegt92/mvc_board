package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;

public class BDeleteCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String bId = request.getParameter("bId");
		//������Ʈ.���Ķ����� �ٹ������ bId�� ������.
		BDao dao = new BDao();
		//DAO����
		dao.delete(bId);
		//DAO���� ��ü�� �Լ��� ������ ���� bId�� ����.
	}

}

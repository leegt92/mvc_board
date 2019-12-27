package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;
import edu.bit.dto.BDto;

public class BModifyCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//���̵� �̸� Ÿ��Ʋ ������
		//���Ķ�����Ҷ� �޾ƿ��°��� content_view��.
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		BDao dao = new BDao();
		//4�� dao�� �Ѱ���
		dao.modify(bId, bName, bTitle, bContent);
		//������Ʈ�¾�Ʈ����Ʈ �� �ʿ� ����. ������ �ϰ� �����ִ°�.
	}

}

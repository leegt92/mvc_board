package edu.bit.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;
import edu.bit.dto.BDto;

public class BContentCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//�ش� ��ȣ�� �ִ� �Խ��ǿ��ִ� �� �����;��� �Ѿ�ö� Ű�������·� �Ѿ��
		//�۹�ȣ ���� �����;���
		
		String bId = request.getParameter("bId");
		//<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}
		//���� bId�� �޾ƾ� ��.
		
		//{content_view.bHit}
		String bHit = request.getParameter("bHit");
		
		BDao dao = new BDao();
		BDto dto = dao.contentView(bId);
		
		//BDto��ü = �Խ��ǿ� ���� �Ѱ��� ��
		request.setAttribute("content_view", dto);
	}

}

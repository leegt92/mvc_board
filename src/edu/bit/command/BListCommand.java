package edu.bit.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;
import edu.bit.dto.BDto;

public class BListCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		BDao dao = new BDao();//�����͸� �������� ���� BDao��ü�� �����
		ArrayList<BDto> dtos = dao.list();//�����͸���� �ٺҷ��´�.
		
		//BDto�� ���⼭ �������.
		//Dao�� ���� list�� �����.
		//list���� dao �޾ƿԴ�. ��� ����Ʈ���� �� �޾ƿ°��� ù��°�ּҰ� dtos�� ����.
		request.setAttribute("list", dtos);
		//ArrayList<BDto> dtos = dao.list();������  BFrontController�� �����͸���� �ҷ��°���
		//�����Ҽ��� ����. �׷��� request.setAttribute("list", dtos);�� �ְԵǸ� BFrontController�� ������
		//request��ü���� dtos�� list��� �̸����� ���ְ� �ȴ�.request�� ������ �����ϱ������� �̹Ƿ� ��ü�ȿ� ���� �־ �̾�����.
		//���� viewPage = "list.jsp"�� list�� �̵��ϴµ�, �̶� list.jsp�ȿ��� dtos�� ���� �ȴ�.
		
		//DAO��ü�� ������ DAO���� 3����Ʈ�� ����� ��̸���Ʈ�� ����� ����Ʈ�������� ����Ѵ�.
		//mvc�� �ִ� ��簴ü�� �� �����´�. request.setAttribute("list", dtos); ���⼭ ��̸���Ʈ�� ���� ��ü�� ���� ������Ʈ�ȿ� �ִ´�.
	}

}

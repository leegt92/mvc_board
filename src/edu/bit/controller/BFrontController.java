package edu.bit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.command.BCommand;
import edu.bit.command.BContentCommand;
import edu.bit.command.BDeleteCommand;
import edu.bit.command.BListCommand;
import edu.bit.command.BModifyCommand;
import edu.bit.command.BReplyCommand;
import edu.bit.command.BReplyViewCommand;
import edu.bit.command.BWriteCommand;

/**
 * Servlet implementation class BFrontController
 */
@WebServlet("*.do")//Ȯ��������
public class BFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BFrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		actionDo(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		actionDo(request,response);
	}
	
	protected void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("actionDo");
		
		request.setCharacterEncoding("EUC-KR");//�ѱ��� �Ѿ�ü� �ֱ⿡ �ѱ����ڵ�
		
		String viewPage = null;
		BCommand command = null;//Model���� Command. FrontController�� BCommand�� ��ü�� �����Ѵ�.
								//�������̽� Ŭ���� �����ߴ�.
		
		String uri = request.getRequestURI();//uri ©�󳻴� ���. �����̴�.
		String conPath = request.getContextPath();//contextPath = ��Ĺ�� �����ϴ� ������Ʈ(���)//context�� �����ϴ������� ���Ͽ��� ������ xml�ε� 
		String com = uri.substring(conPath.length());
		
		System.out.println("�׽�Ʈ URI : "+uri+ ":" + conPath+ ":" + com);
		
		//������ �б� �κ�.
		System.out.println("Ȯ��");
		
		if(com.equals("/write_view.do")) {//url�� ������ ġ�� ������ Bcontroller ��ü�� �������� �ΰ��̳� ������Ʈ�� Ÿ��
			viewPage = "write_view.jsp";  //�Ʒ� �б⸦ Ÿ�� view�� ��
		}
		else if(com.equals("/write.do")) {//write_view.do���� �� �ۼ��ϰ� �Է��� ������write.do.�� ����ǹǷ� �̰� �����
			command = new BWriteCommand();//������ ����. ����������. ��ü����. �����̸��̳� ������ٸ���.
			command.execute(request, response);
			
			viewPage = "list.do";//do�̹Ƿ�, *do�� ���� �ٽ� BFrontController�� ���ƿͼ� �ٽ� actionDo�� Ÿ�Ե�.
		}
		
		else if(com.equals("/list.do")) {//write_view.do���� �� �ۼ��ϰ� �Է��� ������write.do.�� ����ǹǷ� �̰� �����
			
			command = new BListCommand();//������ ����. ����������. ��ü����. �����̸��̳� ������ٸ���.
			command.execute(request, response);
			System.out.println("list.do����");
			viewPage = "list.jsp";//���� �ۼ�������, �� ���� ���Ե� �Խ��Ǳ۸���� ���������.
			
			//url�� list.do�� ġ������� Ŀ�ǵ尴ü�� ���� ���� ��Ų��. BListCommand�� ���� ����������+Ư���� ���� ��Ų��.  execute�� ���� ������Ʈ�����ݽ��� �ѱ��.
			//������Ʈ�� ��� ��̸���Ʈ�� ���� �����͵��� ���ִ�.
		}
		else if(com.equals("/content_view.do")) {
			command = new BContentCommand();
			command.execute(request, response);
			
			viewPage = "content_view.jsp";	
		}
		
		else if(com.equals("/modify.do")) {
			
			command = new BModifyCommand();
			command.execute(request, response);
			
			viewPage = "list.do";//do�� Ÿ���� �����Ȱͱ��� �����.
			
		}
		else if(com.equals("/delete.do")) {
			System.out.println("delete.doſ��");
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do";//do�� Ÿ���� �����Ȱͱ��� �����.
			
		}
		else if(com.equals("/reply_view.do")) {
			
			command = new BReplyViewCommand();
			command.execute(request, response);
			
			viewPage = "reply_view.jsp";//����� �����ִ� jsp����
			
		}
		else if(com.equals("/reply.do")) {
			
			command = new BReplyCommand();
			command.execute(request, response);
			
			viewPage = "list.do";//����� �����ִ� jsp����
			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		//���� ��û�� �״�� Ư�� jsp�� �ѱ��.
		dispatcher.forward(request, response);//������ ���������� viewPage�� ������ ���̰� �Ѵ�. ����.
		
		//list.do�� viewPage�� ����, do �̹Ƿ� �ٽ� �Ϸ� �ͼ� �ٽ� ���� Ÿ�Եǰ�, �� ��� else if(com.equals("/list.do")���� �ɷ���
		//viewPage = "list.do";�� ����ȴ�.
		
		//����ó�� ���� ������Ʈ��ü �� ��ü�� �ѱ��. �������̹Ƿ� ������Ʈ��ü�� ���������� �����ü�� viewPage�� �Ѿ��.
		
	}
}

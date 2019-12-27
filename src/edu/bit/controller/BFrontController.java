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
@WebServlet("*.do")//확장자패턴
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
		
		request.setCharacterEncoding("EUC-KR");//한글이 넘어올수 있기에 한글인코딩
		
		String viewPage = null;
		BCommand command = null;//Model쪽의 Command. FrontController가 BCommand의 객체를 생성한다.
								//인터페이스 클래스 생성했다.
		
		String uri = request.getRequestURI();//uri 짤라내는 방법. 공식이다.
		String conPath = request.getContextPath();//contextPath = 톰캣이 관리하는 프로젝트(경로)//context는 관리하는파일은 톰켓에서 서버의 xml인데 
		String com = uri.substring(conPath.length());
		
		System.out.println("테스트 URI : "+uri+ ":" + conPath+ ":" + com);
		
		//페이지 분기 부분.
		System.out.println("확인");
		
		if(com.equals("/write_view.do")) {//url에 저렇게 치고 들어오면 Bcontroller 객체가 생성된후 두겟이나 두포스트를 타고
			viewPage = "write_view.jsp";  //아래 분기를 타서 view에 들어감
		}
		else if(com.equals("/write.do")) {//write_view.do에서 글 작성하고 입력을 누르면write.do.가 실행되므로 이게 실행됨
			command = new BWriteCommand();//다형성 적용. 폴리몰피즘. 객체생성. 같은이름이나 기능은다르다.
			command.execute(request, response);
			
			viewPage = "list.do";//do이므로, *do에 의해 다시 BFrontController로 돌아와서 다시 actionDo를 타게됨.
		}
		
		else if(com.equals("/list.do")) {//write_view.do에서 글 작성하고 입력을 누르면write.do.가 실행되므로 이게 실행됨
			
			command = new BListCommand();//다형성 적용. 폴리몰피즘. 객체생성. 같은이름이나 기능은다르다.
			command.execute(request, response);
			System.out.println("list.do탔음");
			viewPage = "list.jsp";//글이 작성됫으니, 그 글이 포함된 게시판글목록을 보여줘야함.
			
			//url을 list.do를 치고들어오면 커맨드객체를 통해 일을 시킨다. BListCommand를 통해 다형성적용+특정한 일을 시킨다.  execute를 통해 리퀘스트리스펀스를 넘긴다.
			//리퀘스트에 모든 어레이리스트로 뽑은 데이터들이 들어가있다.
		}
		else if(com.equals("/content_view.do")) {
			command = new BContentCommand();
			command.execute(request, response);
			
			viewPage = "content_view.jsp";	
		}
		
		else if(com.equals("/modify.do")) {
			
			command = new BModifyCommand();
			command.execute(request, response);
			
			viewPage = "list.do";//do를 타야지 수정된것까지 갖고옴.
			
		}
		else if(com.equals("/delete.do")) {
			System.out.println("delete.do탓음");
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do";//do를 타야지 수정된것까지 갖고옴.
			
		}
		else if(com.equals("/reply_view.do")) {
			
			command = new BReplyViewCommand();
			command.execute(request, response);
			
			viewPage = "reply_view.jsp";//답글을 쓸수있는 jsp파일
			
		}
		else if(com.equals("/reply.do")) {
			
			command = new BReplyCommand();
			command.execute(request, response);
			
			viewPage = "list.do";//답글을 쓸수있는 jsp파일
			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		//받은 요청을 그대로 특정 jsp로 넘긴다.
		dispatcher.forward(request, response);//유저의 웹브라우저에 viewPage가 받은걸 보이게 한다. 전달.
		
		//list.do가 viewPage에 오면, do 이므로 다시 일로 와서 다시 여길 타게되고, 그 결과 else if(com.equals("/list.do")에서 걸려서
		//viewPage = "list.do";가 실행된다.
		
		//디스패처를 통해 리퀘스트객체 그 자체를 넘긴다. 포워드이므로 리퀘스트객체의 내부정보는 저장된체로 viewPage로 넘어간다.
		
	}
}

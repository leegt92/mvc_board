package edu.bit.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.bit.dao.BDao;
import edu.bit.dto.BDto;

public class BListCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		BDao dao = new BDao();//데이터를 가져오기 위해 BDao객체를 만들고
		ArrayList<BDto> dtos = dao.list();//데이터목록을 다불러온다.
		
		//BDto를 여기서 만들었다.
		//Dao로 들어가서 list를 만든다.
		//list에서 dao 받아왔다. 모든 리스트들을 다 받아온것의 첫번째주소가 dtos에 들어간다.
		request.setAttribute("list", dtos);
		//ArrayList<BDto> dtos = dao.list();만으론  BFrontController에 데이터목록을 불러온것을
		//전달할수가 없다. 그러나 request.setAttribute("list", dtos);를 넣게되면 BFrontController에 들어갔을때
		//request객체에는 dtos가 list라는 이름으로 들어가있게 된다.request의 범위는 응답하기전까지 이므로 객체안에 값을 넣어도 이어진다.
		//그후 viewPage = "list.jsp"로 list로 이동하는데, 이때 list.jsp안에는 dtos가 들어가게 된다.
		
		//DAO객체를 생성해 DAO에서 3종셋트를 사용해 어레이리스트도 사용해 셀렉트올쿼리를 사용한다.
		//mvc에 있는 모든객체를 다 가져온다. request.setAttribute("list", dtos); 여기서 어레이리스트가 가진 객체를 전부 리퀘스트안에 넣는다.
	}

}

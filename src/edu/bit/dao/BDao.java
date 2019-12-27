package edu.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



import edu.bit.dto.BDto;

public class BDao {
	DataSource dataSource;//커넥션풀. 이 객체를 언제 가져오는게 가장 좋은가? 객체생성될때 바로 생성자에 넣는다.
	
	public BDao() {
		try {
			Context context = new InitialContext();//context는 xml에서 가져왔다. 커넥션풀은 xml에서 설정했기때문.
			//서버에 설정된 context를 메모리에 올리는것. 그결과 메모리가 알아서 커넥션풀을 생성함.
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(String bName, String bTitle, String bContent) {
		//3종셋트 그냥 이렇게치는거임 걍쳐 DB에 데이터넣는것.
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = dataSource.getConnection(); //dataSource.getConnection();에서 데이터를 가져와서 connection에 넣고
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) "
					+ "values (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0 )";
			//mvc_board_seq.nextval는 오라클이 알아서 순번을 정해주는것으로, 이 쿼리문에선 글 번호 이다. 즉 글번호 1 2 3 이런식으로 오라클이 알아서 적어준다.
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn==1) {
				System.out.println("정상");
			}else {
				System.out.println("비정상 처리");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
	}

	public ArrayList<BDto> list() {
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;//값을 가져오는것이므로, 리절트셋.
		
		try {//DB에서 자료 뽑아오는것. 글 목록을 보는것임. list
			connection = dataSource.getConnection();
			
			String query = "select * from mvc_board order by bGroup desc, bStep asc";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				//
				Timestamp bDate = resultSet.getTimestamp("bDate");
				
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit,bGroup, bStep, bIndent);
				dtos.add(dto);//DB에서 뽑아온걸 하나하나 dtos에 넣음. 전부 다 dtos에 들어가면 dtos 리턴.
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return dtos;
	}

	public BDto contentView(String strID) {
		//게시판을 리턴해줘야함 dto 한개가 게시판글한개 따라서 널해줌
		
		upHit(strID);
		
		BDto dto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
			try {//DB에서 자료 뽑아오는것. 글 목록을 보는것임. list
				connection = dataSource.getConnection();
				
				String query = "select * from mvc_board where bId = ?";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, Integer.parseInt(strID));
				resultSet = preparedStatement.executeQuery();
				
				while (resultSet.next()) {
					int bId = resultSet.getInt("bId");
					String bName = resultSet.getString("bName");
					String bTitle = resultSet.getString("bTitle");
					String bContent = resultSet.getString("bContent");
					//
					Timestamp bDate = resultSet.getTimestamp("bDate");
					
					int bHit = resultSet.getInt("bHit");
					int bGroup = resultSet.getInt("bGroup");
					int bStep = resultSet.getInt("bStep");
					int bIndent = resultSet.getInt("bIndent");
					
					dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit,bGroup, bStep, bIndent);
					
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		
		return dto;
		
	}
	
	private void upHit(String bId) {
	//컨텐츠뷰를 눌럿을때 실행됨. 왜냐면 컨텐츠뷰를 눌른다는건 실행 이라는것이므로
	//조회수가 1 들어나야함.
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	
	try {
		connection = dataSource.getConnection();
		String query = "update mvc_board set bHit = bHit+1 where bId =?";
		//쿼리문을써서 데이터에 있는bHit에 +1을 한다. 어떤애를 +1하냐면 가져온 bId를 참고해서.
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, Integer.parseInt(bId));
		//bId를 쿼리문을 가지고 있는 preparedStatement에 넣는다. 이 때 bId를 가져온건 문자형인데 DB에선숫자형이므로
		//여기서 IntegerparseInt(bId)를 써서 문자형이였던 bId를 숫자형으로 바꾼다.
		preparedStatement.executeUpdate();
		//쿼리문 실행하는 명령어. 이 때 실행하는 쿼리문이 select라면 executeQuery이고, delete 나 update 등 수정하는사항이면
		//executeUpdate()이다.
		
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		
	}
		
	}

	public void modify(String bId, String bName, String bTitle, String bContent) {
		
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	
	try {
		//3종셋트 그냥 이렇게치는거임 걍쳐 DB에 데이터넣는것.
		connection = dataSource.getConnection(); //dataSource.getConnection();에서 데이터를 가져와서 connection에 넣고
		String query = "update mvc_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
		//mvc_board_seq.nextval는 오라클이 알아서 순번을 정해주는것으로, 이 쿼리문에선 글 번호 이다. 즉 글번호 1 2 3 이런식으로 오라클이 알아서 적어준다.
		preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, bName);
		preparedStatement.setString(2, bTitle);
		preparedStatement.setString(3, bContent);
		preparedStatement.setInt(4, Integer.parseInt(bId));//문자를 숫자로 바꿧음.
		
		int rn = preparedStatement.executeUpdate();//이그젝트 업데이트임. 셀렉트는 이그젝트쿼리, 업데이트 삭제 등은 업데이트. 라이트에서 끌어오면됨.
		
		if(rn==1) {
			System.out.println("정상");
		}else {
			System.out.println("비정상 처리");
		}
		
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		
	}
	
}

	public void delete(String bId) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connection = dataSource.getConnection();
			String query = "delete from mvc_board where bId=?";
			preparedStatement = connection.prepareStatement(query);
			//데이터베이스에 쿼리문 넣는다.
			preparedStatement.setInt(1, Integer.parseInt(bId));
			//bId가 String인데, 데이터베이스로넘길때 숫자로 바꿔야한다.
			preparedStatement.executeUpdate();
			//preparedStatement.executeQuery(); 쿼리문이 셀렉트라면 이거
			//preparedStatement.executeUpdate(); 쿼리문이 셀렉트 이외의것(업데이트 딜리트 등)이라면 이것.
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
	}

	public BDto reply_view(String bId) {
		
		BDto dto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
			try {//DB에서 자료 뽑아오는것. 글 목록을 보는것임. list
				connection = dataSource.getConnection();
				
				String query = "select * from mvc_board where bId = ?";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, Integer.parseInt(bId));
				resultSet = preparedStatement.executeQuery();
				
				while (resultSet.next()) {
					int number = resultSet.getInt("bId");
					//위의 선언과 똑같아서 임의로 number로 바꿧다. 나머진 컨텐츠뷰랑 거의 똑같음.
					String bName = resultSet.getString("bName");
					String bTitle = resultSet.getString("bTitle");
					String bContent = resultSet.getString("bContent");
					//
					Timestamp bDate = resultSet.getTimestamp("bDate");
					
					int bHit = resultSet.getInt("bHit");
					int bGroup = resultSet.getInt("bGroup");
					int bStep = resultSet.getInt("bStep");
					int bIndent = resultSet.getInt("bIndent");
					
					dto = new BDto(number, bName, bTitle, bContent, bDate, bHit,bGroup, bStep, bIndent);
					
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		
		return dto;
		
	}

	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep,
			String bIndent) {
		
		replyShape(bGroup, bStep);
		//댓글쓸때 댓글들이 최신순으로 숫자가 늘어날것. 그것을 위한 Shape
		//bId는 필요없는데, 왜냐하면 같은 구간(같은 Id)이기때문.
		//댓글들이 이미 달린 글에서 다시 댓글을 달면 최신댓글이 글의 바로 밑으로 올라가기때문에 남아있던 댓글들의 step이 +1이 되어야 한다.
		
		//replyShape(bGroup, bStep); 이 코드 상에서의 bStep은 0이다. 왜냐하면 원본글에 대한 댓글이기때문에 원본글의 것을 가져옴. 원본글은 bGroup = 1, bStep = 0이다.
		//들어온 bStep이 0이니까. 0보다 큰 모든 bStep(댓글들)에 +1들어감.
		
		//reply는 sql에서 insert. 임 따라서 라이트
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			//3종셋트 그냥 이렇게치는거임 걍쳐 DB에 데이터넣는것.
			connection = dataSource.getConnection(); //dataSource.getConnection();에서 데이터를 가져와서 connection에 넣고
			String query = 
			"insert into mvc_board(bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values(mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
			//mvc_board_seq.nextval자동증가. sql이 숫자넣어주는것.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			
			preparedStatement.setInt(4, Integer.parseInt(bGroup));
			preparedStatement.setInt(5, Integer.parseInt(bStep)+1);//답글이 달릴때 아래로 달리므로, 세로 1이 증가함. 따라서 step1증가
			preparedStatement.setInt(6, Integer.parseInt(bIndent)+1);//답글 달릴때 오른쪽으로 한칸 안으로 들어가므로 가로1증가 따라서 Indent1증가
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn==1) {
				System.out.println("정상");
			}else {
				System.out.println("비정상 처리");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
	}

	
	private void replyShape(String bGroup, String bStep) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			//3종셋트 그냥 이렇게치는거임 걍쳐 DB에 데이터넣는것.
			connection = dataSource.getConnection(); //dataSource.getConnection();에서 데이터를 가져와서 connection에 넣고
			String query = 
			"update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
			//같은 그룹 넘버에 스텝이 중간에 끼기 때문에 넣을당시 자기보다 bStep이 큰놈한테 +1해줌
			//replyShape(bGroup, bStep); 이 코드 상에서의 bStep은 0이다. 왜냐하면 원본글에 대한 댓글이기때문에 원본글의 것을 가져옴. 원본글은 bGroup = 1, bStep = 0이다.
			//들어온 bStep이 0이니까. 0보다 큰 모든 bStep(댓글들)에 +1들어감.
			//모든 bStep들이 +1이 된 후 최신으로 들어온 0이었던 댓글이 1이됨.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.parseInt(bGroup));
			preparedStatement.setInt(2, Integer.parseInt(bStep));
			
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn==1) {
				System.out.println("정상");
			}else {
				System.out.println("비정상 처리");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
	}

}

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
	DataSource dataSource;//Ŀ�ؼ�Ǯ. �� ��ü�� ���� �������°� ���� ������? ��ü�����ɶ� �ٷ� �����ڿ� �ִ´�.
	
	public BDao() {
		try {
			Context context = new InitialContext();//context�� xml���� �����Դ�. Ŀ�ؼ�Ǯ�� xml���� �����߱⶧��.
			//������ ������ context�� �޸𸮿� �ø��°�. �װ�� �޸𸮰� �˾Ƽ� Ŀ�ؼ�Ǯ�� ������.
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(String bName, String bTitle, String bContent) {
		//3����Ʈ �׳� �̷���ġ�°��� ���� DB�� �����ͳִ°�.
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = dataSource.getConnection(); //dataSource.getConnection();���� �����͸� �����ͼ� connection�� �ְ�
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) "
					+ "values (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0 )";
			//mvc_board_seq.nextval�� ����Ŭ�� �˾Ƽ� ������ �����ִ°�����, �� ���������� �� ��ȣ �̴�. �� �۹�ȣ 1 2 3 �̷������� ����Ŭ�� �˾Ƽ� �����ش�.
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn==1) {
				System.out.println("����");
			}else {
				System.out.println("������ ó��");
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
		ResultSet resultSet = null;//���� �������°��̹Ƿ�, ����Ʈ��.
		
		try {//DB���� �ڷ� �̾ƿ��°�. �� ����� ���°���. list
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
				dtos.add(dto);//DB���� �̾ƿ°� �ϳ��ϳ� dtos�� ����. ���� �� dtos�� ���� dtos ����.
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return dtos;
	}

	public BDto contentView(String strID) {
		//�Խ����� ����������� dto �Ѱ��� �Խ��Ǳ��Ѱ� ���� ������
		
		upHit(strID);
		
		BDto dto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
			try {//DB���� �ڷ� �̾ƿ��°�. �� ����� ���°���. list
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
	//�������並 �������� �����. �ֳĸ� �������並 �����ٴ°� ���� �̶�°��̹Ƿ�
	//��ȸ���� 1 ������.
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	
	try {
		connection = dataSource.getConnection();
		String query = "update mvc_board set bHit = bHit+1 where bId =?";
		//���������Ἥ �����Ϳ� �ִ�bHit�� +1�� �Ѵ�. ��ָ� +1�ϳĸ� ������ bId�� �����ؼ�.
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, Integer.parseInt(bId));
		//bId�� �������� ������ �ִ� preparedStatement�� �ִ´�. �� �� bId�� �����°� �������ε� DB�����������̹Ƿ�
		//���⼭ IntegerparseInt(bId)�� �Ἥ �������̿��� bId�� ���������� �ٲ۴�.
		preparedStatement.executeUpdate();
		//������ �����ϴ� ��ɾ�. �� �� �����ϴ� �������� select��� executeQuery�̰�, delete �� update �� �����ϴ»����̸�
		//executeUpdate()�̴�.
		
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		
	}
		
	}

	public void modify(String bId, String bName, String bTitle, String bContent) {
		
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	
	try {
		//3����Ʈ �׳� �̷���ġ�°��� ���� DB�� �����ͳִ°�.
		connection = dataSource.getConnection(); //dataSource.getConnection();���� �����͸� �����ͼ� connection�� �ְ�
		String query = "update mvc_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
		//mvc_board_seq.nextval�� ����Ŭ�� �˾Ƽ� ������ �����ִ°�����, �� ���������� �� ��ȣ �̴�. �� �۹�ȣ 1 2 3 �̷������� ����Ŭ�� �˾Ƽ� �����ش�.
		preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setString(1, bName);
		preparedStatement.setString(2, bTitle);
		preparedStatement.setString(3, bContent);
		preparedStatement.setInt(4, Integer.parseInt(bId));//���ڸ� ���ڷ� �مf��.
		
		int rn = preparedStatement.executeUpdate();//�̱���Ʈ ������Ʈ��. ����Ʈ�� �̱���Ʈ����, ������Ʈ ���� ���� ������Ʈ. ����Ʈ���� ��������.
		
		if(rn==1) {
			System.out.println("����");
		}else {
			System.out.println("������ ó��");
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
			//�����ͺ��̽��� ������ �ִ´�.
			preparedStatement.setInt(1, Integer.parseInt(bId));
			//bId�� String�ε�, �����ͺ��̽��γѱ涧 ���ڷ� �ٲ���Ѵ�.
			preparedStatement.executeUpdate();
			//preparedStatement.executeQuery(); �������� ����Ʈ��� �̰�
			//preparedStatement.executeUpdate(); �������� ����Ʈ �̿��ǰ�(������Ʈ ����Ʈ ��)�̶�� �̰�.
			
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
		
			try {//DB���� �ڷ� �̾ƿ��°�. �� ����� ���°���. list
				connection = dataSource.getConnection();
				
				String query = "select * from mvc_board where bId = ?";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, Integer.parseInt(bId));
				resultSet = preparedStatement.executeQuery();
				
				while (resultSet.next()) {
					int number = resultSet.getInt("bId");
					//���� ����� �Ȱ��Ƽ� ���Ƿ� number�� �مf��. ������ ��������� ���� �Ȱ���.
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
		//��۾��� ��۵��� �ֽż����� ���ڰ� �þ��. �װ��� ���� Shape
		//bId�� �ʿ���µ�, �ֳ��ϸ� ���� ����(���� Id)�̱⶧��.
		//��۵��� �̹� �޸� �ۿ��� �ٽ� ����� �޸� �ֽŴ���� ���� �ٷ� ������ �ö󰡱⶧���� �����ִ� ��۵��� step�� +1�� �Ǿ�� �Ѵ�.
		
		//replyShape(bGroup, bStep); �� �ڵ� �󿡼��� bStep�� 0�̴�. �ֳ��ϸ� �����ۿ� ���� ����̱⶧���� �������� ���� ������. �������� bGroup = 1, bStep = 0�̴�.
		//���� bStep�� 0�̴ϱ�. 0���� ū ��� bStep(��۵�)�� +1��.
		
		//reply�� sql���� insert. �� ���� ����Ʈ
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			//3����Ʈ �׳� �̷���ġ�°��� ���� DB�� �����ͳִ°�.
			connection = dataSource.getConnection(); //dataSource.getConnection();���� �����͸� �����ͼ� connection�� �ְ�
			String query = 
			"insert into mvc_board(bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values(mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
			//mvc_board_seq.nextval�ڵ�����. sql�� ���ڳ־��ִ°�.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			
			preparedStatement.setInt(4, Integer.parseInt(bGroup));
			preparedStatement.setInt(5, Integer.parseInt(bStep)+1);//����� �޸��� �Ʒ��� �޸��Ƿ�, ���� 1�� ������. ���� step1����
			preparedStatement.setInt(6, Integer.parseInt(bIndent)+1);//��� �޸��� ���������� ��ĭ ������ ���Ƿ� ����1���� ���� Indent1����
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn==1) {
				System.out.println("����");
			}else {
				System.out.println("������ ó��");
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
			//3����Ʈ �׳� �̷���ġ�°��� ���� DB�� �����ͳִ°�.
			connection = dataSource.getConnection(); //dataSource.getConnection();���� �����͸� �����ͼ� connection�� �ְ�
			String query = 
			"update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
			//���� �׷� �ѹ��� ������ �߰��� ���� ������ ������� �ڱ⺸�� bStep�� ū������ +1����
			//replyShape(bGroup, bStep); �� �ڵ� �󿡼��� bStep�� 0�̴�. �ֳ��ϸ� �����ۿ� ���� ����̱⶧���� �������� ���� ������. �������� bGroup = 1, bStep = 0�̴�.
			//���� bStep�� 0�̴ϱ�. 0���� ū ��� bStep(��۵�)�� +1��.
			//��� bStep���� +1�� �� �� �ֽ����� ���� 0�̾��� ����� 1�̵�.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.parseInt(bGroup));
			preparedStatement.setInt(2, Integer.parseInt(bStep));
			
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn==1) {
				System.out.println("����");
			}else {
				System.out.println("������ ó��");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
	}

}

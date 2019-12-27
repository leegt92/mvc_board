<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>번호</td>
			<td>이름</td>
			<td>제목</td>
			<td>날짜</td>
			<td>히트</td>
		</tr>
		<c:forEach items="${list}" var="dto"> <%-- jstl사용. items="${list}"는 객체 var="dto"는 변수. 즉 list객체를 사용하는데 이름을 변수dto로 한다. --%>
		<tr>								<!-- 이부분이 바로 request.setAttribute("list", dtos);임. 여기서 가져온 데이터인 list를 여기서 dto라는 이름으로 넣은것. -->
												<!-- 즉 결론은 dto = dtos. 어레이리스트 돌린것. 모든데이터가 다 들어있는것이다 -->
												<!-- list 포문을 돌려서 dto에 몽땅 넣는다. 이를 통해 아까 뽑은 데이터들이 다 dto에 들어간다. -->
			<td>${dto.bId}</td><!-- dto.getbId -->
			<td>${dto.bName}</td>
			<td>
				<c:forEach begin="1" end="${dto.bIndent}">-</c:forEach><!-- 대댓글 처리부분. 포문갯수에따라 - 가 붙음. 왜냐면 대댓글이니까.-->
				<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}</a></td>
																		<!-- 링크있고 ? = 겟방식으로 받는다. -->
																		<%-- content_view.do?bId=${dto.bId}는 해당 타이틀에 링크걸어주는 부분 --%>
																		<!-- 클릭하면 프론트컨트롤러로 가서, 프라이머리키를 이용해 DB로 가서 데이터를 끌고와 상세페이지를 만들어서 보여줌 -->
			<td>${dto.bDate}</td>
			<td>${dto.bHit}</td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="5"> <a href="write_view.do">글작성</a> </td> <!-- 글 작성하고 싶으면 글작성페이지로 갈수있게 링크해줌 -->
		</tr>
	</table>
</body>
</html>
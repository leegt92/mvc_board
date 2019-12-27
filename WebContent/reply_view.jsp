<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
		<table width="500" cellpadding="0" cellspacing="0" border="1">
		<form action="reply.do" method="post">
		
			<input type="hidden" name = "bId" value="${reply_view.bId}">
			<input type="hidden" name = "bGroup" value="${reply_view.bGroup}">
			<input type="hidden" name = "bStep" value="${reply_view.bStep}">
			<input type="hidden" name = "bIndent" value="${reply_view.bIndent}">
			<tr>
				<td> 번호 </td>
				<td> ${reply_view.bId} </td>
			</tr>
			<tr>
				<td> 히트 </td>
				<td> ${reply_view.bHit} </td>
			</tr>
			<tr>
				<td> 이름 </td>
				<td> <input type="text" name="bName" value="${reply_view.bId}"> </td>
			</tr>
			<tr>
				<td> 제목 </td>
				<td> <input type="text" name="bTitle" value="${reply_view.bTitle}"> </td>
			</tr>
			<tr>
				<td> 내용 </td>
				<td> <textarea name="bContent" value="${reply_view.bContent}" ></textarea> </td>
			</tr>
			<tr >
				<td colspan="2"> <input type="submit" value="답변"> &nbsp;&nbsp; <a href="list.do">목록보기</a></td>
			</tr>
		</form>
		</table> 
</body>
</html>
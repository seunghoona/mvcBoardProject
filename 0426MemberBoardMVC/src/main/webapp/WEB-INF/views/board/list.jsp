<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>목록보기</title>
<style>
.table th {
	text-align: center;
}
</style>
</head>
<body>
	<%@include file="../include/header.jsp"%>
	<div class="box">
		<div class="box-header with-border">
			<c:if test="${msg == null}">
				<h3 class="box-title">게시판 목록보기</h3>
			</c:if>
			<c:if test="${msg != null}">
				<h3 class="box-title">${msg}</h3>
			</c:if>
		</div>
		<!-- 출력할 데이터 개수를 설정하는 select -->
		<div class="box-header with-border">
			<select id="count" class="form-control">
				<option value="1"
					<c:out value="${map.pageMaker.criteria.perPageNum==1?'selected':''}"/>>5개씩보기</option>
				<option value="2"
					<c:out value="${map.pageMaker.criteria.perPageNum==2?'selected':''}"/>>10개씩보기</option>
				<option value="3"
					<c:out value="${map.pageMaker.criteria.perPageNum==3?'selected':''}"/>>15개씩보기</option>
				<option value="4"
					<c:out value="${map.pageMaker.criteria.perPageNum==4?'selected':''}"/>>20개씩보기</option>

			</select>
		</div>
		<div class="box-body">
			<table class="table table-bordered table-hover">
				<tr>
					<th width="11%">글번호</th>
					<th width="46%">제목</th>
					<th width="16%">작성자</th>
					<th width="16%">작성일</th>
					<th width="11%">조회수</th>
				</tr>
				<c:forEach var="vo" items="${map.list}">
					<tr>
						<td align="right">${vo.bno}&nbsp;</td>
						<td>&nbsp;<a href="detail?bno=${vo.bno}&page=${map.pageMaker.criteria.page}&perPageNum=${map.pageMaker.criteria.perPageNum}&searchType=${map.pageMaker.criteria.searchType}&keyword=${map.pageMaker.criteria.keyword}">${vo.title}</a>
						<span class="badge bg-blue">${vo.replycnt}</span>
						
						<c:if test="${vo.replycnt>0}">
							<img src="../resources/new.gif" width="25" height="25"/>
						</c:if>
						</td>
						
						<td><a href="../user/sendemail?email=${vo.email}">${vo.nickname}</a>
						<td>&nbsp; ${vo.dispDate}</td>
						<td align="right"><span class="badge bg-blue">
								${vo.readcnt}</span>&nbsp;</td>
					</tr>
				</c:forEach>
			</table>
		</div>



		<div class="box-footer text-center">

			<ul class="pagination">
				<!-- page의 수가 0보다 크다면  -->
				<c:if test="${map.pageMaker.totalCount>0}">
					<c:if test="${map.pageMaker.prev}">
						<li><a
							href="list?page=${map.pageMaker.startPage-1}&perPageNum=${map.pageMaker.criteria.perPageNum}
							&searchType=${map.criteria.searchType}&keyword=${map.criteria.keyword}">이전</a></li>
					</c:if>
					<!-- 페이지 번호  -->
					<c:forEach var="idx" begin="${map.pageMaker.startPage}"
						end="${map.pageMaker.endPage}">
						<li
							<c:out value="${map.pageMaker.criteria.page==idx?'class=active':'' }"/>><a
							href="list?page=${idx}&perPageNum=${map.pageMaker.criteria.perPageNum}&searchType=${map.criteria.searchType}&keyword=${map.criteria.keyword}">${idx}</a></li>
					</c:forEach>
					<!-- 다음 링크 -->
					<c:if test="${map.pageMaker.next}">
						<li><a
							href="list?page=${map.pageMaker.endPage+1}&perPageNum=${map.pageMaker.criteria.perPageNum}&searchType=${map.criteria.searchType}&keyword=${map.criteria.keyword}">다음</a></li>
					</c:if>
				</c:if>
			</ul>
		</div>
			<div class="box-body text-center">
			 	<select name="searchType" id="searchType">
			 			<option value="n"
			 		<c:out value="${map.pageMaker.criteria.searchType==null?'selected':''}"/>>말머리선택</option>
			 			<option value="t"
			 		<c:out value ="${map.pageMaker.criteria.searchType=='t'?'selected':''}"/>>제목</option>
			 			<option value="c"
			 		<c:out value ="${map.pageMaker.criteria.searchType=='c'?'selected':''}"/>>내용</option>
			 			<option value="tc"
			 		<c:out value ="${map.pageMaker.criteria.searchType=='tc'?'selected':''}"/>>제목+내용</option>
			 	</select>
				<input type="text" name="keyword" id="keyword" value="${map.pageMaker.criteria.keyword}"/>
				<input type="button" class="btn btn-waring" id="searchBtn" value="검색"/>
				
			</div>

		<div class="box-footer">
			<div class="text-center">
				<button id='mainBtn' class="btn-primary">메인으로</button>
			</div>

			<script>
				$(function() {
					$('#mainBtn').on("click", function(event) {
						location.href = "../";
					});
				});
			</script>
		</div>
	</div>
	<%@include file="../include/footer.jsp"%>
</body>


<script>
	count = document.getElementById("count")

	count.addEventListener("change", function() {

		location.href = "list?page${map.pageMaker.criteria.page}&"
				+ 'perPageNum=' + this.value;

	});
</script>


<script type="text/javascript">

document.getElementById("count").addEventListener("change",function(){
	search =document.getElementById("searchType").value;
	keyword = document.getElementById("keyword").value;
	
	location.href = 'list?page=${map.pageMaker.criteria.page}&' +
	'perPageNum=' + this.value + "&serarchType=" +
	searchType + "&keyword=" + keyword;
})
</script>

<script type="text/javascript">
document.getElementById("searchBtn").addEventListener("click", function(){
	//select의 선택된 항목 찾기 
	//선택된 행번호 찾기 
	var x = document.getElementById("searchType").selectedIndex;
	//select 모든 값을 배열로 가져오기 
	var y = document.getElementById("searchType").options;
	//keyword에 입력된 값 가져오기 
	keyword = document.getElementById("keyword").value;
	

	location.href="list?page=1&perPageNum=${map.pageMaker.criteria.perPageNum}"+"&searchType=" +y[x].value + "&keyword=" +keyword;
})
</script>
</html>


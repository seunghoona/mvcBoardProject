<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 현재 파일 위체이서 include 디렉토리의 header.jsp 파일의 코드를 가져와서 삽입 
 -->
<%@ include file="include/header.jsp"%>

<section class="content">
	<div class="box">
		<div class="box-header with-border" id="address"></div>
		<c:if test="${user==null}">
			<div class="box-header with-border">
				<a href="user/login"><h3 class="box-title">로그인</h3></a>
			</div>
			<div class="box-header with-border">
				<a href="user/register"><h3 class="box-title">회원가입</h3></a>
			</div>
			
			<div class="box-header with-border">
				<a href="board/list"><h3 class="box-title">게시판 목록보기</h3></a>
			</div>

		</c:if>

		<c:if test="${user!=null}">
			<div class="box-header with-border">
				<a href="user/logout"><h3 class="box-title">로그아웃</h3></a>
			</div>

			<div class="box-header with-border">
				<a href="board/register"><h3 class="box-title">게시물작성</h3></a>
			</div>
		</c:if>

	</div>

</section>


<c:if test="${msg!=null || msg=='회원가입'}">
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="/resources/demos/style.css">
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

	<div id="dialog-message" title="Download complete">
		<p>${msg}에성공하셨습니다.</p>
	</div>

	<script>
  $( function() {
    $( "#dialog-message" ).dialog({
      modal: true,
      buttons: {
        Ok: function() {
          $( this ).dialog( "close" );
        }
      }
    });
  } );
  </script>
</c:if>
<script>
setInterval(function(){
	
	//현재 접속한 브라우저의 위도와 경도 출력하기 
	navigator.geolocation.getCurrentPosition(function(position){

		loc = position.coords.latitude + "-" +position.coords.longitude
		//address라는 URL loc를 파라미터로 넘겨서 
		//json 타입으로 데이터를 받아오는 ajax 요청 
		console.log(loc)
		$.ajax({
			url: "address",
			data:{"loc":loc},
			dataType:'json',
			success:function(data){
				document.getElementById("address").innerHTML="<h3>" + data.address + "</h3>";
			}
		});
		
	});
},10000);
</script>

<%@ include file="include/footer.jsp"%>
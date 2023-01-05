<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix ="fn" uri ="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>



<body>
	<!-- header 부분 -->
	<jsp:include page="../../menu.jsp" />

	<!-- --------------------------- <visual> --------------------------------------- -->
	<!-- visual 부분 -->

	<div id="visual">
		<div class="content-container"></div>
	</div>
	<!-- --------------------------- <body> --------------------------------------- -->
	<div id="body">
		<div class="content-container clearfix">

			<!-- --------------------------- aside --------------------------------------- -->
			<!-- aside 부분 -->


			<aside class="aside">
				<h1>ADMIN PAGE</h1>

				<nav class="menu text-menu first margin-top">
					<h1>마이페이지</h1>
					<ul>
						<li><a href="../../../admin/index.html">관리자홈</a></li>
						<li><a href="../../../teacher/index.html">선생님페이지</a></li>
						<li><a href="../../../student/index.html">수강생페이지</a></li>
					</ul>
				</nav>

				<nav class="menu text-menu">
					<h1>알림관리</h1>
					<ul>
						<li><a href="../../../admin/board/notice/list.html">공지사항</a></li>
					</ul>
				</nav>

			</aside>
			<!-- --------------------------- main --------------------------------------- -->



			<main class="main">
				<h2 class="main title">공지사항</h2>

				<div class="breadcrumb">
					<h3 class="hidden">경로</h3>
					<ul>
						<li>home</li>
						<li>고객센터</li>
						<li>공지사항</li>
					</ul>
				</div>

				<div class="search-form margin-top first align-right">
					<h3 class="hidden">공지사항 검색폼</h3>
					<form class="table-form">
						<fieldset>
							<legend class="hidden">공지사항 검색 필드</legend>
							<label class="hidden">검색분류</label>
							<select name="f">
								<option value="title">제목</option>
								<option value="writerId">작성자</option>
							</select>
							<label class="hidden">검색어</label>
							<input type="text" name="q" value="" />
							<input class="btn btn-search" type="submit" value="검색" />
						</fieldset>
					</form>
				</div>

				<form action="list" method="post">
					<div class="notice margin-top">
						<h3 class="hidden">공지사항 목록</h3>
						<table class="table">
							<thead>
								<tr>
									<th class="w60">번호</th>
									<th class="expand">제목</th>
									<th class="w100">작성자</th>
									<th class="w100">작성일</th>
									<th class="w60">조회수</th>
									<th class="w40">공개</th>
									<th class="w40">삭제</th>
								</tr>
							</thead>
							<tbody>
							
							
								<c:forEach var = "n" items="${list}">
								<c:set var ="open" value=""/>
								<c:if test="${n.pub}">
									<c:set var ="open" value="checked"/>
								</c:if>
								<tr>
									<td>${n.id}</td>
									<td class="title indent text-align-left"><a href="detail?id=${n.id}">${n.title}</a><span style = "color:red;">[${n.cmtCount}]</span></td>
									<td>${n.writerId}</td>
									<!-- MM은 왜 대문자냐 분이라고 하는 문자와 같아 대소문자로 구분한 것 대문자 M은 월 소문자 m은 분 -->
									<td><fmt:formatDate pattern="yyyy-MM-dd" value = "${n.regdate}" /></td>
									<td>${n.hit}</td>
									<td><input type="checkbox" name="open-id" ${open}  value="${n.id}"></td>
									<td><input type="checkbox" name="del-id" value="${n.id}" ></td>
								</tr>
								</c:forEach>
	
									
	
							</tbody>
						</table>
					</div>
	
					<c:set var="page" value="${(empty param.p)?1:param.p}" />
					<c:set var = "startNum" value = "${page-(page-1)%5}"/>
					 <!-- Math.ceil(10.2) -> 소숫점을 가지고 있으면 11.0으로 만들어주는 거 -->
					 <!-- 11.0으로 결괏값이 나와서 fn:substringBefore(,'')를 사용하여 ''안에 구분자 .을 입력하여 -->
					 <!-- .앞에 있는 부분만 잘라 달라고 하는 메서드 --> 
					<c:set var = "lastNum" value = "${fn:substringBefore(Math.ceil(count/10),'.')}"/>
				
					<div class="indexer margin-top align-right">
						<h3 class="hidden">현재 페이지</h3>
						<!-- empty는 빈 문자열이거나 null값이 들어가 있으면 참을 리턴함 -->
						<div><span class="text-orange text-strong">${(empty param.p)?1:param.p}</span> / ${lastNum} pages</div>
					</div>
	
	
	
					<div class="text-align-right margin-top">
						<c:set var="ids" value=""/>
						<c:forEach var ="n" items="${list}">
							<c:set var="ids" value="${ids} ${n.id}"/> <!-- list의 아이디를 ids에 대입 -->
						</c:forEach>
						<input type ="hidden" name="ids" value="${ids}">
						<input type="submit" class="btn-text btn-default" name ="cmd" value="일괄공개">
						<input type="submit" class="btn-text btn-default" name ="cmd" value="일괄삭제">
						<a class="btn-text btn-default" href="reg">글쓰기</a>				
					</div>
				</form>
				<div class="margin-top align-center pager">	
		
				<div>
			
				<c:if test="${startNum>1}">
					<a  href="?p=${startNum-1}&t=&q=" class="btn btn-prev" >이전</a>
				</c:if>
				<c:if test="${startNum<=1}">
					<span class="btn btn-prev" onclick="alert('이전 페이지가 없습니다.');">이전</span>
				</c:if>
				
				</div>		
				
				<ul class="-list- center">
				<c:forEach var = "i" begin="0" end="4">
				<c:if test="${startNum+i<=lastNum}">
				<!-- orange을 "" 로 감싸게 되면 "orange"로 리턴이 되므로 ''로 감싸면 그냥 그 문자만 리턴이 됨 -->
					<li><a class="-text- ${(page==(startNum+i))?'orange':''}  bold" href="?p=${startNum+i}&f=${param.f}&q=${param.q}" >${startNum+i}</a></li>
				</c:if>
				</c:forEach>			
				</ul>
				<div> 
					
				<c:if test="${startNum+4<lastNum}">
					<a href="?p=${startNum+5}&t=&q="   class="btn btn-next">다음</a>
				</c:if>
				<c:if test="${startNum+4>=lastNum}">
				<span class="btn btn-next" onclick="alert('다음 페이지가 없습니다.');">다음</span>
				</c:if>
				
				</div>
				
						</div>
			</main>


		</div>
	</div>

	<!-- footer 부분 -->
	<jsp:include page="../../footer.jsp" />
</body>

</html>
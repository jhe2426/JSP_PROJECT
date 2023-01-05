<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri ="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri ="http://java.sun.com/jsp/jstl/functions" %>
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




            <main>
                <h2 class="main title">공지사항</h2>

                <div class="breadcrumb">
                    <h3 class="hidden">breadlet</h3>
                    <ul>
                        <li>home</li>
                        <li>고객센터</li>
                        <li>공지사항</li>
                    </ul>
                </div>

                <div class="margin-top first">
                    <h3 class="hidden">공지사항 내용</h3>
                    <table class="table">
                        <tbody>
                            <tr>
                                <th>제목</th>
                                <td class="text-align-left text-indent text-strong text-orange" colspan="3">${n.title}</td>
                            </tr>
                            <tr>
                                <th>작성일</th>
                                <td class="text-align-left text-indent" colspan="3"><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value = "${n.regdate}" /></td>
                            </tr>
                            <tr>
                                <th>작성자</th>
                                <td>${n.writerId}</td>
                                <th>조회수</th>
                                <td><fmt:formatNumber  value="${n.hit}" /></td>
                            </tr>
                            <tr>
                                <th>첨부파일</th>
                                <td colspan="3">
                                	
									<!--items의 문자열을  delims에 구분자를 이용하여 분리할 수 있도록 해줌 -->
									<c:forTokens var ="fileName" items="${n.files}" delims="," varStatus="st">
									<c:set var="style" value=""/>
									<c:if test ="${fn:endsWith(fileName, '.zip')}">
										<c:set var="style" value="font-weight:bold; color:red;"/>
									</c:if>
									<a download href="../../../upload/${fileName}" style = "${style}">${fn:toUpperCase(fileName)}</a> 
									<c:if test="${!st.last}">
										/
									</c:if>										
									</c:forTokens>	
                                
                                </td>
                            </tr>
                            <tr class="content">
                                <td colspan="4">${n.content}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="margin-top text-align-center">
                    <a class="btn-text btn-cancel" href="list">목록</a>
                    <a class="btn-text btn-default" href="edit">수정</a>
                    <a class="btn-text btn-default" href="del">삭제</a>
                </div>

                <div class="margin-top">
                    <table class="table border-top-default">
                        <tbody>
                            <tr>
                                <th>다음글</th>
                                <td colspan="3" class="text-align-left text-indent">다음글이 없습니다.</td>
                            </tr>
                            <tr>
                                <th>이전글</th>
                                <td colspan="3" class="text-align-left text-indent"><a class="text-blue text-strong"
                                        href="">스프링 DI 예제 코드</a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </main>

        </div>
    </div>

	<!-- footer 부분 -->
	<jsp:include page="../../footer.jsp" />
</body>

</html>
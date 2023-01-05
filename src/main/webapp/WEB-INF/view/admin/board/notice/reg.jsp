<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                <h2 class="main title">공지사항 등록</h2>

                <div class="breadcrumb">
                    <h3 class="hidden">breadlet</h3>
                    <ul>
                        <li>home</li>
                        <li>게시글 관리</li>
                        <li>공지사항</li>
                    </ul>
                </div>

				<!-- enctype에 enctype="application/x-www-form-urlencoded" 이렇게 입력했을 때 -->
				<!-- 입력하지 않아도 디폴트가 인코딩 타입이 application/x-www-form-urlencoded임 -->
				<!-- url에 submit을 눌렀을 떄 보내는 값이 -->
				<!-- 주소창에 뜨도록 하는 것 -->
				<!-- 하지만 method방식이 get일 때만 url에 보내는 값이 보여지지 -->
				<!-- post방식으로 설정하면 url에 보내는 값이 표시되어지지않음 -->
                <form method="post" action="reg" enctype="multipart/form-data">
                    <div class="margin-top first">
                        <h3 class="hidden">공지사항 입력</h3>
                        <table class="table">
                            <tbody>
                                <tr>
                                    <th>제목</th>
                                    <td class="text-align-left text-indent text-strong text-orange" colspan="3">
                                        <input type="text" name="title" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>첨부파일</th>
                                    <td colspan="3" class="text-align-left text-indent"><input type="file"
                                            name="file" />         
                                    </td>
                                </tr>
                                <tr>
                                    <th>첨부파일</th>
                                    <td colspan="3" class="text-align-left text-indent"><input type="file"
                                            name="file" />         
                                    </td>
                                </tr>
                                <tr class="content">
                                    <td colspan="4"><textarea class="content" name="content"></textarea></td>
                                </tr>
                                <tr>
                                    <td colspan="4" class="text-align-right"><input class="vertical-align" type="checkbox" id="open" name="open" value="true"><label for="open" class="margin-left">바로공개</label> </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="margin-top text-align-center">
                        <input class="btn-text btn-default" type="submit" value="등록" />
                        <a class="btn-text btn-cancel" href="list">취소</a>
                    </div>
                </form>

            </main>
        </div>
    </div>

	<!-- footer 부분 -->
	<jsp:include page="../../footer.jsp" />
</body>

</html>
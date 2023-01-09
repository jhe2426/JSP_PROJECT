# JSP_PROJECT



실행 방법 :<br>
관리자 게시물 리스트 페이지<br>
src -> main -> java -> com-> newlecture-> web -> controller-> admin 폴더 밑에 있는 AdminListController를 실행하면 됩니다.<br>
사용자 게시물 리스트 페이지<br>
src -> main -> java -> com-> newlecture-> web -> controller-> notice 폴더 밑에 있는 ListController를 실행하면 됩니다.<br>
<br><br>

1.설명<br>
- 개발도구 : Java, oracleDB
- 게시물의 MVC 패턴을 이용하여 CRUD가 어떻게 코드가 이뤄어져있는지 궁금하여 클론코딩한 페이지입니다.<br><br>


2. 작동원리<br>
사용자 MVC<br>
![사용자MVC](https://user-images.githubusercontent.com/117806984/211289593-e8fb7903-226d-4ad3-8df9-72347cddd7d4.png)<br><br>

관리자 MVC<br>
![관리자MVC](https://user-images.githubusercontent.com/117806984/211289600-3d5cf602-f428-4b7e-8894-964454cd0506.png)<br><br>



3. 이슈 및 해결책<br>
처음에 html 파일을 다운을 받아 실행을 시켰을 때 CSS가 하나도 적용이 안 되어 클론 코딩의 웹페이지 주소와 제 웹페이지 주소를 비교하니 경로가 하나 추가가 되어있는 것을 보고 css 파일 경로부분에 경로를 하나 더 추가를 해주니 CSS가 적용이 되었습니다.<br><br>





4. 추가하고 싶은 기능<br>
-게시물의 상세페이지 부분에 있는 삭제 버튼을 눌렀을 때 데이터베이스에 해당 게시물이 삭제되고 뷰에서도 삭제되는 것을 추가하고 싶습니다.<br>
-게시물의 상세페이지에서 다음 글 이전 글 부분도 추가하고 싶습니다.<br><br>

5. 추가 구현한 부분<br>
XML에 시큐리티를 추가하여 관리자 리스트 페이지에 들어갈 때 로그인을 하도록 추가 구현했습니다.<br><br>


6. 출처<br>
https://www.youtube.com/watch?v=JjP4kGxKiuI&list=PLq8wAnVUcTFVOtENMsujSgtv2TOsMy8zd&index=52

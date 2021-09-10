# [Spring Boot] 통합 게시판 커뮤니티
-----------

**개요**
* Spring Boot를 이용한 통합 게시판

**주요 기능**
* 회원 가입
* 자유 게시판
* 영화 검색
* 지역 검색
* 회원 정보 수정

----------- 
**주요 기술**
* Google OTP MFA 적용  
* 영화 검색 API 기능 사용
* 지역 검색 API 기능 사용
* 조회수, 추천수, 댓글 기능
* 세션 처리 및 권한 별 기능 구분
* 게시판 작성, 수정, 파일 업로드 기능 
* 관리자 기능 구현

-----------
**프로젝트 화면**
1. 로그인 화면
![login](https://user-images.githubusercontent.com/65889807/132848253-0b0146cb-9a73-470f-b001-a4b80c30f767.png)

2. 회원가입 화면
 * 회원 정보 미 입력시 alert 기능
 * 비밀번호는 최소 8자 최대 16자, 특수문자 하나 이상 포함
![user_register](https://user-images.githubusercontent.com/65889807/132848395-70e7532d-28d0-481a-9e7b-cde4c9d1afde.png)

3. OTP 화면
![Google_OTP](https://user-images.githubusercontent.com/65889807/132848703-c5d00a44-09ee-44fa-8eca-0967bfcff5d2.png)

4. 자유 게시판
  * 로그인 성공 시 Session 저장
  * profile -> logout시 Session 삭제
  * 게시판 리스트 출력
![Board_Insert](https://user-images.githubusercontent.com/65889807/132849300-074d74ea-e931-4bd0-a5a8-2cdadd85014c.png)


5. 게시글 작성
   * 제목, 내용(3000kbs 제한), 파일업로드(100MB이하) 구현
![Board_Insert](https://user-images.githubusercontent.com/65889807/132849034-65d25cd6-f177-49f8-b761-4577c493c5af.png)
 





-----------

**기술 스택**
* Spring Boot
* Spring JPA, Spring JDBC
* Spring Security
* thymleaf
* Mybatis
* Mysql
* AJax
* Bootstrap
* Gradle


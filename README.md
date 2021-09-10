# [Spring Boot] 통합 게시판 커뮤니티
-----------

**개요**
* Spring Boot를 이용한 통합 게시판 커뮤니티
----------- 

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

----------- 
2. 회원가입 화면
 * 회원 정보 미 입력시 alert 기능
 * ID, 비밀번호, 이름, 나이 입력
 * 비밀번호는 최소 8자 최대 16자, 특수문자 하나 이상 포함
 
 ![user_register](https://user-images.githubusercontent.com/65889807/132848395-70e7532d-28d0-481a-9e7b-cde4c9d1afde.png)
 
 ----------- 
3. Google OTP 화면 구현             

![Google_OTP](https://user-images.githubusercontent.com/65889807/132848703-c5d00a44-09ee-44fa-8eca-0967bfcff5d2.png)

----------- 
4. 자유 게시판
  * 로그인 성공 시 Session 저장
  * profile -> logout시 Session 삭제
  * 게시판 리스트 출력
  
![Main_freeboard](https://user-images.githubusercontent.com/65889807/132849714-5bb2a688-5a2a-41ef-84fd-52461a8c8291.png)

----------- 
5. 게시글 작성
   * 제목, 내용(3000Bytes 제한), 파일업로드(100MB이하) 구현
   
![Board_Insert](https://user-images.githubusercontent.com/65889807/132849034-65d25cd6-f177-49f8-b761-4577c493c5af.png)

----------- 
6. 게시글 상세
   * 게시글 수정 기능 구현(자기가 쓴 게시글만 수정버튼 보임)
   * 게시글 삭제 기능 구현(자기가 쓴 게시글만 삭제버튼 보임)
   * 댓글 기능 구현(자신이 쓴 댓글만 삭제 가능)
   * 추천기능(1번만 가능, 한번 더 누를 시 추천 취소) 구현
   * 조회수, 추천 수, 댓글 수 출력
  
 ![Board_detail](https://user-images.githubusercontent.com/65889807/132850962-e8704895-9ce1-4609-b6a8-811dd665746e.png)

----------- 
8. 영화 검색
   * 네이버 영화 검섹 API 기능 사용
   * 영화 제목, 개봉(년), 감독, 주요 배우, 관객 수 제공
   
![Movie_research](https://user-images.githubusercontent.com/65889807/132849866-928a7ff1-7c87-44f5-a927-5a415afaeefb.png)

 ----------- 
 7. 지역 검색
    * 네이버 지역 검색 API 기능 사용
    * Category, 이름, 링크, 도로명 주소 제공(최대 5개)
    * Excel 버튼을 이용해 다운로드 가능
    * 링크 클릭시 홈페이지로 이동

![Location_research](https://user-images.githubusercontent.com/65889807/132850327-d46efa4c-6e99-427e-bf50-c7287d2c4a9e.png)

----------- 
 8. 기록
    * 자유 게시판에서 추천 누른 게시글 리스트 출력
    * 추천누른 시간 제공
  
![Recommend](https://user-images.githubusercontent.com/65889807/132850435-84a78bd0-1306-431b-ab82-6437993fdbd4.png)

----------- 
 9. 회원 정보(권한: User)
   * 사용자 정보(아이디, 이름, 나이, 권한, 등록일, 비밀번호)
   * 비밀번호 변경 기능(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)
   * 회원 탈퇴 기능
   * 댓글 작성했던 게시글 리스트 출력(댓글 등록 날짜 제공)

![User_info](https://user-images.githubusercontent.com/65889807/132850630-68bdc55e-fc5a-48e3-83b0-17e264f68329.png)

-----------
10. 회원 정보(권한: Admin)
   * 사용자 정보(아이디, 이름, 나이, 권한, 등록일, 비밀번호)
   * 비밀번호 변경 기능(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)
   * 회원 탈퇴 기능
   * 사용자 리스트 출력(사용자 등록일 제공) 

![User_Info_Admin](https://user-images.githubusercontent.com/65889807/132851443-d4ba0849-afa9-48ec-88b5-1e11adf52cc2.png)
  

-----------
**기술 스택**
* Spring Boot
* Spring JPA
* Spring JDBC
* Spring Security
* thymleaf
* Mybatis
* Mysql
* AJax
* Bootstrap
* Gradle
* Google OTP API
* NAVER API

-----------
**프로젝트 정보**
* 신승용 - (ssyong917@naver.com)
* 프로젝트 소요 시간 - 40시간

----------- 

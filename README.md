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
* 로그인 SSO 구현(구글, 카카오, 네이버)

----------- 
**주요 기술**
* Google OTP MFA 적용  
* 영화 검색 API 기능 사용
* 지역 검색 API 기능 사용
* 조회수, 추천수, 댓글 기능
* 세션 처리 및 권한 별 기능 구분
* 게시판 작성, 수정, 파일 업로드 기능 
* 관리자 기능 구현
* Spring oAuth2를 이용한 SSO 기능 구현
* 이메일 인증 기능 추가
-----------
**프로젝트 화면**
1. 로그인 화면
<p align="center"><img src="https://user-images.githubusercontent.com/65889807/139256132-762cd76d-8d8c-4b3f-9ce9-7580334dc6d2.png">

 -----------
 
**1-1) Google SSO, Naver SSO, Kakao SSO**
 * Spring oAuth 적용
 * 계정 정보를 받으면 DB에 저장(이미 SSO정보가 DB에 있을시 redirect)
 * SSO 로그인 성공 시 Session 저장 및 main화면으로 redirect

<p align="left">
 <img src="https://user-images.githubusercontent.com/65889807/139256350-2ece58ab-1995-42ea-8c27-b0e02aabbe7c.png" width="450" height="622">
 <img src="https://user-images.githubusercontent.com/65889807/139257329-a76399cb-4158-4e68-8f28-1ab9d89a08e7.png" width="500" height="900">
 <img src="https://user-images.githubusercontent.com/65889807/139257961-f4b4d722-cdcb-43c1-97ba-db78e3b36550.png" width="524" height="860">
</p>

----------- 
**2. 회원가입 화면**
 * 회원 정보 미 입력시 alert 기능
 * ID, 비밀번호, 이름, 나이 입력
 * 비밀번호는 최소 8자 최대 16자, 특수문자 하나 이상 포함
 * Email 인증 기능 추가 
 
 ![user_register](https://user-images.githubusercontent.com/65889807/140730583-bef992f8-1de5-437c-86ff-3aec8c923237.png)
 ![valifyEmail](https://user-images.githubusercontent.com/65889807/140730626-97af6c49-9e20-4b46-8a4c-e6724921c31b.png)

 ----------- 
**3. Google OTP 화면 구현**             
* Google OTP 앱을 다운받아 생성된 계정으로 6자리 코드입력(BoardCommunity: (admin))
* 30초마다 6자리 코드 새로고침
* 코드 입력후 일치하면 메인 화면으로 
![Google_OTP](https://user-images.githubusercontent.com/65889807/132848703-c5d00a44-09ee-44fa-8eca-0967bfcff5d2.png)

----------- 
**4. 자유 게시판**
  * 로그인 성공 시 Session 저장
  * profile -> logout시 Session 삭제
  * 게시판 리스트 출력
  
![Main_freeboard](https://user-images.githubusercontent.com/65889807/132849714-5bb2a688-5a2a-41ef-84fd-52461a8c8291.png)

----------- 
**5. 게시글 작성**
   * 제목, 내용(3000Bytes 제한), 파일업로드(100MB이하) 구현
   
![Board_Insert](https://user-images.githubusercontent.com/65889807/132849034-65d25cd6-f177-49f8-b761-4577c493c5af.png)

----------- 
**6. 게시글 상세**
   * 게시글 수정 기능 구현(자기가 쓴 게시글만 수정버튼 보임)
   * 게시글 삭제 기능 구현(자기가 쓴 게시글만 삭제버튼 보임)
   * 댓글 기능 구현(자신이 쓴 댓글만 삭제 가능)
   * 추천기능(1번만 가능, 한번 더 누를 시 추천 취소) 구현
   * 조회수, 추천 수, 댓글 수 출력
  
 ![Board_detail](https://user-images.githubusercontent.com/65889807/132850962-e8704895-9ce1-4609-b6a8-811dd665746e.png)

----------- 
**7. 영화 검색**
   * 네이버 영화 검섹 API 기능 사용
   * 영화 제목, 개봉(년), 감독, 주요 배우, 관객 수 제공
   
![Movie_research](https://user-images.githubusercontent.com/65889807/132849866-928a7ff1-7c87-44f5-a927-5a415afaeefb.png)

 ----------- 
**8. 지역 검색**
  * 네이버 지역 검색 API 기능 사용
  * Category, 이름, 링크, 도로명 주소 제공(최대 5개)
  * Excel 버튼을 이용해 다운로드 가능 
  * 링크 클릭시 홈페이지로 이동

![Location_research](https://user-images.githubusercontent.com/65889807/132850327-d46efa4c-6e99-427e-bf50-c7287d2c4a9e.png)

----------- 
**9. 기록**
  * 자유 게시판에서 추천 누른 게시글 리스트 출력
  * 추천누른 시간 제공
 
  ![record](https://user-images.githubusercontent.com/65889807/139260252-c0f461e6-137b-470d-bf25-4a766dc43114.png)

----------- 
**10. 회원 정보(권한: User)**
   * 사용자 정보(아이디, 이름, 나이, 권한, 등록일, 비밀번호)
   * 비밀번호 변경 기능(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)
   * 회원 탈퇴 기능
   * 댓글 작성했던 게시글 리스트 출력(댓글 등록 날짜 제공)

![User_info](https://user-images.githubusercontent.com/65889807/132850630-68bdc55e-fc5a-48e3-83b0-17e264f68329.png)

-----------
**11. 회원 정보(권한: Admin)**
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
* SSO
* Sring oAuth2

-----------
**프로젝트 정보**
* 신승용 - (ssyong917@naver.com)

----------- 

# :dog: 펫 탐정소 Server     
<h3>
  <a href="https://docs.google.com/presentation/d/1KBlHlEJMoPWHiP5bZy2ViVnBxUouRoL6/edit?usp=sharing&ouid=100246645175953757075&rtpof=true&sd=true">portfolioー한국어 <- </a> <br/>
<a href="https://docs.google.com/presentation/d/1zKrOkAhA9B9OQr9WThakyF4qwp0yUx-X/edit?usp=sharing&ouid=100246645175953757075&rtpof=true&sd=true">ポートフォリオ-日本語 <- </a>
</h3>


유실견 문제로 발생하는 사회적 문제에 대한 해결책을 찾고자  
강아지를 잃어버렷을때 필요한 행동에 대한 기능을 제공하는 유실견 찾기 플랫폼 서비스 **펫 탐정소** 입니다.  
![image](https://user-images.githubusercontent.com/68727046/173567700-261ab0a7-fd27-4902-b793-4486c44b6d00.png)

<br/><br/>
## :computer: Development Environment  
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white"/>  <img src="https://img.shields.io/badge/Spring Data Jpa-6DB33F?style=flat&logo=SpringBoot&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white"/>  <img src="https://img.shields.io/badge/Amazon S3 -569A31?style=flat&logo=AmazonS3&logoColor=white"/>  <img src="https://img.shields.io/badge/Heroku -430098?style=flat&logo=Heroku&logoColor=white"/>
<br/><br/>
## :floppy_disk: Brach Strategy
- ``` main ``` 브랜치
  - ``` main ```에 직접적인 commit, push는 가급적 금지합니다 
- ```Koo & won``` 브랜치
  - 백엔트 파트를 맡은 인원에 따른 ``` 개인 ``` 브랜치 
  - 계획한 모든 기능 구현 & 테스트 통과 시 ``` 개인 ``` 브랜치로 Pull 합니다.
  - 팀원들에게 코드리뷰를 요청합니다
  - 리뷰를 완료하면 ```main``` 으로 merge 하고, conflict가 발생하면 해결 후 ```main``` 으로 merge합니다
  - 새로운 작업을 할 때 ```main``` 브랜치의 내용을 ```개인``` 브랜치로 pull 받아서 작업을 이어갑니다  
    ``` git pull origin develop ```
## ⚙Conention 
- Code Convention  
  1. Variable, Method name lowerCamelCase 사용
  2. Method 는 "동사 + 명사 " 형태 사용 
  3. 약어 사용 지양

- Commit Convention
  1. 변경된 코드를 바로 볼수있도록 기능 구현시 즉시 커밋 권장.
  2. 커밋 메세지는 로그 확인시 알아볼수 있도록 작성 (한글)

## :page_with_curl: Dependencies  

<details>
  <summary>상세 보기 </summary>

<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-aws</artifactId>
			<version>2.0.1.RELEASE</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>com.turo</groupId>
			<artifactId>pushy</artifactId>
			<version>0.13.10</version>
		</dependency>
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.8.6</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-client</artifactId>
			<version>2.6.6</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>net.nurigo</groupId>
			<artifactId>javaSDK</artifactId>
			<version>2.2</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-hibernate5 -->
		<dependency>
			<groupId>com.fasterxml.jackson.datatype</groupId>
			<artifactId>jackson-datatype-hibernate5</artifactId>
			<version>2.13.2</version>
		</dependency>

	</dependencies>
</details>

## 🌀 API 명세서
[API 명세서 보러가기](https://iospring.notion.site/REST-API-ca2f4423404d497893118e058380caac/)
	  
## :chart_with_downwards_trend: ERD
<img src = "https://user-images.githubusercontent.com/68727046/174724489-f1dd0a73-208f-44fc-b880-2cfb0a727308.png" width="70%" height="70%">
	  
## :notebook_with_decorative_cover: Story Board

### 로그인,회원가입 
<img src = "https://user-images.githubusercontent.com/68727046/174611838-e7267654-43ad-4cd8-aa24-6edf530c5f7d.png" width="30%" height="30%"> <img src = "https://user-images.githubusercontent.com/68727046/174612156-c8b04098-5048-4803-a2c3-78ef0913edb8.png" width="30%" height="30%"> 
<details>
  <summary>기능 설명 </summary>
 	- 핸드폰 번호 입력 후, 해당 핸드폰에 오는 문자의 인증번호를 입력한다.</br>
	-  추가정보( 현재위치, 이메일 주소, Device Token ) 등을 받아서 회원가입 을 진행한다.</br>
 	- 기존 유저가 아닌 경우 회원가입 절차로 이동.  </br>
 	- 기존 회원인 경우 로그인 및  ios 기기의 Device Token 을 갱신 </br>

</details>


### 게시글
<img src = "https://user-images.githubusercontent.com/68727046/174612474-ff008d11-c9d9-49eb-b5ca-1878f8e0169c.png" width="30%" height="30%"> <img src = "https://user-images.githubusercontent.com/68727046/174611575-9e23e12a-8cd6-423a-ad05-ae038cfded41.png" width="30%" height="30%"> 

<details>
  <summary>기능 설명 </summary>
	- 사용자가 등록한 게시글 을 볼수있는 페이지이다.</br>
	- 위치, 종, 털색 의 조건으로 검색할수 있다.
</details>

### 게시글 상세보기  
<img src = "https://user-images.githubusercontent.com/68727046/174612397-7132371b-84f7-42e4-9caa-71daf3417816.png" width="30%" height="30%"> 

<details>
  <summary>기능 설명 </summary>
	- 게시글 상세 내용 을 볼수 있는 페이지이다.</br>
</details>

### 게시글 등록  
<img src = "https://user-images.githubusercontent.com/68727046/174612891-b7ab4c13-cb55-4bfb-9393-7fd8c604ee8d.png" width="30%" height="30%"> <img src = "https://user-images.githubusercontent.com/68727046/174612999-6f2b2200-0b97-48d6-812c-207682fea8b3.png" width="30%" height="30%">  
<img src = "https://user-images.githubusercontent.com/68727046/174613083-02bb7e37-c6f5-4c63-aac0-4ae447ca915b.png" width="30%" height="30%"> <img src = "https://user-images.githubusercontent.com/68727046/174613514-390c3f8e-c912-4379-a10b-7eac48b9434a.png" width="30%" height="30%">

<details>
  <summary>기능 설명 </summary>
	- 게시글을 등록하는 페이지 이다.</br>
	- 사용자가 강아지의 사진을 올리면 A.I 가 자동으로 종,털색을 분류하여 정보를 입력해준다. </br>
	- 사용자는 지도핀 을 통해 위치정보를 입력할수있다.</br>
	- 게시글을 수정,삭제 또한 가능하다.
</details>

### 골든타임 게시글 보기
<img src = "https://user-images.githubusercontent.com/68727046/174614276-41be45f0-0037-43da-a8bd-f93df1401eb4.png" width="30%" height="30%">
<details>
  <summary>기능 설명 </summary>
	- 지도를 통해 게시글을 보여준다. </br>
	- 상단의 탭을 통해 의뢰/발견 게시글을 선택할 수 있다. </br>
	- 이때  골든타임(3시간) 이내 && 사용자 정보(사용자가 등록한 위치정보, 의뢰/발견 게시글 등) 을 취합하여 정보들을 필터링 해서 보여준다.
</details>

### 푸시 알림
<img src = "https://user-images.githubusercontent.com/68727046/174615351-e01e5286-7892-48a8-b878-5d569e6c3d2e.png" width="30%" height="30%">
<details>
  <summary>기능 설명 </summary>
	- 사용자가 글을 작성시 조건에 맞는 사용자들에게 푸시알림을 보내준다.</br>
	- 조건은 </br>
		1. 의뢰 게시글 등록시 주변 3km 사용자 </br>
		2. 발견 게시글 등록시 주변 3km , 발견 이전에 강아지를 잃어버린 사용자, 동일한 품종 등 이 일치하는 사용자
</details>

### 알림 리스트
<img src = "https://user-images.githubusercontent.com/68727046/174616273-6729ed7c-4140-4587-be4e-f695ddc35a22.png" width="30%" height="30%">
<details>
  <summary>기능 설명 </summary>
	- 받을 알림들을 볼수있는 페이지 이다.</br>
	- 알림 터치시 해당 게시글 페이지로 이동한다. 
</details>

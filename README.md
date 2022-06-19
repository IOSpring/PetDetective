# 펫 탐정소 Server       
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

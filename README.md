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

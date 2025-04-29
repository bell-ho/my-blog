<p align="center">
  <img src="https://user-images.githubusercontent.com/62537935/226505111-bbbe363f-7183-43b8-81d9-040d53a4dd68.png">
</p>

## 🔖 소개
> **나의 일상 공유하기**
> 
<p align="center">
  <img src="https://user-images.githubusercontent.com/62537935/226513661-830146e7-7e65-433f-b6ce-e95767e8a043.gif">
</p>

<br>

## 구현 기능

### 소셜 로그인, 로그아웃
### 게시글, 사진 기능
### 좋아요 기능
### 게시글 숨기기 기능
### 숨겨진 게시글 태그 검색 기능

<br>

## 서비스 구조
![myblog2 drawio (1)](https://github.com/user-attachments/assets/1d50a3cf-a399-4caa-b156-34077fd86e99)

<br>

<br>

## ERD
![myblog-erd](https://github.com/user-attachments/assets/86fd3415-0b4d-4ec2-93c6-99d782a80536)

<br>

## 📚 기술스택

| 분야           | 사용 기술                                      | 비고 |
| -------------- |--------------------------------------------| ---- |
| FrontEnd       | React, Next.js, SSR, Next-Auth, React-Query, javascript, MUI |
| BackEnd        | SpringBoot, JPA                                    |
| Database       | MySql                                      |
| Cloud Services | AWS EC2, AWS S3, AWS RDS, AWS ROUTE53                            |

<br>

## 작업 내역
#### 2023
* 03/14
  * 프로메테우스, 그라파나 적용
* 03/07
  * 태그 검색기능 딜레이 추가 (300ms)
* 03/06
  * 사용자 Role 정의
  * ROLE_ADMIN은 모든 게시글 삭제 권한 추가 (게시글 관리 차원)
* 03/03
  * AWS EC2 서버 구축
  * 도메인 supercola.co.kr
* 03/02
  * Next-Auth 소셜 로그인 추가 (카카오, 구글, 네이버)
* 02/27
  * 로그인 화면 수정
* 02/27
  * 게시글 도배 방지 기능 추가 (3초 동안 연속 등록 불가)
  * 해시태그 검색 기능 추가 (숨겨진 게시글도 검색 가능)
  * 스크롤 탑 버튼 기능
  * 좋아요 중복 버그 수정
* 02/24
  * AWS S3 환경 구축
  * 게시글 사진 등록 기능 구현
  * 게시글 무한 스크롤링 구현
* 02/23
  * 게시글 좋아요 기능 구현
* 02/21
  * Next-Auth 적용
  * 접근 차단 미들웨어 적용
  * 게시글 등록시 해시태그 적용되도록 구현
* 02/20
  * 게시글 등록 기능 구현
* 02/17
  * 회원가입 기능 구현
  * AWS RDS 구축

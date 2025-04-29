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

### 소셜 로그인, 로그아웃 (NextAuth + 카카오/구글/네이버 연동)
카카오와 네이버는 OAuth 2.0 표준을 완벽하게 따르지 않는 부분이 있어 next-auth의 기본 OAuth provider 설정만으로는 정상적으로 연동되지 않았습니다.
특히 카카오는 userinfo API를 별도로 호출하여 이메일, 닉네임 등의 정보를 직접 가져와야 했고, 네이버는 토큰 발급 시 client secret을 추가로 요구하는 방식이 달랐습니다.
이 문제를 해결하기 위해 next-auth에서 제공하는 CredentialsProvider 대신, OAuthProvider를 커스터마이징하여 profile 함수를 오버라이드하고, 필요한 사용자 정보를 수동으로 매핑했습니다.
카카오의 경우 https://kapi.kakao.com/v2/user/me API를 직접 호출하여 id, email, nickname, profile_image 등의 정보를 가져오도록 수정했습니다.
또한 네이버는 토큰 발급 요청 시 client secret을 함께 보내도록 설정을 별도로 조정하여 문제를 해결했습니다.

### 게시글 무한스크롤링, 사진 기능 (Next.js + React Query + JPA + QueryDSL)
무한스크롤을 구현할 때 단순한 offset 페이징은 중간에 데이터가 삽입되거나 삭제되면 리스트 순서가 꼬일 위험이 있기때문에 백엔드에서 커서 기반 페이징을 지원해야 했습니다.
이를 해결하기 위해 QueryDSL을 이용해서 where id < :lastId order by id desc limit :size 형태로 커서 기반 페이징 쿼리를 만들었습니다.
또한 사진 업로드를 다루는 부분에서, multipart/form-data 처리가 Next.js API Routes와 충돌나는 경우가 있어서 어려웠습니다.
프론트엔드에서는 react-query의 useInfiniteQuery를 활용하여 getNextPageParam을 설정하고, 마지막 게시글 ID를 커서로 넘겨 다음 데이터를 요청하는 구조로 구현했습니다.
Next.js의 API Routes는 기본적으로 multipart/form-data 처리를 지원하지 않아, 파일 업로드와 충돌 문제가 발생했습니다.
그래서 클라이언트에서 S3 Presigned URL을 발급받아 직접 파일을 업로드한 뒤, 업로드된 파일 URL만 서버에 전달하는 방식으로 구조를 변경하여 문제를 우회했습니다.

### 좋아요 기능
좋아요를 누를 때마다 서버에 요청을 보내면 성능이 떨어질 수 있고 특히 무한스크롤 중에 좋아요 상태를 유지하는 것도 어려웠습니다.
이때 낙관적 업데이트를 적용했습니다. react-query의 onMutate, onError, onSettled를 사용해서, 좋아요 버튼을 클릭하면 즉시 화면에 반영하고, 실패하면 롤백하도록 처리했습니다.
백엔드에서는 좋아요 테이블(PostLike)을 따로 만들고, 게시글 ID와 사용자 ID의 조합으로 중복 좋아요를 방지했습니다.

### 게시글 숨기기 기능
숨긴 게시글은 리스트에서는 제외되지만, 내가 쓴글 목록에서는 보여야 해서 조회 조건을 동적으로 조정해야 했습니다.
QueryDSL에서 BooleanBuilder를 활용해, 조회하는 상황에 따라 post.hide = false 조건을 걸거나 제거했습니다.
프론트엔드에서는 숨김 상태를 토글할 수 있게 API를 따로 만들었고, react-query의 invalidateQueries를 사용해 게시글 리스트를 새로 갱신했습니다.

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

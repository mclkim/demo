## 점프 투 스프링부트3 책의 소스코드

* 위키독스 : https://wikidocs.net/book/7601


"점프 투 스프링부트"는 "Spring Boot Board(SBB)"라는 이름의 게시판 서비스를 만들어가는 과정을 설명한 스프링부트 입문서이다. 자바 설치부터 시작하여 서비스 운영까지 웹 프로그래밍의 처음부터 끝까지 모든 것을 알 수 있도록 구성하였다.

이 책을 따라하다 보면 다음과 같은 웹 사이트가 만들어진다. (최종 결과물)

* [http://sbb.pybo.kr](http://sbb.pybo.kr)


책을 따라하다 생기는 질문은 위키독스의 댓글 또는 필자가 운영하는 디스코드를 활용하도록 하자.

* 질문과 답변 서비스 "디스코드" - [https://discord.gg/ZwqRRYRYkR](https://discord.gg/ZwqRRYRYkR)

* 이 책을 이해하기 위해 필요한 사전지식
이 책을 읽고 이해하기 위해서는 다음과 같은 사전 지식이 필요하다.

1. 자바 기초 지식 - <점프 투 자바> 책 정도의 내용을 이해할 수 있다면 OK
1. HTML 기초 지식 - <table>, <div>, <form> 태그가 무엇인지 알고 있다면 OK
1. CSS 기초 지식 - 스타일시트 파일을 작성해 본 적이 있다면 OK
1. 자바스크립트 기초 지식 - 자바스크립트를 작성해 본 적이 있다면 OK
1. 폼(form)에 대한 지식 - 브라우저가 서버에 요청을 보낼 때 사용하는 GET, POST 방식의 차이점에 대해 알고 있다면 OK
1. 데이터베이스에 대한 기초 지식 - 테이블이 무엇인지 SQL 쿼리가 무엇을 의미하는지 알고 있다면 OK

이 중에서 데이터베이스에 대한 지식은 필수는 아니다. 스프링부트는 데이터베이스에 대해서 몰라도 객체를 통해 데이터를 처리하기 때문에 데이터베이스에 대해서 몰라도 데이터를 처리할 수는 있다. 하지만 내부적으로 어떻게 동작하는지 잘 이해하려면 데이터베이스에 대한 지식은 큰 도움이 된다.

* 스프링부트 버전
스프링부트(Spring Boot) 3.x 버전을 기준으로 한다. 만약 스프링부트 2.x 버전을 사용해야 한다면 다음의 부록을 참고하자.

* 자바 버전
자바 17 버전을 기준으로 한다. (스프링부트 3.x 버전은 자바 17 이상에서만 동작한다.)

* 부트스트랩 버전
부트스트랩 5.2.3 버전을 기준으로 한다. (5.x.x와 같이 메이저 5 버전대는 호환이 가능하다. 단, 3.x, 4.x 버전의 부트스트랩은 사용할수 없다.)
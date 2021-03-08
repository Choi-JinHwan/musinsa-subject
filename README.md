# 과제 내용
URL을 입력받아 짧게 줄여주고, Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 URL Shortening Service
예) https://en.wikipedia.org/wiki/URL_shortening => http://localhost/JZfOQNro

* URL 입력폼 제공 및 결과 출력
* URL Shortening Key는 8 Character 이내로 생성되어야 합니다.
* 동일한 URL에 대한 요청은 동일한 Shortening Key로 응답해야 합니다.
* 동일한 URL에 대한 요청 수 정보를 가져야 한다(화면 제공 필수 아님)
* Shortening된 URL을 요청받으면 원래 URL로 리다이렉트 합니다.
* Database 사용은 필수 아님

다음의 경우 가산점이 부과됩니다.

* Unit test 및 Integration test 작성.

# 실행 방법

## 리눅스 환경일 경우

프로젝트 루트 디렉토리에서 `./mvnw` 실행

# API문서

http://localhoost:8080/swagger-ui.html

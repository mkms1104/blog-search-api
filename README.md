# blog-search-api


# 개발환경
- Kotlin, Java 11
- Spring boot 2.7.6
- Gradle
- H2

# 프로젝트 실행
```
./gradlew blog-search-api-app:build 
```
```
java -jar ./blog-search-api-app/build/libs/blog-search-api-app.jar
```

# 구현 상세
## 라이브러리 활용
- 부가 기능(명령창 분기 처리, 명령창 표시 등) 구현 시간 단축을 위한 Spring Shell 라이브러리 활용
- 단순한 CRUD 처리를 위한 Spring Data JDBC 활용

## 상품 초기화 (ItemInitRunner)
- 프로젝트 리소스 경로에 상품 csv 파일 보관
- 애플리케이션 로드 시점에 OpenCSV 라이브러리를 통해 상품 파일을 읽어 item 테이블에 동기화

## 주문 및 결제 (OrderCommandService, PaymentService)
- 사용자가 주문한 N개의 상품에 대해 부분 결제는 불가능 하도록 설계 (전부 성공 또는 실패)
- 사용자의 상품 주문과 결제 메서드를 분리하여 결제 메서드에만 트랜잭션 적용 (락 시간 최소화)
- 결제 메서드 진입 시 사용자가 주문한 상품 각각에 대해 pessimistic write lock 획득 (해당 결제 메서드에 먼저 진입한 사용자가 주문 우선순위를 가진다.)
- PayStrategy 인터페이스를 통해 결제 방식 추상화 (현재는 명령창에 단순 출력하는 방식만 존재)

## 동시성 테스트
- N개의 쓰레드를 생성하여 하나의 상품에 대해 결제 메서드를 동시에 호출한다.
- 모든 쓰레드의 결제가 종료된 후 재고 수량을 확인한다.

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

# 서드파티 라이브러리
- io.mockk:mockk:1.13.4 : 코틀린 문법 기반 mock 
- com.ninja-squad:springmockk:3.0.1 : mockito @MockBean, @SpyBean 대체
- com.google.code.gson:gson:2.10.1 : json 파싱
- com.google.guava:guava:31.1-jre : guava 캐시 활용

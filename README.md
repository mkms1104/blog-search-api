# blog-search-api

- 카카오, 네이버 블로그 검색 API를 활용한 인기 키워드 관리

# 개발환경

- Kotlin, Java 11
- Spring boot 2.7.6
- Gradle
- H2

# jar 다운로드 링크
```
https://github.com/mkms1104/blog-search-api/raw/main/blog-search-api-app.jar
```

# 프로젝트 실행

```
./gradlew blog-search-api-app:build 
```

```
java -jar ./blog-search-api-app/build/libs/blog-search-api-app.jar
```

# REST API

### GET /api/v1/search/blog (블로그 검색)

### request

| 파라미터    | 타입      | 설명  | 필수  |
|---------|---------|-----|-----|
| keyword | String  | 키워드    | O   |
| page    | Integer | 페이지 번호    | X   |
| size    | Integer | 페이지 사이즈    | X   |
| sort    | String  | 정렬 기준 컬럼    | X   |

### response

```
{
    "data": [
        {
            "title": "배기<b>청바지</b> 여성 리바이스 세미 밴딩 팬츠",
            "contents": "룩 남자쇼핑몰 남자쇼핑몰추천 남자데일리룩코디 남자데일리룩패션 남자옷코디 남자옷 남자옷맛집 남자패션 남자패션추천 남자배기데님 닉우스터<b>청바지</b> 벌룬<b>청바지</b> 항아리<b>청바지</b> 배기청밴딩 컬러가다했다 취저🫶🏻 허벅지는 여유롭고 발목에서 살짝 모아지는 배기핏입니다! 다잉팬츠 핑크 소라 그린➿ 에노 💙 배시시<b>청바지</b>...",
            "blogName": "나예",
            "url": "http://naayee.tistory.com/90"
        },
        {
            "title": "부츠컷<b>청바지</b>",
            "contents": "택배사는 cj대한통운 입니다 [ 마즈의류 ] 구매하러 가기 MARZ (srookpay.com) 개인적으로 제가 너어무 좋아해서 진짜 닳도록 입고 다니는 부츠컷 <b>청바지</b>인데요 아무도 안사도 되니까 나만 혼자라도 입고 싶다 마음으로 화이트 / 블랙 new컬러 제작했구요 이 바지 입으면 스스로 너무 세련되고 멋있는 여자같은 느낌이...",
            "blogName": "마즈 [MARZ]",
            "url": "https://blog.naver.com/marilyngirl/223049373877"
        },
        {
            "title": "디젤 DIESEL 남자<b>청바지</b> 22FW <b>청바지</b> 남성",
            "contents": "차 카포토그래퍼 기아자동차 첫차 정비사 순정 순정파 노블레스스페셜 디젤 피카부 로고 반팔 티셔츠 A062680AFAA 142,000 DIESEL 디젤 디젤 22FW 남성 <b>청바지</b> 테이퍼드핏 디파이닝 1312310019 162,000 디젤 T-안지 피카부 로고 티셔츠 5컬러 139,000 11주년 디젤 타나즈 슬림핏 <b>청바지</b> 진청 THANAZ 887K 133,510 DIESEL...",
            "blogName": "고려 백",
            "url": "http://gobacktodream.tistory.com/122"
        },
        {
            "title": "포인트 주기 좋은 치트키 <b>청바지</b>",
            "contents": "착장에서 매우 중요하다고 생각하는, 어떻게 보면 가장 중요하다고 까지 생각되는 게 바지입니다. 물론 아우터도 중요하고 이너도 중요하지만 발마칸 코트에 <b>청바지</b>를 입으면 미니멀한 남친룩 코디가 됩니다 하지만 나일론 팬츠나 트레이닝복을 믹스매치한다면 세련되면서 캐주얼한, 스트릿 하기까지 한 착장이 됩니다...",
            "blogName": "B급패션",
            "url": "http://jjjuxxx.tistory.com/29"
        },
        {
            "title": "온앤온 여성 <b>청바지</b> 구입 후기",
            "contents": "봄 시즌 대비한 온앤온 여성 <b>청바지</b> 찬바람이 줄어들면 두꺼운 옷은 정리하고 가볍고 얇은 옷을 빨리 꺼내야 합니다. 항상 봄은 어느새 오기 때문에 다른 시즌과 다르게 입을 옷을 빠르게 준비해야 합니다. 그런데 가지고 있는 바지가 어두운 색이라 이번에 온앤온에서 밝은 <b>청바지</b>를 구입했습니다. 이번에 구입한...",
            "blogName": "인포메이션센터",
            "url": "http://www.nalla.kr/687"
        }
    ],
    "pageInfo": {
        "page": 1,
        "size": 5,
        "sort": "ACCURACY"
    }
}
```

---

### GET /api/v1/search/major-keywords (인기 검색어 목록)

### response

```
{
    "data": [
        {
            "keyword": "청바",
            "searchCnt": 8
        },
        {
            "keyword": "운동",
            "searchCnt": 1
        },
        {
            "keyword": "셔",
            "searchCnt": 1
        },
        {
            "keyword": "셔츠",
            "searchCnt": 1
        }
    ],
    "pageInfo": null
}
```

# 서드파티 라이브러리

- io.mockk:mockk:1.13.4 : 코틀린 문법 기반 mock
- com.ninja-squad:springmockk:3.0.1 : mockito @MockBean, @SpyBean 대체
- com.google.code.gson:gson:2.10.1 : json 파싱
- com.google.guava:guava:31.1-jre : guava 캐시 활용

버스 정보 조회 + 정류장 위치 기반 채팅 시스템
=

* 버스 정보 (정류장 도착정보, 예상 도착시간, 노선 정보, 인접한 정류장 정보 제공 등) API 제공
* 버스 정류장에 인접한 유저끼리 대화할 수 있는 익명 채팅 기능 
<br>

## 개발 인원 👨‍💻
<div align="center">

개인 프로젝트
| **문태진** | 
| :------: | 
| [<img src="https://avatars.githubusercontent.com/u/110981825?s=400&u=90f6ff0a633494f94916237dc912e9eedb225e34&v=4" height=150 width=150> <br/> @TaeJin Moon](https://github.com/DPODW) |

</div>

<br>

## 1. 개발 정보 🖥️ 
### 개발 환경
* Language : JAVA
* Back-End : SpringBoot
* ORM : JPA
* DB : My Sql
* IDE : Intelli J

<br>

## 2. 프로젝트 구조 📂
* 디렉토리 수준으로 구조를 설정하였습니다. (내부 class 는 표시되지 않습니다)

```
├─businfo [버스 정보 API]
│  ├─controller
│  ├─dto
│  │  └─xml
│  ├─entity
│  ├─exception
│  ├─repo
│  │  └─impl
│  ├─response
│  └─service
│      └─impl
├─chat [채팅 기능]
│  ├─config
│  ├─dto
│  └─handler
├─confing [설정 클래스]
│  └─webConfig.java
│  └─querydsl
└─location [위치 기능 API]  
    ├─controller
    ├─dto
    ├─exception
    └─service
```
<br>

## 3. DB 테이블 구조 💾
* BUSSTOP_INFO TABLE : 버스 정류장 정보 
* BUSROUTE_INFO TABLE : 버스 노선 정보 
* 외래키 사용 X

#### 버스 정류장 테이블 (BUSSTOP_INFO)

| 컬럼명             | 타입           | 제약 / 기본값                      | 설명                       |
|------------------|---------------|---------------------------------|--------------------------|
| SEQUENCE         | int           | NOT NULL / Auto Increment / PK   | 순서                       |
| BUSSTOP_ID       | varchar(10)   |                                 | 정류장 ID                  |
| BUSSTOP_NAME     | varchar(25)   |                                 | 정류장 이름                |
| BUSSTOP_LAT      | varchar(15)   |                                 | 정류장 위도 값             |
| BUSSTOP_LON      | varchar(15)   |                                 | 정류장 경도 값             |
| BUSSTOP_MARK     | varchar(50)   |                                 | 정류장 방면 정보           |
| BUSSTOP_ROUTE_ID | varchar(5000) |                                 | 정류장에 멈추는 노선 정보  |
| CREATED_AT       | timestamp     | NOT NULL / CURRENT_TIMESTAMP / DEFAULT_GENERATED | 컬럼 생성 시간 |
| UPDATED_AT       | timestamp     | NOT NULL / CURRENT_TIMESTAMP / DEFAULT_GENERATED | 컬럼 수정 시간 |

<br>

#### 버스 노선 테이블 (BUSROUTE_INFO)

| 컬럼명          | 타입           | 제약 / 기본값                      | 설명               |
|----------------|---------------|---------------------------------|------------------|
| SEQUENCE       | int           | NOT NULL / Auto Increment / PK   | 순서               |
| BRT_ID         | varchar(10)   |                                 | 노선 ID            |
| BRT_NO         | varchar(10)   |                                 | 노선 번호          |
| BRT_NAME       | varchar(50)   |                                 | 노선 이름          |
| DIRECTION      | char(1)       |                                 | 노선 방향          |
| COMPANY        | varchar(30)   |                                 | 노선 회사명        |
| BRT_TYPE       | char(2)       |                                 | 노선 타입          |
| CLASS          | char(1)       |                                 | 노선 구분          |
| STOPST_ID      | varchar(30)   |                                 | 기점 정류장 ID     |
| STOPED_ID      | varchar(30)   |                                 | 종점 정류장 ID     |
| BRT_TIMETABLE  | varchar(5000) |                                 | 노선 시간표        |
| CREATED_AT     | timestamp     | NOT NULL / CURRENT_TIMESTAMP / DEFAULT_GENERATED | 컬럼 생성 시간 |
| UPDATED_AT     | timestamp     | NOT NULL / CURRENT_TIMESTAMP / DEFAULT_GENERATED | 컬럼 수정 시간 |
<br>


## 4. API 출처 📄
- 울산광역시 BIS 정보 (공공 API)
  [https://www.data.go.kr/data/15052669/openapi.do](https://www.data.go.kr/data/15052669/openapi.do)
- **해당 API 인증키가 있어야, 아래에 작성된 API 를 사용할 수 있습니다.**
  
---

## 5. API 설명 🖨

### [버스 정보 저장 API]
- 조회 API를 사용하려면 DB에 버스 정류장, 노선 정보가 저장되어 있어야 합니다.
- 울산광역시 BIS 정보 (공공 API)에서 제공받은 XML 형식의 정보를 파싱해 DB에 저장합니다.


#### 버스 정류장 정보 저장 API 💿
- 요청
  - URL : /bus/stopinfo
  - Method : POST
  - Parameters : X (필요 없음)

- 예시 요청
```http
POST /bus/stopinfo
```
- 응답 (201)
```json
{
  "httpStatus": 201,
  "code": "BUS_STOP-SAVE-SUCCESS-01",
  "message": "요청이 정상적으로 끝났습니다"
}
```
- 버스 정보 저장 API - DB 최종 저장 상태
<img width="2076" height="58" alt="Image" src="https://github.com/user-attachments/assets/9402bf79-d5c6-4e18-a20e-5e6a550e3352" />

----

#### 버스 노선 정보 저장 API 💿
- 요청
  - URL : /bus/routeinfo
  - Method : POST
  - Parameters : X (필요 없음)

- 예시 요청
```http
POST /bus/routeinfo
```
- 응답 (201)
```json
{
  "httpStatus": 201,
  "code": "BUS_ROUTE-SAVE-SUCCESS-01",
  "message": "요청이 정상적으로 끝났습니다"
}
```
- 버스 노선 정보 저장 API - DB 최종 저장 상태
<img width="1619" height="57" alt="Image" src="https://github.com/user-attachments/assets/b0ed7de2-0d1b-44ae-8e65-60ee7faacc14" />
<img width="305" height="54" alt="Image" src="https://github.com/user-attachments/assets/1da1ecfb-8f4b-478e-a7bc-f31597ab7062" />

----

#### 버스 정류장별 노선 정보 저장 API 💿
- 요청
  - URL : /bus/stoprouteinfo
  - Method : POST
  - Parameters : X (필요 없음)

- 예시 요청
```http
POST /bus/stoprouteinfo
```
- 응답 (201)
```json
{
  "httpStatus": 201,
  "code": "BUS_STOP_ROUTE-SAVE-SUCCESS-01",
  "message": "요청이 정상적으로 끝났습니다"
}
```
- 조건 실패 (409)
  - API 제공 정류장 개수와 DB 저장 개수가 다를 때
```json
{
  "status": 409,
  "name": "BUSSTOP_COUNT_MISMATCH",
  "code": "BUS_STOP-ERROR-03",
  "message": "API 제공 정류장 개수와 DB에 저장된 정류장 개수가 다릅니다"
}
```
  - API 제공 노선 개수와 DB 저장 개수가 다를 때
```json
{
  "status": 409,
  "name": "ROUTE_COUNT_MISMATCH",
  "code": "ROUTE-ERROR-01",
  "message": "API 제공 정류장 개수와 DB에 저장된 정류장 개수가 다릅니다"
}
```
- 주의사항
  - 내부적으로 외부 API를 호출하기 때문에 시간 소요가 클 수 있습니다.
  - 데이터가 이미 존재하면, 누락된 부분만 업데이트하므로 시간이 추가로 소요될 수 있습니다.

<br>

-  버스 정류장별 노선 정보 저장 API - DB 최종 저장 상태
-  기존에 저장된 (버스 정류장 정보 저장 API만 실행했을때) 상태에서 BUSSTOP_ROUTE_ID 데이터가 추가됌
<img width="460" height="58" alt="Image" src="https://github.com/user-attachments/assets/2430ee9d-770b-4f60-ab8b-d22981ed12f6" />

----

#### 버스 노선별 시간표 저장 API 💿
- 요청
  - URL : /bus/timetable
  - Method : POST
  - Parameters : X (필요 없음)

- 예시 요청
```http
POST /bus/timetable
```
- 응답 (201)
```json
{
  "httpStatus": 201,
  "code": "BUS_STOP_TIMETABLE-SAVE-SUCCESS-01",
  "message": "요청이 정상적으로 끝났습니다"
}
```

-  버스 노선별 시간표 저장 API - DB 최종 저장 상태
-  기존에 저장된 (버스 노선 정보 저장 API만 실행했을때) 상태에서 BRT_TIMETABLE 데이터가 추가됌
<img width="587" height="56" alt="Image" src="https://github.com/user-attachments/assets/371aead9-dc7a-4c72-8461-271256c8a1de" />

---

### [버스 정보 조회 API]
- 버스 정보가 모두 저장되었다면 조회 API를 호출할 수 있습니다.

---

#### 버스 정류장 검색 API 💿
- 요청
  - URL : /bus/stopinfo/{busStopName}
  - Method : GET

- Parameters

| 파라미터     | 타입   | 필수 여부 | 설명               |
|-------------|-------|-----------|------------------|
| busStopName | String | O         | 검색할 정류장 이름 |


- 예시 요청
```http
GET /bus/stopinfo/우미
```
- 응답 (200)
```json
{
  "busStopId": "196020415",
  "busStopName": "우미린2차 푸르지오2차",
  "busStopX": "129.2419171",
  "busStopY": "35.57314942",
  "busStopMark": "범서파출소 방면",
  "busStopRouteInfoList": [
    {
      "brtName": "318(안전보건공단 동서발전 방면)",
      "brtId": "196900468",
      "busStopStId": "196015417",
      "busStopEdId": "192011415",
      "busStopStName": "삼남신화",
      "busStopEdName": "안전보건공단 동서발전"
    },
    {
      "brtName": "5002(꽃바위 방면)",
      "brtId": "196000457",
      "busStopStId": "196015416",
      "busStopEdId": "194019016",
      "busStopStName": "울산역",
      "busStopEdName": "꽃바위"
    }
  ]
}
```

- 검색 결과 없음 (404)
```json
{
  "status": 404,
  "name": "BUSSTOP_NAME_NOT_FOUND",
  "code": "BUS_STOP-ERROR-02",
  "message": "해당 정류장 이름과 일치하는 정류장 정보가 없습니다.(해당 글자가 포함되는 정류장도 없음)"
}
```
---

#### 실시간 버스 도착정보 검색 API 💿
- 요청
  - URL : /bus/stopinfo/arrival/{busStopId}
  - Method : GET

- Parameters

| 파라미터     | 타입   | 필수 여부 | 설명               |
|-------------|-------|-----------|------------------|
| busStopId | String | O         | 검색할 정류장 아이디 |

- 예시 요청
```http
GET /bus/stopinfo/arrival/196020415
```
- 응답 (200)
```json
[
  {
    "busPrevStopCnt": "6",
    "busArrivalTime": 364,
    "busRouteId": "196000441",
    "busStopId": "196020415",
    "busStopName": "우미린2차 푸르지오2차",
    "nowBusStopName": "입암",
    "busRouteNm": "543(덕하공영차고지(종점) 방면)",
    "busStartTime": null,
    "busStopStName": null
  },
  {
    "busPrevStopCnt": null,
    "busArrivalTime": 0,
    "busRouteId": "196000457",
    "busStopId": null,
    "busStopName": null,
    "nowBusStopName": null,
    "busRouteNm": "5002(꽃바위 방면)",
    "busStartTime": "23:30",
    "busStopStName": "울산역"
  }
]
```

- 검색 결과 없음 (404)
```json
{
  "status": 404,
  "name": "BUSSTOP_NAME_NOT_FOUND",
  "code": "BUS_STOP-ERROR-02",
  "message": "해당 정류장 이름과 일치하는 정류장 정보가 없습니다.(해당 글자가 포함되는 정류장도 없음)"
}
```
---

#### 최대 근접 정류장 검색 API 💿
- 요청
  - URL : /bus/location/user/{latitude}/{longitude}
  - Method : GET

- Parameters

| 파라미터     | 타입   | 필수 여부 | 설명               |
|-------------|-------|-----------|------------------|
| latitude | double | O         | 위도 |
| longitude | double | O         | 경도 |

- 예시 요청
```http
GET /bus/location/user/35.28333284/129.09497371
```
- 응답 (200)
```json
{
  "busStopId": "140000184",
  "busStopName": "노포동역(종점)",
  "busStopLon": 129.09497371,
  "busStopLat": 35.28333284,
  "busStopMark": "노포동역(기점) 방면",
  "busStopRouteInfoList": [
    {
      "brtName": "1154(노포동역(종점) 방면)",
      "brtId": "195000241",
      "busStopStId": "999000149",
      "busStopEdId": "140000184",
      "busStopStName": "명촌차고지(기점)",
      "busStopEdName": "노포동역(종점)"
    }
  ]
}
```
- 조건 실패 (404)
  - 500M 이내 정류장 없음 (LOCATION-ERROR-01)
```json
{
  "status": 404,
  "name": "BUSSTOP_NOT_FOUND_500M_RANGE",
  "code": "LOCATION-ERROR-01",
  "message": "사용자 위치 500M 반경에 정류장이 없습니다."
}
```
  - 50M 이내 정류장 없음 (LOCATION-ERROR-02)
```json
{
  "status": 404,
  "name": "BUSSTOP_NOT_FOUND_50M_RANGE",
  "code": "LOCATION-ERROR-02",
  "message": "사용자 위치 50M 반경에 정류장이 없습니다."
}
```

<br>

## 6. 위치기반 익명 채팅 🖨
* 정류장으로부터 50M 반경 안에 있는 유저들끼리의 채팅 시스템
* WebSocket 을 사용하여 구현
* 50명 제한 , 50M 반경 바깥으로 이동시 채팅방 연결 종료

<br>

#### WebSocket 핸들러
- front-end 에서 호출할 back-end 웹소켓 정보
  - URL : /bus/chat (WebSocket 핸들러 URL)



#### FRONT-END -> BACK-END 
* FRONT-END 에서 BACK-END (busChat - 현재 프로젝트) 로 채팅 데이터를 전송해주어야 합니다.
* JSON 형식과 쿼리스트링으로 데이터를 받습니다.

<br>

* 새로운 연결 (첫 연결)
  * 첫 연결시에는 사용자 현재 위치를 websocket url + 쿼리스트링(위치 정보) 로 전송해주어야 합니다. (첫 연결에만 쿼리스트링으로 위치 정보 전송)
  * JS 기준 geolocation 기능을 사용하여 위치 정보를 얻어올 수 있습니다. (정확도는 떨어짐)
  * 아래는 JS 예시 입니다.
```javascript
new WebSocket(`ws://localhost:8080/bus/chat?latitude=${currentLatitude}&longitude=${currentLongitude}`);
```

<br>


* 입장 메세지 전송
  * 연결 후, 입장 메세지와 유저의 이름 (휘발성 닉네임) 을 서버로 전송해주어야 합니다.
  * 아래는 JS 예시 입니다.
```javascript
socket.onopen = () => { // 소켓 OPEN
   const joinMsg = {
      sender: sender, // 휘발성 닉네임
      message: "입장했습니다." // 입장 메세지
    };
    socket.send(JSON.stringify(joinMsg)); //JSON 형식으로 전송
```

<br>

* 일반 메세지 전송
  * 일반적인 메세지 전송입니다.
  * 아래는 JS 예시 입니다.
```javascript
 const chatMessage = {
        sender: sender, // 휘발성 닉네임
        message: message // 메세지 내용 
    };
    socket.send(JSON.stringify(chatMessage)); //JSON 형식으로 전송
```

<br>

* 퇴장 처리
  * 채팅방 퇴장 처리 입니다.
  * 아래는 JS 예시 입니다.
```javascript
    const closeMsg = {
        sender: sender,
        message: "퇴장하였습니다."
    }; // 퇴장 메세지
    socket.send(JSON.stringify(closeMsg));

    socket.close(); //소켓 닫음
    socket.onclose = () => { // 소켓이 닫히면 알람 (불필요시 삭제 가능)
        alert("바이바이");
    };
```

<br>

* 유저 현재 위치 갱신 메세지
  * 유저가 현재도 정류장 50M 반경 안에 있는지 확인하기 위해, 10초마다 유저의 위치를 서버로 보내주어야 합니다.
  * JSON 형식의 메세지로 전송하되, 일반 메세지와 구분하기 위한 구분코드(validate-001)를 필수적으로 같이 전송해주어야 합니다.
  * 서버에서는 구분코드를 활용하여 일반 메세지 / 검증용 위치 정보를 구분하여 확인합니다. 구분코드 (validate-001)
  * 아래는 JS 예시 입니다.
```javascript
   setInterval(() => {
       const locationInfo = {
            location: "validate-001", // 위치 메세지 구분 코드 (validate-001)
            longitude: currentLongitude, // 경도
            latitude: currentLatitude // 위도
         };
         socket.send(JSON.stringify(locationInfo));
         }, 10000);
```

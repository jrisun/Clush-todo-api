# ToDo API - 할 일 관리 애플리케이션

간단한 할 일 관리 애플리케이션 서비스입니다.<br/>
- 해야할 일을 작성하고 등록하면 수정하고 삭제할 수 있습니다.
- 목록 중 중요한 할 일은 상단에 고정하여 처리할 수 있습니다.
- 완료한 작업은 체크하여 하단에 따로 모아 구분할 수 있습니다.
- 할 일 목록이 없거나 로딩과 관련된 부분도 처리하였습니다.
- Open AI 를 활용한 할 일 목록 요약 및 응원 내용이 출력됩니다.

[Todo List 애플리케이션 링크](https://todo.clearline.click)


## 소스 빌드 및 실행 방법 메뉴얼

### 기술 스택

- **Backend**: Java 17, SpringBoot 3.3.10, MyBatis
- **Database**: MySQL 8
- **Frontend**: React 19 + Vite, HTML, CSS
- **IDE** : VSCode, DBeaver


### 배포 환경

Raspberry5, Docker Compose, AWS Route53

### 실행 방법

아래 링크를 통해서 실행 및 확인 가능합니다. (https 로만 접속 가능합니다.)
- 통합 테스트 링크: [Todo List 애플리케이션 링크](https://todo.clearline.click)


### DB 스키마

| 필드명          | 타입              | 설명                |
|----------------|------------------|---------------------|
| id             | BIGINT (PK)      | 할 일 고유 ID       |
| description    | VARCHAR(100)     | 할 일 설명          |
| is_completed   | BOOLEAN           | 완료 여부           |
| is_fixed       | BOOLEAN           | 고정 여부           |
| created_at     | DATETIME          | 생성 시각           |
| updated_at     | DATETIME          | 수정 시각           |
| completed_at   | DATETIME (nullable) | 완료 시각 (null 가능) |


## 활용한 라이브러리 

- **Spring Boot** : Java 백엔드 프레임워크로, 빠른 개발과 자동 설정을 지원해 RESTful API 구축에 적합하며, 의존성 관리 및 구조화된 설정을 제공합니다. 대부분의 프로젝트에서 많이 활용하는 언어 및 프레임워크로써 일반적인 프레임워크 선택시 최우선 고려사항으로 판단하여 선택하였습니다.
- **MyBatis** : SQL을 직접 작성할 수 있고  Mapper 구조로 단순하고 개발자 중심의 관법과 패턴을 지원합니다. 또한 차후 복잡도가 있는 쿼리 포함시 더 가볍게 진행할 수 있는 관계로 선택하였습니다.
- **React** : 컴포넌트 기반의 선언형 UI 라이브러리로, 프론트엔드에서 효율적인 렌더링을 제공하며 SPA(싱글 페이지 어플리케이션) 구현에 적합합니다. 


## Api 명세

| HTTP Method | URI                       | Description | Request Parameter                      | Response                                                                                                                                                                                                                                                                    |
|-------------|---------------------------|-------------|----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST        | /api/todo                 | 할 일 생성      | description: 할 일의 내용                   | id (number): 할 일 고유 ID<br>description (string): 할 일 내용<br>isCompleted (boolean): 완료 여부 (true: 완료됨 / false: 미완료)<br>isFixed (boolean): 고정 여부 (true: 고정됨 / false: 일반)<br>createdAt (string): 생성 시각 (예: 2025-04-20T20:03:00)<br>completedAt (string | null): 완료 시각, 미완료 시 null |
| GET         | /api/todo                 | 할 일 전체 조회   |                                        | 이하 동일                                                                                                                                                                                                                                                                       |
| PATCH       | /api/todo/{id}            | 할 일 내용 수정   | id: 할 일의 번호<br>description: 할 일의 내용    | 이하 동일                                                                                                                                                                                                                                                                       |
| POST        | /api/todo/{id}/complete   | 할 일 완료 처리   | id: 할 일의 번호<br>isCompleted: 할 일의 완료 여부 | 이하 동일                                                                                                                                                                                                                                                                       |
| POST        | /api/todo/{id}/uncomplete | 할 일 완료 취소   | id: 할 일의 번호<br>isCompleted: 할 일의 완료 여부 | 이하 동일                                                                                                                                                                                                                                                                       |
| POST        | /api/todo/{id}/pin        | 할 일 고정 처리   | id: 할 일의 번호<br>isFixed: 할 일의 고정 여부     | 이하 동일                                                                                                                                                                                                                                                                       |
| POST        | /api/todo/{id}/unpin      | 할 일 고정 해제   | id: 할 일의 번호<br>isFixed: 할 일의 고정 여부     | 이하 동일                                                                                                                                                                                                                                                                       |
| DELETE      | /api/todo/{id}            | 할 일 삭제      | id: 할 일의 번호                            | 이하 동일                                                                                                                                                                                                                                                                       |
 GET      | /api/todo/summary           | 할 일 요약      |                             | data (String): 할 일 요약 내용                                                                                                                                                                                                                                                                      |
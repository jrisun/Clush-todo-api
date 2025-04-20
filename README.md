# ToDo API - 할 일 관리 애플리케이션


> 간단한 할 일 관리 API 서비스입니다.<br/>
> REST API 와 React Frontend 로 구현해 Spring Boot + MyBatis + MySQL 를 활용하여 기본 CRUD를 수행하고, 할 일 목록에서 "고정 / 완료 / 검색” 기능을 추가 구현했습니다.

[Todo List 애플리케이션 링크](https://todo.clearline.click)


## 소스 빌드 및 실행 방법 메뉴얼

### 기술 스택

- **Backend**: Java 17, SpringBoot 3.3.10, MyBatis
- **Database**: MySQL 8.0
- **Frontend**: React 19 + Vite, HTML, CSS
- **IDE** : VSCode, DBeaver


### 배포 환경

라즈베리파이 5, Docker Compose, DDNS

### 실행 방법

아래 링크를 통해서 실행 및 확인 가능합니다.
- 프론트엔드: https://todo.clearline.click
- 백엔드: https://todo-api.clearline.click
- 통합 테스트 링크: [Todo List 애플리케이션 링크](https://todo.clearline.click)
> https 로만 접속 가능합니다.

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

- **Spring Boot** : 설명 및 사용 이유 기입
- **MyBatis** : SQL과 원거리적 배치가 가능하고 통일 복수적 CRUD에 적합
- **React** : 


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

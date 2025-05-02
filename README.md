# 📝 ToDo API - 할 일 관리 애플리케이션

간단한 할 일 관리 애플리케이션 서비스입니다.<br/>
- 해야할 일을 작성하고 등록하면 수정하고 삭제할 수 있습니다.
- 목록 중 중요한 할 일은 상단에 고정하여 관리할 수 있습니다.
- 완료한 작업은 체크하여 하단에 따로 모아 구분할 수 있습니다.
- 할 일 목록이 없을 때나 로딩과 관련된 부분도 처리하였습니다.
- Open AI 를 활용한 할 일 목록 요약 및 응원 내용이 출력됩니다.

🔗 [Todo List 애플리케이션 링크](https://todo.clearline.click)
<br/><br/>


## 🛠 소스 빌드 및 실행 방법 메뉴얼

### 기술 스택

- **Backend**: Java 17, SpringBoot 3.3.10, MyBatis
- **Database**: MySQL 8
- **Frontend**: React 19 + Vite, HTML, CSS
- **IDE** : VSCode, DBeaver


### 배포 환경

Raspberry5, Docker Compose, AWS Route53

### 실행 방법

아래 링크를 통해서 실행 및 확인 가능합니다. (✅ https 로만 접속 가능합니다.)
- 링크: [Todo List 애플리케이션 링크](https://todo.clearline.click)


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
<br/>


## 📦 활용한 라이브러리 

- **Spring Boot** : <br/>
Java 기반의 대표적인 백엔드 프레임워크로써 빠른 프로젝트 초기화와 자동 설정 기능을 제공하여 RESTful API 서버를 효율적으로 구축할 수 있습니다. 또한 의존성 관리가 용이하고 구조화된 계층 아키텍처를 자연스럽게 구성할 수 있어 유지보수성과 확장성이 뛰어납니다. 실제 많은 기업과 서비스의 프로젝트에서 많이 활용되는 언어 및 프레임워크로써 일반적인 프레임워크 선택시 최우선 고려사항으로 판단하여 활용하였습니다.
- **MyBatis** : <br/>
SQL Mapper 기반의 데이터 액세스 프레임워크로, 개발자가 SQL을 직접 제어할 수 있도록 지원합니다. 차후 복잡한 비즈니스 로직이나 고성능 쿼리 작성이 필요할 때 더 가볍고 명확하게 대응할 수 있는 관계로 선택하였습니다.
- **React** : <br/>
JS 기반의 컴포넌트 기반 선언형 UI 라이브러리로, 사용자 인터페이스를 모듈 단위로 나누어 관리할 수 있어 재사용성과 확장성이 높습니다. 
또한 프론트엔드에서 효율적인 렌더링을 제공하며 SPA(Single Page Application) 구조 구현에 적합합니다.
이번 프로젝트에서 기본 개념을 중심으로 학습 및 구현을 진행하였고 앞으로 더 학습하고 확장해나갈 계획입니다.
<br/>


## 📖 Api 명세

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
 GET      | /api/todo/summary           | 할 일 요약      |                             | Summary (String): 할 일 요약 내용                                                                                                                                                                                                                                                                      |
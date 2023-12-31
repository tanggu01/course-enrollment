# Spring short project - Course Enrollment
## 주제 - 수강신청 프로세스

### 전제 조건

- 최소 5개의 강의가 존재해야 하며, 관리자가 강의를 추가할 수 있다.
    - 강의는 API를 통해 확장될 수 있어야 한다.
    - 지우지는 않아도 된다.
    - 강의는 최소 6명의 학생이 필요하고, 최대 15명을 넘을 수 없다.
- 학생은 총 30명이 있다.
    - 학생은 강의를 동시에 3개 까지 수강할 수 있다.
    - 꼭 3개까지 들을 필요는 없지만, 최소 하나 이상은 수강해야 한다.
    - 학생은 신청한 수강목록을 조회할 수 있다.
    - 학생은 수강신청을 철회할 수 있다.
        - 단, 수강신청 마감 3일 전에는 수강신청을 철회할 수 없다.
    - 학생은 수강신청이 열리기 전, 강의의 목록을 조회할 수 있다.
- 3명의 교수가 있다.
    - 교수는 강의를 동시에 2개 까지 강의할 수 있다.
    - 마찬가지로 최소 하나 이상은 강의해야 한다.
    - 교수는 강의하는 강의 목록을 조회할 수 있다.
    - 교수는 강의하기로 한 과목을 철회할 수 없다.
    - 교수는 수강신청이 끝난 이후, 수강을 신청한 학생들을 열람할 수 있다.
        - 이 때, 수강한 강의을 포함한 모든 학생의 정보를 볼 수 있어야 한다.
- 수강신청 프로세스 관리자가 있어야 한다.
    - 관리자는 수강신청 기간을 정해 수강신청 페이즈를 열 수 있다.
        - 최소 2주의 시간을 제한해두며, 최대 4주를 넘어가서는 안된다.
    - 해당 수강신청 기간은 변경되어질 수 있어야 한다.
    - 수강신청을 열기 전, 교수들을 통해 강의를 열 수 있도록 해야 한다.
    - 강의의 모든 정보를 조회할 수 있어야 한다.

### 기술 조건

- 데이터는 모두 DB로 관리되어야 한다.
    - RDBMS를 사용해야 한다.
- 모든 REST API는 각각의 리소스 별로 관리가 되어야 한다.
    - ex - 학생 /student/**, 교수 /professor/** …. etc
- MVC패턴을 지켜야 한다.
- 동시 수강신청에 대해 처리할 수 있어야 한다.
- 동시접속자는 10명으로 제한되어있다.
- JDK 11~17 사이의 버전을 사용해야 한다.
- 적절한 HTTP Method를 활용해야 한다.
- 각 역할에 맞는 로그인을 구현해야 한다.
    - 학생/교수/관리자 로 나뉘어야 하며, 복수역할은 존재할 수 없다.
    - 인증/인가방식은 토큰으로 구현한다.
- Spring과 Spring Boot의 최소 버전은 5(Spring framewokr), 2.7(Spring boot)로 제한한다.
- 각 Controller method와 Service method에는 주석을 달아 설명을 작성한다.(Java doc)

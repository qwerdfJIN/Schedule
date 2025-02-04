## Schedule

<br>

***레벨별 과제내용**

#### Lv 0. API 명세 및 ERD 작성

- API 명세서 및 ERD 작성하기

![API 명세서](images/Schedule_API&ERD.jpg)


#### Lv 1. 일정 생성 및 조회

- **일정 생성(일정 작성하기)**

  - `할일`, `작성자명`, `비밀번호`, `작성/수정일` 저장
  - `작성/수정일`은 날짜와 시간을 모두 포함한 형태

  - 각 일정의 고유 식별자(ID)를 자동으로 생성하여 관리

  - 최초 입력 시, 수정일은 작성일과 동일

    

- **전체 일정 조회(등록된 일정 불러오기)**

  - 다음 조건을 바탕으로 등록된 일정 목록을 전부 조회

    - `수정일` (형식 : YYYY-MM-DD)
    - `작성자명`

  - 조건 중 한 가지만을 충족하거나, 둘 다 충족을 하지 않을 수도, 두 가지를 모두 충족할 수도 있습니다.

  - `수정일` 기준 내림차순으로 정렬하여 조회

    

- **선택 일정 조회(선택한 일정 정보 불러오기)**

  - 선택한 일정 단건의 정보를 조회할 수 있습니다.

  - 일정의 고유 식별자(ID)를 사용하여 조회합니다.

    

#### Lv 2. 일정 수정 및 삭제

- 선택한 일정 수정
  - 선택한 일정 내용 중  `할일`, `작성자명`만 수정 가능
    - 서버에 일정 수정을 요청할 때 `비밀번호`를 함께 전달합니다.
    - `작성일` 은 변경할 수 없으며, `수정일` 은 수정 완료 시, 수정한 시점으로 변경합니다.
- 선택한 일정 삭제
  - 선택한 일정을 삭제할 수 있습니다.
    - 서버에 일정 수정을 요청할 때 `비밀번호`를 함께 전달합니다.



***과제 진행현황**

Lv 0 - Lv 2: 완료
Lv 3 - Lv 6: 미완료



<br>

***트러블슈팅**

https://qwerdfjin.tistory.com/16
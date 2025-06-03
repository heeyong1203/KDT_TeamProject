# 🧑‍💻 Team Project Repository

이 저장소는 팀 프로젝트를 위한 GitHub 협업 저장소입니다.  
모든 작업은 개별 브랜치에서 진행하며, Pull Request를 통해 `main` 브랜치에 병합합니다.

=> 이후 변경 가능성 있음.

---

## 📦 프로젝트 클론 및 실행 방법

아래 명령어를 통해 이 저장소를 로컬에 클론하고 작업을 시작할 수 있습니다.

```bash
# 저장소 클론
git clone https://github.com/ABCchocolate/KDT_TeamProject.git

# 저장소 디렉토리로 이동
cd KDT_TeamProject
````
---

## 🛠️ Git 브랜치 전략 및 작업 가이드

### 🔀 브랜치 생성 규칙

작업은 반드시 각자의 브랜치에서 진행해주세요.
브랜치 이름은 아래 예시처럼 작성합니다:

```bash
# 브랜치 생성 (예: 박유신 로그인 기능 작업 시)
git checkout -b feature/login-yushin
```

**브랜치 이름 규칙 예시**
 __회의를 통해 변경될 수 있음__
* `feature/기능명-이름` → 새 기능 개발
* `fix/버그내용-이름` → 버그 수정
* `refactor/내용-이름` → 코드 리팩터링

---

### 📤 작업 후 커밋 & 푸시

```bash
git add .
git commit -m "기능: 로그인 폼 구현"
git push origin feature/login-yushin
```

**커밋 메시지 컨벤션 예시**

* `기능: ~` → 새로운 기능 추가
* `수정: ~` → 기존 기능 수정
* `버그: ~` → 오류 수정
* `리팩토링: ~` → 코드 개선

---

### 🔁 병합(Merge)은 Pull Request로

모든 작업은 Pull Request(PR)를 통해 `main` 브랜치로 병합합니다.
직접 `main`에 푸시하지 마세요.

PR 작성 시 포함할 항목:

* 작업 요약
* 주요 변경 사항
* 이슈 번호 또는 참고사항 (있다면)

---

## 🙋 협업자 목록
## 👥 Collaborators

| 이름 | 역할 | GitHub |
|------|------|--------|
| 박유신 | ERD 작성 , 레이아웃 설계 , 레이아웃 구현| [@ABCchocolate](https://github.com/ABCchocolate) |
| 김소희 | ERD 작성 , 레이아웃 설계| [[@Kim-sohee](https://github.com/Kim-sohee) |
| 문창규 | ERD 작성 , DB 연동 | [@Choo-Choo-Moon](https://github.com/Choo-Choo-Moon) |
| 류희용 | ERD 작성 , 레이아웃 설계 , 레이아웃 구현| [@heeyong1203](https://github.com/heeyong1203) |

## 📦 기술 스택

| 범주     | 기술       | 설명                  |
| ------ | -------- | ------------------- |
| 언어     | `Java`   | 백엔드 로직 및 API 개발에 사용 |
| 데이터베이스 | `MySQL`  | 관계형 데이터 저장 및 관리     |
| 형상 관리  | `GitHub` | 버전 관리 및 협업용 코드 저장소  |
| 커뮤니케이션 | `Slack`  | 팀 커뮤니케이션 및 작업 공유    |


---
## 📅 프로젝트 일정

| 기간 | 내용 |
|------|------|
| 6/1 ~ 6/16 | UI,ERD 기획 및 디자인 |
| 6/16 ~ 6/30 | 개발 (기능 완성) |
| 6/30| 팀 프로젝트 발표 |

----

## 📎 기타 참고 사항
1. 문제가 생겼을 때(바로 해결할 수 없는 문제) 바로 말하기
2. SLACK 확인 잘하기

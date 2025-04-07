# Escaper
미로탈출게임, 스마트폰게임프로그래밍 프로젝트
게임 컨셉
플레이어가 NPC의 추격을 따돌리며 미로를 탈출하는 퍼즐게임
4방향 조이스틱으로 캐릭터를 조작할 수 있다.
시작지점에서 출발하여 도착지점에 도달하는것이 목적이며 도중에 NPC에게 잡힐경우 게임 오버로 시작지점으로 돌아간다.
중간에 아이템을 획득하면 추가 점수를 받는다.
도착지점에 도달하면 다음 스테이지로 이동한다.


개발범위
1. 미로
미로 크기: 10*10 타일 맵 구조
미로 종류: 사전에 정의한 맵 3종류 사용

2. 플레이어
이동: 화면 슬라이드를 이용하여 상하좌우 이동

3. NPC
이동: 미리 설정된 경로를 따라 일정 범위내에서 이동(Path 이용)
수: 맵에 따라 1~3명 배치
속도: 플레이어와 동일

4. 아이템
수: 맵에 따라 0~5개 배치
획득 점수: 아이템의 종류에 따라 각각 100점, 200점, 300점으로 분류

5. UI 구성
시작화면: 시작, 종료, 리더보드 버튼으로 구성
   시작버튼->게임화면으로 이동
   종료버튼->종료
   리더보드 버튼->리더보드를 표시
게임화면: 게임뷰, 일시정지 버튼으로 구성
   게임뷰: 게임프레임워크를 이용하여 구성. 미로, 플레이어 캐릭터 등 게임요소 표시
   일시정지 버튼: 누를시 게임뷰의 모든것이 멈추며 화면 중앙에 재시작, 종료 버튼 표시
      재시작: 일시정지 이전의 상태로 돌아간다.
      종료: 시작화면으로 돌아간다.
   조이스틱: 플레이어 이동을 위한 상하좌우 4방향, 4개의 버튼으로 구성


게임 실행 흐름
### 시작화면 ###
![Image](https://github.com/user-attachments/assets/da2e0450-4744-42bc-9c88-2df6592d1436)

시작버튼 클릭-> 게임화면으로 이동
리더보드 버튼 클릭 -> 리더보드 화면으로 이동
종료버튼 클릭 -> 앱 종료

### 게임화면 ###
![Image](https://github.com/user-attachments/assets/ae946dc7-3031-4559-95dc-6f579c18c18a)

일시정지 버튼 -> 게임 일시정지 및 일시정지 메뉴 표시
화면 슬라이드 -> 플레이어 캐릭터 이동

### 일시정지 메뉴 ###
![Image](https://github.com/user-attachments/assets/70a4ede5-12ae-40be-b797-4db5fc9a8d4e)

구현시에는 resume->'이어서 하기', give up->'종료하기' 로 수정 예정
이어서하기 -> 일시정지 해제 후 재개
종료하기 -> 시작화면으로 이동

### 플레이어 캐릭터 이동 설명 ###
화면(어디든지)을 슬라이드하면 그 방향으로 캐릭터가 한 칸 이동

### 이동 화면 예시 ###
화면을 터치하면 그 방향에 흐릿한 원이 생기고 슬라이드하면 그대로 따라간다.

### 평상시(터치 입력이 없을때) ###
![Image](https://github.com/user-attachments/assets/7ff8b3e7-f3f7-4b09-93e5-795acb217034)

### 터치 시(화면 오른쪽에 원 표시) ###
![Image](https://github.com/user-attachments/assets/581a0461-f3b8-47cd-baba-e47eaaace8b5)

개발 일정
1주차: 미로 맵 디자인, 조이스틱 이동 구현 시작
2주차: 조이스틱 이동 구현
3주차: NPC, 아이템 추가
4주차: NPC, 아이템 충돌감지
5주차: 점수 계산 추가
6주차: 최고 점수를 기록하는 리더보드 구현
7~8주차: 테스트 및 디버깅
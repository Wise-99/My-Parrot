# 🦜 우앵귀 - 우리 앵무새 귀엽죠?

## 📖 프로젝트 소개
| 메인 및 메뉴 화면 | 지도 화면 | 게시글 작성 화면 | 댓글 화면 |
|--|--|--|--|
| ![메인 및 메뉴 화면](https://file.notion.so/f/s/c94062a2-5a38-469a-8206-21e4e68bb91e/Untitled.png?id=c47bcecc-384f-485e-8d76-9952594396a9&table=block&spaceId=4fee607c-9fab-47df-96d0-8ba12808c88d&expirationTimestamp=1687421112790&signature=g38hyUSPFb8gRTGEow2DQl2IkLmbBvCVvDu8q8OF__s&downloadName=Untitled.png) | ![지도 화면](https://github.com/Wise-99/My-Parrot/assets/90273263/5c35cd61-c4a6-4381-b871-01d82042b984) | ![게시글 작성 화면](https://file.notion.so/f/s/476eaa97-cef9-4add-8c93-8ff792f75ea7/Untitled.png?id=19c9dc9b-2e0d-4610-b4a8-680eebf35b3f&table=block&spaceId=4fee607c-9fab-47df-96d0-8ba12808c88d&expirationTimestamp=1687421119533&signature=tOkYmu_7fuSOdvhuswdH-SLFMoEyCZspuKP7FshWU9Y&downloadName=Untitled.png) | ![댓글 화면](https://file.notion.so/f/s/6c686914-d20d-41db-ab72-0dd5ea67ea32/Untitled.png?id=da7e4fe5-0159-4895-b5b9-37f4deeaba48&table=block&spaceId=4fee607c-9fab-47df-96d0-8ba12808c88d&expirationTimestamp=1687421122838&signature=iyiZX18-krGIiAs3bIN3MPY1paPXgN4s9eOqvGkttGQ&downloadName=Untitled.png) |

> 🦜 앵무새를 키우는 사람들이 정보를 주고 받을 수 있는 **앵무새 커뮤니티 앱**입니다. 정보 공유 뿐만 아니라 **병원 정보, 다양한 후기, 분양**까지 할 수 있도록 탭을 구성하였습니다. 해당 앱은 **로그인**을 해야 이용할 수 있습니다.
> 
> -   사용자의 위치를 받아 가장 가까운 **동물 병원의 위치, 주소**를 알 수 있습니다.
> -   게시글의 **추천** 수를 통해 세운 **랭킹**을 확인할 수 있습니다. 추천 수가 같다면 **조회수**를 비교합니다.
> -   사용자는 **글**을 쓰거나 수정, 삭제가 가능하며 사용자가 쓴 글을 모아 볼 수 있습니다.
> -   글 작성 시 **사진은 5장**까지 업로드 가능하며 **미리 보기**를 통해 올릴 사진을 확인할 수 있습니다.
> -   사용자는 **댓글**을 작성하고 삭제할 수 있습니다.

## 📄 담당 업무

-   각 회원 별로 정보를 관리하여 **사용자가 쓴 글을 모아 확인** 가능
    -   랭킹, 공지, 후기, 분양 등등 주제별 **TabLayout**을 구성하여 글들을 분리
- 각 글의 **추천 기능과 조회 수** 기능 삽입하여 랭킹 게시판 생성
    - 추천 버튼 클릭 또는 글 조회 시 Firebase 글의 조회, 추천 횟수 수정 및 저장
    - 추천 수가 같다면 조회 수를 비교하여 순위 지정
- **Glide**를 통해 하나의 글에 **5장의 사진 업로드** 가능
    - 글 작성 화면의 ImageView에 업로드하는 방식의 **미리보기**를 통해 어떤 사진이 글과 함계 저장되는지 확인 가능
- **ScrollView**를 이용하여 게시글과 댓글을 한 화면에서 볼 수 있도록 구현
- **Google Map API**를 이용하여 사용자의 위치에서 꽃집 위치 확인 구현
    - 위치 사용 권한 확인 후 사용자의 현재 위치 표시
    - **Google Places API**를 이용하여 사용자의 위치로부터 1000m 이내의 동물 병원 위치 마커로 표시
    - **Geocoder**를 통해 동물 병원의 위치 좌표를 주소로 변환(**역 지오코딩**)하여 주소를 마커에 추가 표시

## 💡 문제 해결 과정

-   스크롤을 해도 화면에 고정되는 수정, 삭제 버튼을 만들고자 함
    -   TextView나 Button을 사용하지 않고 **Floating Action Button**을 사용하여 위치를 고정시킴
    -   이를 이용하여 게시글을 보기 위해 스크롤을 내려서도 글 삭제, 수정 버튼을 이용할 수 있도록 함
-   리스트를 구현하는 View에는 ListView와 **RecyclerView**가 있지만 어느 것을 사용해야할지 고민함
    -   ListView는 재사용성과 커스텀 기능이 떨어지며 성능이 저하됨
        ⇒ **RecyclerView**를 활용하여 재사용성을 높이고 커스텀하여 게시글 목록을 구현함
-   동물 병원 위치를 표시하기 위해 카카오 Map을 적용해봤으나 오류 발생
-   우회하여 네이버 Map을 사용했으나 검색, 좌표 변환을 지원하지 않음
-   구글은 좌표 변환, **Places API**(장소 검색)를 지원함 ⇒ 동물 병원의 위치와 주소를 나타내기 위해 사용
  -   좌표 → 주소 변환은 **Geocoder**를 이용하여 변환

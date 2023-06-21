# 🦜 우앵귀 - 우리 앵무새 귀엽죠?

## 📖 프로젝트 소개
| 메인 및 메뉴 화면 | 지도 화면 | 게시글 작성 화면 | 댓글 화면 |
|--|--|--|--|
| ![메인 및 메뉴 화면](https://file.notion.so/f/s/c94062a2-5a38-469a-8206-21e4e68bb91e/Untitled.png?id=c47bcecc-384f-485e-8d76-9952594396a9&table=block&spaceId=4fee607c-9fab-47df-96d0-8ba12808c88d&expirationTimestamp=1687421112790&signature=g38hyUSPFb8gRTGEow2DQl2IkLmbBvCVvDu8q8OF__s&downloadName=Untitled.png) | ![지도 화면](https://file.notion.so/f/s/63193d39-3c0f-4d36-b93e-48b613ff3327/Untitled.png?id=df7f1a55-beb0-41ac-8e88-3d88cf147c89&table=block&spaceId=4fee607c-9fab-47df-96d0-8ba12808c88d&expirationTimestamp=1687421116294&signature=9SguiPAZK-kjbYV5t2jBB9aWcI_NzoymfQfoXXXDr3M&downloadName=Untitled.png) | ![게시글 작성 화면](https://file.notion.so/f/s/476eaa97-cef9-4add-8c93-8ff792f75ea7/Untitled.png?id=19c9dc9b-2e0d-4610-b4a8-680eebf35b3f&table=block&spaceId=4fee607c-9fab-47df-96d0-8ba12808c88d&expirationTimestamp=1687421119533&signature=tOkYmu_7fuSOdvhuswdH-SLFMoEyCZspuKP7FshWU9Y&downloadName=Untitled.png) | ![댓글 화면](https://file.notion.so/f/s/6c686914-d20d-41db-ab72-0dd5ea67ea32/Untitled.png?id=da7e4fe5-0159-4895-b5b9-37f4deeaba48&table=block&spaceId=4fee607c-9fab-47df-96d0-8ba12808c88d&expirationTimestamp=1687421122838&signature=iyiZX18-krGIiAs3bIN3MPY1paPXgN4s9eOqvGkttGQ&downloadName=Untitled.png) |

> 🦜 앵무새를 키우는 사람들이 정보를 주고 받을 수 있는 **앵무새 커뮤니티 앱**입니다. 정보 공유 뿐만 아니라 **병원 정보, 다양한 후기, 분양**까지 할 수 있도록 탭을 구성하였습니다. 해당 앱은 **로그인**을 해야 이용할 수 있습니다.
> 
> -   사용자의 위치를 받아 가장 가까운 **동물 병원의 위치, 주소**를 알 수 있습니다.
> -   게시글의 **추천** 수를 통해 세운 **랭킹**을 확인할 수 있습니다. 추천 수가 같다면 **조회수**를 비교합니다.
> -   사용자는 **글**을 쓰거나 수정, 삭제가 가능하며 사용자가 쓴 글을 모아 볼 수 있습니다.
> -   글 작성 시 **사진은 5장**까지 업로드 가능하며 **미리 보기**를 통해 올릴 사진을 확인할 수 있습니다.
> -   사용자는 **댓글**을 작성하고 삭제할 수 있습니다.

## 📄 담당 업무

-   Java를 이용하여 스크롤 뷰, 리사이클러 뷰 등 앱 화면 구성
-   각 회원 별 글 작성, 삭제, 수정 및 추천 기능 구현
-   사용자의 현재 위치를 받아 지도에 동물병원 위치 확인 기능 구현
-   FireBase를 이용한 실시간 데이터 베이스 구축 및 운영

## 💡 깨달은 점

-   액션바 보다는 **Toolbar**(툴바)를 사용함으로써 모양 및 다양한 기능을 부여할 수 있음
-   **플로팅 액션 버튼**을 사용하여 위치가 고정된 버튼을 만들 수 있음
    -   이를 이용하여 게시글을 보기 위해 스크롤을 내려서도 글 삭제, 수정 버튼을 이용할 수 있도록 함
-   리스트를 구현하는 View에는 ListView와 **RecyclerView** 가 있다.
    -   ListView는 재사용성과 커스텀 기능이 떨어지며 성능이 저하됨
        ⇒ **RecyclerView**를 활용하여 재사용성을 높이고 커스텀하여 게시글 목록을 구현함
-   네이버, 카카오, **구글** 모두 Map API를 지원함
    -   그러나 카카오 지도 API는 에뮬레이터에서 실행되지 않으며, 네이버 지도는 검색, 좌표 변환을 지원하지 않음
    -   구글은 좌표 변환, **Places API**(장소 검색)를 지원함 ⇒ 동물 병원의 위치와 주소를 나타내기 위해 사용
        -   좌표 → 주소 변환은 **Geocoder**를 이용하여 변환

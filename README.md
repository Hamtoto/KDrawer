# KDrawer

## 0. 목차

1. [프로젝트 소개](#1-프로젝트-소개)
2. [팀 소개](#2-팀-소개)
3. [기술스택](#3-기술스택)
4. [프로젝트 구성 및 아키텍처](#4-프로젝트-구성-및-아키텍처)
5. [스크린샷](#5-스크린샷)
6. [다운로드](#6-Download)
   
## 1. 프로젝트 소개
+ KDrawer는 디자인 및 프로토타이핑 협업툴입니다.
+ 다양한 그림 객체를 사용할 수 있으며 생성된 객체를 수정하거나 복사 또는 삭제 기능을 제공합니다.
+ Talk 기능을 통해 사용자 간의 실시간 채팅 기능 사용할 수 있습니다.
+ 작업물을 사용자 간 전송 또는 수신할 수 있으며 실시간으로 공유하는 기능도 포함하고 있습니다.

## 2. 팀 소개

|                                                                                    **김정우**                                                                                    |                                                                                                  **고현규**                                                                                                  |                                                                                                                                         **김동혁**     
| :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | 
|                         <img src="https://avatars.githubusercontent.com/u/85924877?v=4" height=180 >                         |                                       <img src="https://avatars.githubusercontent.com/u/100178817?v=4" height=180 >                                       |                                 <img src="https://avatars.githubusercontent.com/u/75555609?v=4" height=180 >                                 |
| [🔗 GitHub](https://github.com/jungwu2503)<br/>  | [🔗 GitHub](https://github.com/Hamtoto)<br/>  | [🔗 GitHub](https://github.com/BaSak0630)<br/>  |

## 3. 기술스택
<table>
<tr>
 <td align="center" width="100px">사용 언어</td>
 <td width="800px">
 <img src="https://img.shields.io/badge/JAVA-007396?style=flat&logo=Java&logoColor=white"/>
    </td>
</tr>
<tr>
 <td align="center">협업</td>
 <td>
    <img src="https://img.shields.io/badge/kakaotalk-yellow?style=flat&logo=kakaotalk&logoColor=FFFFFF"/>
<img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=FFFFFF"/>
<img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=FFFFFF"/> 

 </td>
<tr>
 <td align="center">IDE</td>
 <td>
    <img src="https://img.shields.io/badge/VSCode-007ACC?style=flat&logo=Visual%20Studio%20Code&logoColor=white"/>
    <img src="https://img.shields.io/badge/intellij idea-000000?style=flat&logo=IntelliJ%20IDEA&logoColor=white"/>
</tr>
</table>
 
## 4. 프로젝트 구성 및 아키텍처 

<details>
    <summary>프로젝트 구조</summary>

```
├── fig
│    ├── KArrow.java
│    ├── KBox.java
│    ├── KCircle.java
│    ├── KCurve.java
│    ├── KDiamond.java
│    ├── KEraser.java
│    ├── KFigure.java
│    ├── KImage.java
│    ├── KLine.java
│    ├── KLinearFigure.java
│    ├── KLines.java
│    ├── KOnePointFigure.java
│    ├── KPoint.java
│    ├── KRightTriangle.java
│    ├── KScribble.java
│    ├── KSelector.java
│    ├── KStar.java
│    ├── KText.java
│    ├── KTriangle.java
│    └── KTwoPointFigure.java
├── gui
│    ├── ColorAction.java
│    ├── Drawer.java
│    ├── DrawerFrame.java
│    ├── DrawerView.java
│    ├── FigureIcon.java
│    ├── MouseAction.java
│    ├── MouseGrabber.java
│    ├── noname.jdr
│    ├── SelectAction.java
│    ├── StatusBar.java
│    ├── StringMap.java
│    ├── dlg
│    │    ├── FigureDialog.java
│    │    ├── FontDialog.java
│    │    ├── InfoDialog.java
│    │    ├── TableDialog.java
│    │    └── TreeDialog.java
│    └── popup
│          ├── .editorconfig
│          ├── KArrowPopup.java
│          ├── KBoxPopup.java
│          ├── KCirclePopup.java
│          ├── KColorSubmenu.java
│          ├── KCurvePopup.java
│          ├── KDiamondPopup.java
│          ├── KEraserPopup.java
│          ├── KFigurePopup.java
│          ├── KGroupPopup.java
│          ├── KImagePopup.java
│          ├── KLinePopup.java
│          ├── KLinesPopup.java
│          ├── KMainPopup.java
│          ├── KObjectPopup.java
│          ├── KPointPopup.java
│          ├── KPopup.java
│          ├── KRightTrianglePopup.java
│          ├── KScribblePopup.java
│          ├── KStarPopup.java
│          ├── KTextPopup.java
│          └── KTrianglePopup.java
├── image
│     ├── exit.png
│     ├── logo.png
│     └── new.gif
│          .
│          .
│          .
└── net
     ├── ButtonPanel.java
     ├── ChatPanel.java
     ├── InputPanel.java
     ├── KTalkDialog.java
     ├── MainPanel.java
     ├── Message.java
     └── ServerRole.java
```     
</details>

<details>
    <summary>클래스 다이어그램</summary>
  
![Gui class diagram](https://github.com/KDrawer/KDrawer/assets/100178817/5b18abdd-30bb-44a1-ab5f-42b949a704e1)
![figure class diagram](https://github.com/KDrawer/KDrawer/assets/100178817/4fc56689-fe7f-4b61-8df2-75573fa20eae)
![popup class diagram](https://github.com/KDrawer/KDrawer/assets/100178817/ad8f3be2-1419-4f3b-a077-3c10e8c4782c)
</details>

## 5. 스크린샷
#### 1.초기화면
![KDrawer - 초기화면](https://github.com/KDrawer/KDrawer/assets/100178817/d83b4137-405e-456c-9e29-a2831eec23c0)

#### 2. 메뉴
![KDrawer - 그림 메뉴](https://github.com/KDrawer/KDrawer/assets/100178817/56915d91-4367-4e9b-8bd5-70bb2071d192)
![KDrawer - 도구 메뉴](https://github.com/KDrawer/KDrawer/assets/100178817/5478e563-73db-42cc-979d-644e2cbbc160)

#### 3. 기능
![KDrawer - 그리기 대화상자](https://github.com/KDrawer/KDrawer/assets/100178817/b158d11b-8ec7-4690-b9a0-201ccbc2419a)
![KDrawer - 테이블](https://github.com/KDrawer/KDrawer/assets/100178817/d8418066-3d17-47c7-8f46-54d93730c6a2)
![KDrawer - 트리](https://github.com/KDrawer/KDrawer/assets/100178817/f0ecc368-6553-449d-9810-cdd96ac2aa43)
![KDrawer - talk 5](https://github.com/KDrawer/KDrawer/assets/100178817/6cb40b3b-9938-43e1-a307-a88730d0de0d)

#### 4. Talk
![KDrawer - talk 1](https://github.com/KDrawer/KDrawer/assets/100178817/051de79f-148c-4cb8-8989-bb3f8c72d11e)
![KDrawer - talk 2](https://github.com/KDrawer/KDrawer/assets/100178817/c0f9b355-6ceb-49a2-b2e6-3f6169862870)
![KDrawer - talk 3](https://github.com/KDrawer/KDrawer/assets/100178817/240f4ffe-9dca-46de-ac2e-4b476527b043)
![KDrawer - talk 4](https://github.com/KDrawer/KDrawer/assets/100178817/8e6d3c1d-ab5a-4c53-92ea-ea34b7762efd)

## 6. Download
[Link](https://github.com/KDrawer/KDrawer/releases/tag/0.1)


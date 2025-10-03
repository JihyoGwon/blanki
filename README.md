# Blanki - Firebase 구글 로그인 안드로이드 앱

Firebase Authentication을 사용한 구글 로그인 기능이 포함된 안드로이드 앱입니다.

## 🚀 시작하기

### 1. Firebase 프로젝트 설정

1. [Firebase Console](https://console.firebase.google.com/)에 접속
2. "프로젝트 추가" 클릭
3. 프로젝트 이름을 "Blanki"로 설정
4. Google Analytics는 선택사항 (필요시 체크)

### 2. Android 앱 추가

1. Firebase 프로젝트에서 "Android" 아이콘 클릭
2. 패키지 이름: `com.blanki.app` 입력
3. 앱 닉네임: `Blanki` 입력
4. SHA-1 인증서 지문 추가 (아래 참조)

### 3. SHA-1 인증서 지문 얻기

#### Windows (PowerShell):
```powershell
keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
```

#### macOS/Linux:
```bash
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

### 4. google-services.json 파일 다운로드

1. Firebase Console에서 "google-services.json" 파일 다운로드
2. 이 파일을 `app/` 폴더에 복사

### 5. Web Client ID 설정

1. Firebase Console > 프로젝트 설정 > 일반 탭
2. "Web API 키" 섹션에서 "Web 클라이언트 ID" 복사
3. `app/src/main/res/values/strings.xml` 파일에서 `YOUR_WEB_CLIENT_ID_HERE`를 실제 Web Client ID로 교체

### 6. Google Sign-In 활성화

1. Firebase Console > Authentication > Sign-in method
2. "Google" 제공업체 클릭
3. "사용 설정" 토글을 켜기
4. 프로젝트 지원 이메일 설정
5. "저장" 클릭

## 📱 앱 실행

1. Android Studio에서 프로젝트 열기
2. 에뮬레이터 또는 실제 기기에서 앱 실행
3. "Sign in with Google" 버튼 클릭하여 로그인 테스트

## 🔧 주요 기능

- Firebase Authentication을 통한 구글 로그인
- 사용자 정보 표시
- 로그아웃 기능
- 간단하고 직관적인 UI

## 📁 프로젝트 구조

```
app/
├── src/main/
│   ├── java/com/blanki/app/
│   │   └── MainActivity.java          # 메인 액티비티
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml     # 메인 레이아웃
│   │   └── values/
│   │       ├── strings.xml           # 문자열 리소스
│   │       ├── colors.xml            # 색상 리소스
│   │       └── themes.xml            # 테마 리소스
│   └── AndroidManifest.xml           # 앱 매니페스트
├── build.gradle                      # 앱 레벨 Gradle 설정
└── google-services.json             # Firebase 설정 파일 (다운로드 필요)
```

## ⚠️ 주의사항

- `google-services.json` 파일은 실제 Firebase 프로젝트에서 다운로드해야 합니다
- `strings.xml`의 `default_web_client_id`를 실제 값으로 교체해야 합니다
- SHA-1 인증서 지문을 Firebase에 등록해야 구글 로그인이 작동합니다

## 🆘 문제 해결

### 로그인이 안 될 때:
1. SHA-1 인증서 지문이 Firebase에 등록되었는지 확인
2. `google-services.json` 파일이 올바른 위치에 있는지 확인
3. Web Client ID가 올바르게 설정되었는지 확인

### 빌드 에러가 날 때:
1. Android Studio에서 "Sync Project with Gradle Files" 실행
2. "Clean Project" 후 "Rebuild Project" 실행
# Core

마인크래프트 멀티서버 환경을 위한 통합 플러그인 프레임워크입니다.
Bukkit(Spigot/Paper)과 BungeeCord 플랫폼을 모두 지원합니다.

## 기술 스택

- **Java 8** / **Kotlin 1.7**
- **Gradle** (Shadow JAR)
- **MariaDB** (HikariCP 커넥션 풀)
- **Redis** (Lettuce, 서버 간 통신)
- **Paper API 1.8.8** / **BungeeCord API 1.20**
- **ProtocolLib 4.8.0**

## 주요 모듈

| 모듈 | 설명 |
|------|------|
| **AnnoCommand** | 어노테이션 기반 커맨드 시스템 |
| **AnnoConfig** | 어노테이션 기반 설정 로딩 |
| **Channel** | Redis 기반 멀티서버 채널 시스템 |
| **CommonSender** | 플레이어 데이터 관리 및 SQL 연동 |
| **GUI** | ActionBar, 인벤토리 GUI, Title, Quickbar, Sidebar, Sign GUI |
| **Option** | 플레이어 옵션/설정 저장 |
| **SqlLib** | SQL 데이터베이스 추상화 |
| **RedisLib** | Redis 연결 및 Pub/Sub 관리 |
| **Coroutine** | Kotlin 코루틴 비동기 처리 (Bukkit/BungeeCord) |
| **CommonScheduler** | 태스크 스케줄링 |
| **CommonEvent** | 커스텀 이벤트 시스템 |
| **EventSupport** | Bukkit 이벤트 확장 |
| **Support** | 플랫폼 감지, 로깅, NMS 지원 |
| **CommonConfig** | 공통 설정 유틸리티 |

## 프로젝트 구조

```
Core/
├── java/                        # Java 소스 모듈
│   └── src/main/java/net/pooleaf/core/
│       ├── Core.java            # 메인 코디네이터
│       ├── module/              # 모듈 관리 시스템
│       ├── modules/             # 기능 모듈 (14개)
│       ├── plugin/              # 플러그인 추상화 레이어
│       ├── redis/               # Redis 매니저
│       └── sql/                 # SQL 매니저
├── kotlin/                      # Kotlin 확장 모듈
│   └── src/main/kotlin/net/pooleaf/core/modules/
│       ├── coroutine/           # 코루틴 지원
│       ├── gui/                 # Kotlin GUI 확장
│       └── support/             # Kotlin 유틸리티
├── src/main/resources/
│   ├── plugin.yml               # Bukkit 플러그인 매니페스트
│   └── bungee.yml               # BungeeCord 플러그인 매니페스트
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## 빌드

```bash
# 빌드
./gradlew build

# Shadow JAR (의존성 포함)
./gradlew shadowJar
```

빌드 결과물은 `build/libs/`에 생성됩니다.

## 설치

1. Shadow JAR을 빌드합니다.
2. 생성된 JAR 파일을 서버의 `plugins/` 폴더에 넣습니다.
3. MariaDB/MySQL 데이터베이스와 Redis 서버가 필요합니다.
4. ProtocolLib이 설치되어 있어야 합니다.

## 요구 사항

- Java 8+
- MariaDB / MySQL
- Redis
- ProtocolLib

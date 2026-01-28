# dundemo

dundemo는 Neople API를 활용하여 '던전앤파이터' 게임 내 캐릭터 및 모험단의 레이드 클리어 횟수를 조회하고 집계하는 Spring Boot 애플리케이션입니다.

## 주요 기능

- **캐릭터 레이드 횟수 조회**: 특정 캐릭터의 레이드 클리어 횟수를 조회합니다.
- **모험단 레이드 횟수 조회**: 모험단에 속한 모든 캐릭터의 레이드 클리어 횟수를 합산하여 제공합니다.
- **데이터 캐싱 및 갱신**: 조회된 캐릭터 정보와 레이드 클리어 횟수는 MongoDB에 저장하여 빠른 재조회를 지원합니다.
- **갱신 쿨타임**: 단기간의 무분별한 갱신 요청을 방지하기 위해 갱신 기능에 쿨타임이 적용되어 있습니다.

## 기술 스택

- **언어**: Java 17
- **프레임워크**: Spring Boot 3.5.5
- **데이터베이스**: MongoDB
- **주요 라이브러리**
  - Spring Web & WebFlux
  - Spring Data MongoDB
  - Lombok
- **빌드 도구**: Gradle

## API 엔드포인트

| HTTP Method | Path                              | 설명                                       | 요청 본문 예시                                                               |
|-------------|-----------------------------------|--------------------------------------------|------------------------------------------------------------------------------|
| `POST`      | `/api/v1/character/count`         | 특정 캐릭터의 레이드 클리어 횟수를 조회합니다. | `{"serverName": "cain", "characterName": "캐릭터명"}`                        |
| `POST`      | `/api/v1/adventure`               | 특정 모험단의 레이드 클리어 횟수를 조회합니다. | `{"adventureName": "모험단명"}`                                             |
| `POST`      | `/api/v1/adventure/refresh`       | 특정 모험단의 레이드 클리어 횟수를 갱신합니다. | `{"adventureName": "모험단명"}`                                             |

## 프로젝트 구조
 - **advice**: 사용자 예외가 모여있는 디렉토리입니다.
 - **client**: Neople API와 통신하기위한 Client를 정의한 디렉토리입니다.
 - **config**: CORS 설정을 관리하는 config 파일이 위치한 디렉토리입니다.
 - **domain**: mvc패턴의 모델에 해당하는 MongoDB Document를 정의하는 디렉토리입니다.
 - **enums**: 프로젝트에서 사용되는 다양한 enum을 정의하는 디렉토리입니다.
   - exception: 사용자 예외마다 에러코드를 부여해주는 enum ErrorCode가 정의되어 있습니다.
   - 나머지 enum은 Neople API에서 사용되는 고유한 명칭들을 정의했습니다.
 - **repository**: Spring Data MongoDB를 사용해 DB를 조작하는 reposiotry들을 정의되어 있습니다.
 - **service**: mvc 패턴의 뷰에 해당하는 서비스가 정의되어 있습니다.
 - **utils**: 프로젝트에서 공통적으로 사용될 다양한 기능이 정의되어 있습니다.
   - model: API 응답값을 타입에 따라 나누어 정의되어 있습니다.
   - response: 최종 API 응답이 정의되어 있습니다.
 - **web**:
   - controller: mvc 패턴의 컨트롤러를 정의한 디렉토리입니다.
   - dto: 데이터 구조를 정의한 DTO들이 정의된 디렉토리입니다.
## DB 설정
---------------------
1. 사용자 root 접속
2. DB 생성 쿼리 실행
``` create database greendb2; ```
3. 이모지 insert를 위한 DB 설정
``` DB 마우스 우클릭 -> 편집 - utf8mb4_general_ci ```

## 실행 순서
-----------------------
1. application.yml 파일에서 ddl_auto : create 로 변경
2. batch 폴더의 DBInitializer 파일 가서 @Bean 주석 처리 해제
3. 데이터베이스 데이터 초기화 확인 후 ddl_auto : update 로 변경
4. batch 폴더의 DBInitializer 가서 @Bean 주석 처리


프로젝트 보고서 : <https://www.notion.so/seongyung/fc5dc85f79b94374911469781f1af65d>

시연 영상 : <https://youtu.be/ssWhhUyoIFo>

구글 드라이브 : <https://drive.google.com/drive/folders/115z79b8d3497xXTp0gXrbU0BpqFKiQZB>

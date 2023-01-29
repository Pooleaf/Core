# ReserveModule

특정 조건 발생 시 수행할 코드를 예약해둘 수 있는 모듈입니다.

## 예시
플레이어 접속 시 메시지 출력하기
```
PlayerOnlineReserve.add(new PlayerReserve() {
    public void run() {
        getPlayer().sendMessage("능력 지속 시간이 종료되었습니다.");    
    }
});
```
package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 커넥션 파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    /**
     * 트랜잭션을 시작하기 위해선 커넥션이 필요합니다.
     * 비지니스 로직이 성공 시 커밋합니다.
     * 비지니스 로직이 실패 시 롤백합니다.
     * 트랜잭션이 모두 종료되면, 커넥션을 닫습니다.
     */
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();

        try {
            con.setAutoCommit(false);   //트랜잭션 시작
            //비지니스 로직
            bizLogic(con, fromId, toId, money);     //커넥션 객체 함께 전달
            con.commit();   //성공시 커밋
        } catch (Exception e) {
            con.rollback(); //실패시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(con);   //커넥션 반환
        }
    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }

    /**
     * 커넥션 반환
     * 커넥션을 커넥션 풀에 반환할 때는 자동 커밋 모드로 변경한 후 반환해야합니다.
     * 기본적인 동작 방식은 자동 커밋이기 때문에, 재사용되는 커넥션을 다른 쓰레드가 사용할 경우 수동 커밋을 자동 커밋으로 착각하고 로직을 수행할 수 있습니다.
     * @param con
     */
    private void release(Connection con) {
        if(con != null) {
            try {
                con.setAutoCommit(true);    //커넥션 풀 고려
                con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }
}

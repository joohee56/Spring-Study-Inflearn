package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    /**
     * service에서 예외를 잡는 테스트
     * 예외가 올라오지 않는다.
     */
    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    /**
     * service에서 예외를 던지는 테스트
     * repository에서 발생한 예외가 최상위까지 올라왔다.
     */
    @Test
    void checked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }

    /**
     * Exception 을 상속받은 예외는 체크 예외가 된다.
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는 예외를 잡아서 처리하거나, 던지거나 둘 중 하나를 필수로 선택해야 한다.
     */
    static class Service{
        Repository repository = new Repository();

        /**
         * 예외를 잡아서 처리
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }

        /**
         * 체크 예외를 밖으로 던짐
         * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야 한다.
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    /**
     * 체크 예외 발생
     */
    static class Repository {
        public void call() throws MyCheckedException{
            throw new MyCheckedException("ex");
        }
    }


}

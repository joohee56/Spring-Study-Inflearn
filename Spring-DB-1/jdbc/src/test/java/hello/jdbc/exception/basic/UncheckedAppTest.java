package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

@Slf4j
public class UncheckedAppTest {

    @Test
    void unchecked() {
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }

    @Test
    void printEx() {
        Controller controller = new Controller();
        try {
            controller.request();
        } catch (Exception e) {
//            e.printStackTrace();
            log.info("ex", e);
        }
    }

    static class Controller {
        Service service = new Service();

        //checked 예외와 달리 runtime 예외이기 때문에 불필요한 throws 구문이 사라졌다.
        public void request() {
            service.logic();
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        //checked 예외와 달리 runtime 예외이기 때문에 불필요한 throws 구문이 사라졌다.
        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {

        public void call() {
            throw new RuntimeConnectionException("연결 실패");
        }
    }

    static class Repository {
        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
            }
        }
        private void runSQL() throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RuntimeConnectionException extends RuntimeException {
        public RuntimeConnectionException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException() {

        }

        /**
         * 현재 예외를 발생시킨 예외(cause)를 포함한다. -> stackTrace에서 출력하기 위함
         */
        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }
}

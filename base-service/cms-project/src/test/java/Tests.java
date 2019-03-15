import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wangjiahao
 * @version 1.0
 * @className Tests
 * @since 2019-02-27 15:08
 */
public class Tests {

    @Test
    public void passswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123");
        System.out.println("encode = " + encode);
    }
}

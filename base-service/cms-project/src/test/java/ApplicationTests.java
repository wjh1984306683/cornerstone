import com.cs.cms.CmsProjectApplication;
import com.cs.cms.dao.sys.UserRepository;
import com.cs.cms.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author wangjiahao
 * @version 1.0
 * @className ApplicationTests
 * @since 2019-02-27 15:34
 */
@SpringBootTest(classes = CmsProjectApplication.class)
@RunWith(SpringRunner.class)
public class ApplicationTests {

    @Autowired
    private UserRepository repository;

    @Test
    @Transactional
    public void test() {
        Optional<User> user = repository.findById("1");
        System.out.println("user.get().getNickname() = " + user.get().getNickname());
        System.out.println("user.get().getRoles().size() = " + user.get().getRoles().size());
    }

}

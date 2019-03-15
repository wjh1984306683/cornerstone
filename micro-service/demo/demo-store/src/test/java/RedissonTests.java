import com.cs.base.common.redis.DistributedLocker;
import com.cs.base.common.redis.RedisClient;
import com.cs.micro.eureka.store.DemoStoreApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author wangjiahao
 * @version 1.0
 * @className RedissonTests
 * @since 2019-03-08 17:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoStoreApplication.class})
public class RedissonTests {

    @Autowired
    private DistributedLocker locker;

    /**
     * 正常使用方法
     */
    @Test
    public void testRedissonLock() {
//        1.尝试获取锁
        if (locker.tryLock("lock", TimeUnit.SECONDS, 5, 5)) {
//            2.结果为true，表示成功获取到锁，现在锁定60秒
            RLock lock = locker.lock("lock", 60);
//            3.其他操作
            RedisClient.set("lockTest", "lockTestContent", 60 * 60);
//            4.操作完成释放锁
            lock.unlock();
        }
    }

    /**
     * 只加锁，不解锁的情况
     */
    @Test
    public void testRedissonLockAndSleep() {
        RLock lock = locker.lock("lock", 60 * 60);
        RedisClient.set("lockTest", "lockTestContent", 60 * 60);
    }

    /**
     * 直接获取锁的情况
     */
    @Test
    public void testRedissonTryLock() {
        RLock rLock = locker.lock("lock", 60 * 60);
        RedisClient.set("lockTest", "lockTestContent", 60 * 60);
        rLock.unlock();
    }

}

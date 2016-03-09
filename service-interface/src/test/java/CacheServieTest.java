import com.yonyou.mcloud.Locator;
import com.yonyou.mcloud.cache.CacheException;
import com.yonyou.mcloud.cache.CacheServicePrx;

/**
 * Created by hubo on 2016/3/9
 */
public class CacheServieTest {

    public static void main(String[] args) throws CacheException {
        CacheServicePrx cacheService = Locator.lookup(CacheServicePrx.class);
        cacheService.add("123456", "Test", 1000);
        System.out.println(cacheService.get("123456"));
        if(cacheService.evict("123456")) {
            System.out.println("删除成功！");
        }
    }

}

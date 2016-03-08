import com.yonyou.mcloud.Locator;
import com.yonyou.mcloud.idgenerator.IdGeneratorPrx;

/**
 * Created by hubo on 2016/3/8
 */
public class IdGeneratorTest {

    public static void main(String[] args) {
        IdGeneratorPrx idGenerator = Locator.lookup(IdGeneratorPrx.class);
        System.out.println(idGenerator.nextId());
    }

}

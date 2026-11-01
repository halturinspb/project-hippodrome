import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class MainTest {

    @Test
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    @Disabled
    void mainTest_ShouldExecuteInLessThan22Seconds() throws Exception {
        Main.main(new String[]{});
    }
}

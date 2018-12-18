package info.clo5de.asukarpg.exception;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestItemConfigException {

    private static ItemConfigException exception;

    @BeforeClass
    public static void setup () {
        exception = new ItemConfigException(ItemConfigException.Action.READ, ItemConfigException.Stage.ItemID, "message");
    }

    @Test
    public void testGetAction () {
        assertThat(exception.getAction()).isEqualTo(ItemConfigException.Action.READ);
    }

    @Test
    public void testGetStage () {
        assertThat(exception.getStage()).isEqualTo(ItemConfigException.Stage.ItemID);
    }
}

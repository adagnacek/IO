package komiii.dor.organisr;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import komiii.dor.organisr.containers.BoilerFunctions;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("komiii.dor.organisr", appContext.getPackageName());
    }

    @Test
    public void testDateFormat(){
        assertNotNull(BoilerFunctions.dateFormat);
        assertEquals(Calendar.DAY_OF_MONTH,BoilerFunctions.translateDateUnit("d"));
        assertEquals(Calendar.WEEK_OF_MONTH,BoilerFunctions.translateDateUnit("w"));
        assertEquals(Calendar.MONTH,BoilerFunctions.translateDateUnit("m"));
        assertEquals(Calendar.YEAR,BoilerFunctions.translateDateUnit("y"));
    }

    @Test
    public void testConnecting(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        BoilerFunctions.connect(appContext);
        assertNotNull(BoilerFunctions.connection);
    }

    @Test
    public void testDisconnecting() throws SQLException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        BoilerFunctions.connect(appContext);
        BoilerFunctions.disconnect(appContext);
        assertTrue(BoilerFunctions.connection.isClosed());
    }

    @Test
    public void testEventListing() throws ParseException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        BoilerFunctions.connect(appContext);
        Date dateFrom = new Date();
        Date dateTo = BoilerFunctions.dateFormat.parse("2022-01-01");
        assertTrue(BoilerFunctions.getEventsBetween(appContext,dateFrom,dateTo).size()>0);
    }

    @Test
    public void UITest(){
    }
}

package komiii.dor.organisr;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import komiii.dor.organisr.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PerformanceActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initTest() {
    }

    @Test
    public void perfTest1() {
        onView(withId(R.id.event_space))
                .perform(click());
        long time = System.currentTimeMillis();
        onView(withId(R.id.calendar_topPart)).check(matches(isDisplayed()));
        time = System.currentTimeMillis()-time;
        assertTrue(time<1000);
    }

    @Test
    public void perfTest2() {
        onView(withId(R.id.shop_space))
                .perform(click());
        long time = System.currentTimeMillis();
        onView(withId(R.id.shopping_frame_container)).check(matches(isDisplayed()));
        onView(withId(R.id.shopping_navigationbar)).check(matches(isDisplayed()));
        time = System.currentTimeMillis()-time;
        assertTrue(time<1000);
    }

    @Test
    public void perfTest3() {
        onView(withId(R.id.reminder_space))
                .perform(click());
        long time = System.currentTimeMillis();
        onView(withId(R.id.reminders_fab)).check(matches(isDisplayed()));
        onView(withId(R.id.reminders_list)).check(matches(isDisplayed()));
        time = System.currentTimeMillis()-time;
        assertTrue(time<1000);
    }

    @Test
    public void perfTest4() {
        onView(withId(R.id.goal_space))
                .perform(click());
        long time = System.currentTimeMillis();
        onView(withId(R.id.goals_lv)).check(matches(isDisplayed()));
        time = System.currentTimeMillis()-time;
        assertTrue(time<1000);
    }

    @Test
    public void perfTest5() {
        onView(withId(R.id.checks_space))
                .perform(click());
        long time = System.currentTimeMillis();
        onView(withId(R.id.checks_frame_container)).check(matches(isDisplayed()));
        onView(withId(R.id.checks_navigationbar)).check(matches(isDisplayed()));
        time = System.currentTimeMillis()-time;
        assertTrue(time<1000);
    }
}
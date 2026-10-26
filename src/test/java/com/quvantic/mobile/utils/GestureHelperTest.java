package com.quvantic.mobile.utils;

import io.appium.java_client.AppiumDriver;
import org.mockito.ArgumentCaptor;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Sequence;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Verifies the W3C pointer actions each gesture sends, using a mocked driver with a
 * 1000 x 2000 screen. No device or Appium server is involved.
 */
public class GestureHelperTest {

    private AppiumDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = mock(AppiumDriver.class);
        WebDriver.Options options = mock(WebDriver.Options.class);
        WebDriver.Window window = mock(WebDriver.Window.class);
        when(driver.manage()).thenReturn(options);
        when(options.window()).thenReturn(window);
        when(window.getSize()).thenReturn(new Dimension(1000, 2000));
    }

    @Test
    public void swipeUpMovesFromLowerToUpperScreenOnCentreLine() {
        GestureHelper.swipeUp(driver);
        List<Map<String, Object>> actions = performedActions();
        assertMove(actions.get(0), 500, 1600);
        assertMove(actions.get(2), 500, 400);
        assertThat(actions.get(2).get("duration")).isEqualTo(300L);
    }

    @Test
    public void swipeDownMovesFromUpperToLowerScreen() {
        GestureHelper.swipeDown(driver);
        List<Map<String, Object>> actions = performedActions();
        assertMove(actions.get(0), 500, 400);
        assertMove(actions.get(2), 500, 1600);
    }

    @Test
    public void swipeLeftAndRightMoveHorizontallyOnMiddleRow() {
        GestureHelper.swipeLeft(driver);
        List<Map<String, Object>> left = performedActions();
        assertMove(left.get(0), 800, 1000);
        assertMove(left.get(2), 200, 1000);

        setUp();
        GestureHelper.swipeRight(driver);
        List<Map<String, Object>> right = performedActions();
        assertMove(right.get(0), 200, 1000);
        assertMove(right.get(2), 800, 1000);
    }

    @Test
    public void swipeIsMoveDownMoveUp() {
        GestureHelper.swipeUp(driver);
        assertThat(performedActions()).extracting(a -> a.get("type"))
                .containsExactly("pointerMove", "pointerDown", "pointerMove", "pointerUp");
    }

    @Test
    public void tapPressesBrieflyAtThePoint() {
        GestureHelper.tap(driver, 120, 340);
        List<Map<String, Object>> actions = performedActions();
        assertThat(actions).extracting(a -> a.get("type"))
                .containsExactly("pointerMove", "pointerDown", "pause", "pointerUp");
        assertMove(actions.get(0), 120, 340);
        assertThat(actions.get(2).get("duration")).isEqualTo(50L);
    }

    @Test
    public void longPressHoldsForTheRequestedDuration() {
        GestureHelper.longPress(driver, 10, 20, Duration.ofSeconds(2));
        List<Map<String, Object>> actions = performedActions();
        assertMove(actions.get(0), 10, 20);
        assertThat(actions.get(2).get("type")).isEqualTo("pause");
        assertThat(actions.get(2).get("duration")).isEqualTo(2000L);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> performedActions() {
        ArgumentCaptor<Collection<Sequence>> captor = ArgumentCaptor.forClass(Collection.class);
        verify(driver).perform(captor.capture());
        Collection<Sequence> sequences = captor.getValue();
        assertThat(sequences).hasSize(1);
        Map<String, Object> encoded = sequences.iterator().next().encode();
        assertThat(encoded.get("type")).isEqualTo("pointer");
        return (List<Map<String, Object>>) encoded.get("actions");
    }

    private static void assertMove(Map<String, Object> action, int x, int y) {
        assertThat(action.get("type")).isEqualTo("pointerMove");
        assertThat(action.get("x")).isEqualTo(x);
        assertThat(action.get("y")).isEqualTo(y);
    }
}

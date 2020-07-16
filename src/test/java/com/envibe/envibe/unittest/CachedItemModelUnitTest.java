package com.envibe.envibe.unittest;

import com.envibe.envibe.UnitTest;
import com.envibe.envibe.model.CachedItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CachedItemModelUnitTest extends UnitTest {

    private static CachedItem testCachedItem;

    private static final String TEST_STRING1 = "test1";
    private static final String TEST_STRING2 = "test2";

    @BeforeEach
    public void setupTestCachedItemInstance() {
        testCachedItem = new CachedItem();
    }

    @Test
    public void testPurpose() {
        testCachedItem = new CachedItem(TEST_STRING1, TEST_STRING2, TEST_STRING2);
        assertThat(testCachedItem.getPurpose()).isEqualTo(TEST_STRING1);
    }

    @Test
    public void testUserTag() {
        testCachedItem = new CachedItem(TEST_STRING2, TEST_STRING1, TEST_STRING2);
        assertThat(testCachedItem.getUserTag()).isEqualTo(TEST_STRING1);
    }

    @Test
    public void testPayload() {
        testCachedItem = new CachedItem(TEST_STRING2, TEST_STRING2, TEST_STRING1);
        assertThat(testCachedItem.getPayload()).isEqualTo(TEST_STRING1);
    }
}

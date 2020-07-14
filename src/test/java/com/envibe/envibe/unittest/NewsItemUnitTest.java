package com.envibe.envibe.unittest;

import com.envibe.envibe.model.NewsItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class NewsItemUnitTest {

    private static NewsItem testNewsItem;

    private static final String TEST_STRING1 = "test1";
    private static final Date TEST_DATE = new Date("2010-02-25");
    private static final int TEST_INTEGER = 5;

    @BeforeEach
    public static void setupTestNewsItemInstance() {
        testNewsItem = new NewsItem();
    }

    @Test
    public void testPostId() {
        testNewsItem.setPost_id(TEST_INTEGER);
        assertThat(testNewsItem.getPost_id()).isEqualTo(TEST_INTEGER);
    }

    @Test
    public void testUsername() {
        testNewsItem.setUsername(TEST_STRING1);
        assertThat(testNewsItem.getUsername()).isEqualTo(TEST_STRING1);
    }

    @Test
    public void testPostDate() {
        testNewsItem.setPost_date(TEST_DATE);
        assertThat(testNewsItem.getPost_date()).isEqualTo(TEST_DATE);
    }

    @Test
    public void testContent() {
        testNewsItem.setContent(TEST_STRING1);
        assertThat(testNewsItem.getContent()).isEqualTo(TEST_STRING1);
    }
}

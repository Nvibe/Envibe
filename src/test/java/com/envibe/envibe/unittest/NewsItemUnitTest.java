package com.envibe.envibe.unittest;

import com.envibe.envibe.model.NewsItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NewsItemUnitTest {

    private static NewsItem testNewsItem;

    private static final String TEST_STRING1 = "test1";
    private static final Date TEST_DATE = new Date();
    private static final int TEST_INTEGER = 5;

    @Before
    public void setupTestNewsItemInstance() {
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

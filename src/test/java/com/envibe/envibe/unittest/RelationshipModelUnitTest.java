package com.envibe.envibe.unittest;

import com.envibe.envibe.model.Relationship;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RelationshipModelUnitTest {

    private static Relationship testRelationship;

    private static final String TEST_STRING = "test";

    @Before
    public void setupTestRelationshipInstance() {
        testRelationship = new Relationship();
    }

    @Test
    public void testUserNameField() throws Exception {
        testRelationship.setUserName(TEST_STRING);
        assertThat(testRelationship.getUserName()).isEqualTo(TEST_STRING);
    }

    @Test
    public void testUserFriendField() throws Exception {
        testRelationship.setUserFriend(TEST_STRING);
        assertThat(testRelationship.getUserFriend()).isEqualTo(TEST_STRING);
    }
}

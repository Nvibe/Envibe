package com.envibe.envibe.unittest;

import com.envibe.envibe.model.Relationship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RelationshipModelUnitTest {

    private static Relationship testRelationship;

    private static final String TEST_STRING = "test";

    @BeforeEach
    public static void setupTestRelationshipInstance() {
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

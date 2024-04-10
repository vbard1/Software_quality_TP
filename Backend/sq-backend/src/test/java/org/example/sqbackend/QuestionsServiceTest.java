package org.example.sqbackend;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class QuestionsServiceTest {

    @BeforeAll
    public static void setup() {
        // TODO
    }

    @AfterAll
    public static void cleanup() {
        // TODO
    }

    @Test
    public void retrieveAllQuestionsFromPollTest(){
    }

    @Test
    public void expiredQuestionsHandlingTest(){

    }

    @Test
    public void answeredQuestionsHandlingTest(){

    }

    @Test
    public void getQuestionsByUserAndPollTest(){

    }

    @Test
    public void emptyResponseHandlingTest(){

    }


}

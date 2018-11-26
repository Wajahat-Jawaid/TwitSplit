package com.zalora.wajahat.twitsplit.mvp.interactors;

import com.zalora.wajahat.twitsplit.mvp.impl.SendImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class SendInteractorTest {

    @Mock
    private SendImpl impl;
    @Mock
    private SendInteractor.OnTweetActionListener listener;
    private SendInteractor interactor;

    @Before
    public void setup() throws Exception {
        interactor = new SendInteractor();
    }

    @Test
    public void getTweetAfterErasingCharLimitExceededWordFromTweet() {
        String tweet = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz is a tweet";
        String word = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
        String updatedTweet = interactor.eraseLimitExceededWord(tweet, word);
        // Loop to read every word from tweet and check if any 50 letter word
        String[] words = updatedTweet.split(" ");
        for (String w : words) {
            Boolean res = interactor.isWordGreaterThan50Letters(w);
            // Now assert that each word has length less than 50
            assertThat(res, is(false));
        }
    }

    @Test
    public void splitTweetForSingleChunk() {
        String tweet = "This is a tweet";
        ArrayList<String> chunks = interactor.splitTweet(tweet, listener);
        assertThat(chunks, hasSize(1));
    }

    @Test
    public void splitTweetForMultipleChunks() {
        String tweet = "Java is a general-purpose computer-programming language that is concurrent, class-based, object-oriented,[15] and specifically designed to have as few implementation dependencies as possible. It is intended to let application developers \\\"write once, run anywhere\\\" (WORA),[16] meaning that compiled Java code can run on all platforms that support Java without the need for recompilation.[17] Java applications are typically compiled to bytecode that can run on any Java virtual machine (JVM) regardless of computer architecture. As of 2016, Java is one of the most popular programming languages in use,[18][19][20][21] particularly for client-server web applications, with a reported 9 million developers.[22] Java was originally developed by James Gosling at Sun Microsystems (which has since been acquired by Oracle Corporation) and released in 1995 as a core component of Sun Microsystems' Java platform. The language derives much of its syntax from C and C++, but it has fewer low-level facilities than either of them";
        ArrayList<String> chunks = interactor.splitTweet(tweet, listener);
        assertThat(chunks, hasSize(greaterThanOrEqualTo(2)));
    }

    @Test
    public void showErrorWhenTweetHasWordWithCharactersGreaterThan50() {
        String word = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"; // 52 letters word
        Boolean res = interactor.isWordGreaterThan50Letters(word);
        assertThat(res, is(true));
    }
}
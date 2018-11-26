package com.zalora.wajahat.twitsplit.mvp.interactors

import com.zalora.wajahat.twitsplit.mvp.impl.SendImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import java.util.ArrayList

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*

@RunWith(MockitoJUnitRunner::class)
class SendInteractorTest {

    @Mock
    private val impl: SendImpl? = null
    @Mock
    private val listener: SendInteractor.OnTweetActionListener? = null
    private var interactor: SendInteractor? = null

    @Before
    @Throws(Exception::class)
    fun setup() {
        interactor = SendInteractor()
    }

    @Test
    fun getTweetAfterErasingCharLimitExceededWordFromTweet() {
        val tweet = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz is a tweet"
        val word = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"
        val updatedTweet = interactor!!.eraseLimitExceededWord(tweet, word)
        // Loop to read every word from tweet and check if any 50 letter word
        val words = updatedTweet.split(" ".toRegex())
        for (w in words) {
            val res = interactor!!.isWordGreaterThan50Letters(w)
            // Now assert that each word has length less than 50
            assertThat(res, `is`(false))
        }
    }

    @Test
    fun splitTweetForSingleChunk() {
        val tweet = "This is a tweet"
        val chunks = interactor!!.splitTweet(tweet, listener!!)
        assertThat<ArrayList<String>>(chunks, hasSize<Any>(1))
    }

    @Test
    fun splitTweetForMultipleChunks() {
        val tweet =
            "Java is a general-purpose computer-programming language that is concurrent, class-based, object-oriented,[15] and specifically designed to have as few implementation dependencies as possible. It is intended to let application developers \\\"write once, run anywhere\\\" (WORA),[16] meaning that compiled Java code can run on all platforms that support Java without the need for recompilation.[17] Java applications are typically compiled to bytecode that can run on any Java virtual machine (JVM) regardless of computer architecture. As of 2016, Java is one of the most popular programming languages in use,[18][19][20][21] particularly for client-server web applications, with a reported 9 million developers.[22] Java was originally developed by James Gosling at Sun Microsystems (which has since been acquired by Oracle Corporation) and released in 1995 as a core component of Sun Microsystems' Java platform. The language derives much of its syntax from C and C++, but it has fewer low-level facilities than either of them"
        val chunks = interactor!!.splitTweet(tweet, listener!!)
        assertThat<ArrayList<String>>(chunks, hasSize<Any>(greaterThanOrEqualTo(2)))
    }

    @Test
    fun showErrorWhenTweetHasWordWithCharactersGreaterThan50() {
        val word = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz" // 52 letters word
        val res = interactor!!.isWordGreaterThan50Letters(word)
        assertThat(res, `is`(true))
    }
}
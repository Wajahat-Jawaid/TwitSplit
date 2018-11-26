package com.zalora.wajahat.twitsplit.mvp.presenters

import com.zalora.wajahat.twitsplit.R
import com.zalora.wajahat.twitsplit.mvp.impl.SendImpl
import com.zalora.wajahat.twitsplit.mvp.interactors.SendInteractor
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SendPresenterTest {

    @Mock
    private val impl: SendImpl? = null
    @Mock
    private val interactor: SendInteractor? = null
    private var presenter: SendPresenter? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        presenter = SendPresenter(impl!!, interactor!!)
    }

    @Test
    @Throws(Exception::class)
    fun showErrorWhenTweetIsEmpty() {
        `when`(impl!!.getTweet()).thenReturn("")
        presenter!!.sendTweet()
        verify(impl).showValidationError(R.string.tweet_empty)
    }
}
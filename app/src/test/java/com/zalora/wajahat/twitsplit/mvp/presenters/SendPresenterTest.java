package com.zalora.wajahat.twitsplit.mvp.presenters;

import com.zalora.wajahat.twitsplit.R;
import com.zalora.wajahat.twitsplit.mvp.impl.SendImpl;
import com.zalora.wajahat.twitsplit.mvp.interactors.SendInteractor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SendPresenterTest {

    @Mock
    private SendImpl impl;
    @Mock
    private SendInteractor interactor;
    private SendPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new SendPresenter(impl, interactor);
    }

    @Test
    public void showErrorWhenTweetIsEmpty() throws Exception {
        when(impl.getTweet()).thenReturn("");
        presenter.sendTweet();
        verify(impl).showValidationError(R.string.tweet_empty);
    }
}
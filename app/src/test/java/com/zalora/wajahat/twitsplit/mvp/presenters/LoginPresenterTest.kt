package com.zalora.wajahat.twitsplit.mvp.presenters

import com.zalora.wajahat.twitsplit.R
import com.zalora.wajahat.twitsplit.mvp.impl.LoginImpl
import com.zalora.wajahat.twitsplit.mvp.interactors.LoginInteractor
import com.zalora.wajahat.twitsplit.mvp.interactors.LoginInteractor.OnLoginFinishedListener
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginPresenterTest {

    @Mock
    private val view: LoginImpl? = null
    @Mock
    private val interactor: LoginInteractor? = null
    @Mock
    private val listener: OnLoginFinishedListener? = null
    private var presenter: LoginPresenter? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        presenter = LoginPresenter(view!!, interactor!!)
    }

    @Test
    @Throws(Exception::class)
    fun showErrorMessageWhenFullNameIsEmpty() {
        `when`(view!!.getFullName()).thenReturn("")
        presenter!!.onLoginClicked()
        verify<LoginImpl>(view).showFullNameError(R.string.full_name_empty)
    }

    @Test
    @Throws(Exception::class)
    fun shouldShowErrorMessageWhenUsernameIsEmpty() {
        `when`(view!!.getFullName()).thenReturn("John Martin")
        `when`(view.getUsername()).thenReturn("")
        presenter!!.onLoginClicked()
        verify<LoginImpl>(view).showUsernameError(R.string.username_empty)
    }

    @Test
    @Throws(Exception::class)
    fun startActivityWhenUsernameAndPasswordAreCorrect() {
        `when`(view!!.getFullName()).thenReturn("John Martin")
        `when`(view.getUsername()).thenReturn("Johnny")
        presenter!!.onLoginClicked()

        verify<LoginInteractor>(interactor).saveDataToPrefs(view.getFullName(),
                view.getUsername(), listener!!)
    }
}
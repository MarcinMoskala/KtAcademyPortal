package org.kotlinacademy.components

import org.kotlinacademy.common.getUrlParam
import org.kotlinacademy.common.secretInUrl
import org.kotlinacademy.data.Info
import org.kotlinacademy.presentation.info.InfoPresenter
import org.kotlinacademy.presentation.info.InfoView
import org.kotlinacademy.views.errorView
import org.kotlinacademy.views.infoFormView
import org.kotlinacademy.views.loadingView
import org.kotlinacademy.views.thankYouView
import react.RBuilder
import react.RProps
import react.ReactElement
import kotlin.properties.Delegates.observable

class SubmitInfoComponent : BaseComponent<RProps, SubmitInfoComponentState>(), InfoView {

    private val presenter by presenter { InfoPresenter(this, id = getUrlParam("id")?.toIntOrNull(), secret = secretInUrl) }

    override var loading: Boolean by observable(false) { _, _, n ->
        setState { state.loading = n }
    }
    override var prefilled: Info? by observable(null as Info?) { _, _, n ->
        setState { state.prefilled = n }
    }

    override fun RBuilder.render(): ReactElement? = when {
        state.loading == true -> loadingView()
        state.showThankYouPage == true -> thankYouView()
        state.error != null -> errorView(state.error!!)
        else -> infoFormView(initial = state.prefilled?.data, onSubmit = presenter::onSubmitClicked)
    }

    override fun backToNewsAndShowSuccess() {
        setState { showThankYouPage = true }
        backAfterDelay(millis = 3_000)
    }
}

external interface SubmitInfoComponentState : BaseState {
    var prefilled: Info?
    var loading: Boolean?
    var showThankYouPage: Boolean?
}
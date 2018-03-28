package org.kotlinacademy.mobile

import android.support.test.espresso.IdlingResource
import android.view.View


class ViewVisibilityIdlingResource(private val view: View, private val mExpectedVisibility: Int) : IdlingResource {

    private var idle: Boolean = false
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return ViewVisibilityIdlingResource::class.java.simpleName
    }

    override fun isIdleNow(): Boolean {
        idle = idle || view.visibility == mExpectedVisibility
        if (idle) resourceCallback?.onTransitionToIdle()
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }
}

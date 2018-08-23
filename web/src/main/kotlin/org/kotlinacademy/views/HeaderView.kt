package org.kotlinacademy.views

import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import kotlinx.html.onClick
import org.w3c.dom.events.Event
import react.RBuilder
import react.ReactElement
import react.dom.*
import kotlin.browser.document

fun RBuilder.headerView(): ReactElement? = div(classes = "header") {

    div(classes = "navigation-bar") {
        div(classes = "logo-container") {
            a(target = "_top", href = "", classes = "pointer logo-img") {
                img(src = "img/logo_full.png", alt = "Kot. Academy Logo") {
                    attrs { width = "122,8"; height = "70,6" }
                }
            }
        }
        nav(classes = "bookmarks") {
            attrs { id = "bookmarks" }
            ul {
                menuItem("Home", "http://kot.academy", first = true)
                menuItem("Portal", "http://portal.kot.academy")
                menuItem("Blog", "http://blog.kotlin-academy.com")
                menuItem("Contact", "http://kot.academy/#contact")
                li(classes = "inline") {
                    a(href = "javascript:void(0);", classes = "menu-icon") {
                        attrs { onClickFunction = ::onHamburgerClicked }
                        i(classes = "fas fa-bars") { }
                    }
                }
            }
        }
    }

    div(classes = "banner") {
        div(classes = "wow fadeInDown") {
            h1 {
                +"KOT. ACADEMY"
            }
            h3 {
                +"We simplify KOTLIN learning"
            }
        }
    }
}

private fun RBuilder.menuItem(text: String, href: String, first: Boolean = false): ReactElement? = li(classes = "inline") {
    a(href = href, classes = "nav-link pointer" + if (first) " first-bookmark" else "") { +text }
}

fun onHamburgerClicked(e: Event) {
    val bookmarksView = document.getElementById("bookmarks")
    bookmarksView?.className = if (bookmarksView?.className == "bookmarks") "bookmarks responsive" else "bookmarks"
}

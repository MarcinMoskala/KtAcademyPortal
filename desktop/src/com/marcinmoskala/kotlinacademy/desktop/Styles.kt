package com.marcinmoskala.kotlinacademy.desktop

import javafx.geometry.Pos.CENTER
import javafx.scene.Cursor.HAND
import javafx.scene.paint.Color.TRANSPARENT
import javafx.scene.paint.Color.WHITE
import javafx.scene.text.FontWeight.BOLD
import tornadofx.*
import java.net.URI

class Styles : Stylesheet() {

    companion object {
        val newsView by cssclass()
        val newsList by cssclass()
        val newsCard by cssclass()
        val newsCardInner by cssclass()
        val listCenter by cssclass()
        val title by cssclass()
        val subtitle by cssclass()
        val header by cssclass()
        val headerTitle by cssclass()
        val icon by cssclass()
        val generalFeedback by cssclass()

        val articleWidth = 640.0
        val replyIcon = "M18,8H6V6H18V8M18,11H6V9H18V11M18,14H6V12H18V14M22,4A2,2 0 0,0 20,2H4A2,2 0 0,0 2,4V16A2,2 0 0,0 4,18H18L22,22V4Z"
        val refreshIcon = "M17.65,6.35C16.2,4.9 14.21,4 12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20C15.73,20 18.84,17.45 19.73,14H17.65C16.83,16.33 14.61,18 12,18A6,6 0 0,1 6,12A6,6 0 0,1 12,6C13.66,6 15.14,6.69 16.22,7.78L13,11H20V4L17.65,6.35Z"
        val eyeIcon = "m 1664,576 q -152,236 -381,353 61,-104 61,-225 0,-185 -131.5,-316.5 Q 1081,256 896,256 711,256 579.5,387.5 448,519 448,704 448,825 509,929 280,812 128,576 261,371 461.5,249.5 662,128 896,128 1130,128 1330.5,249.5 1531,371 1664,576 z M 944,960 q 0,20 -14,34 -14,14 -34,14 -125,0 -214.5,-89.5 Q 592,829 592,704 q 0,-20 14,-34 14,-14 34,-14 20,0 34,14 14,14 14,34 0,86 61,147 61,61 147,61 20,0 34,14 14,14 14,34 z m 848,-384 q 0,-34 -20,-69 Q 1632,277 1395.5,138.5 1159,0 896,0 633,0 396.5,139 160,278 20,507 0,542 0,576 q 0,34 20,69 140,229 376.5,368 236.5,139 499.5,139 263,0 499.5,-139 236.5,-139 376.5,-368 20,-35 20,-69 z"
    }

    init {
        header {
            backgroundImage += URI.create("/images/header.jpg")
            prefWidth = 1280.px
            prefHeight = 200.px
            padding = box(50.px)
            alignment = CENTER
            fontSize = 22.px

            text {
                fill = WHITE
            }

            headerTitle {
                fontSize = 40.px
                fontWeight = BOLD
            }
        }

        listCenter {
            maxWidth = articleWidth.px
            alignment = CENTER
        }

        newsList {
            title {
                padding = box(0.px, 0.px, 10.px, 0.px)
                fontSize = 30.px
                fontWeight = BOLD
            }

            subtitle {
                padding = box(10.px)
                fontSize = 18.px
            }

            borderColor += box(TRANSPARENT)

            selected {
                backgroundColor += TRANSPARENT
            }

            listCell {
                alignment = CENTER
            }
        }

        newsCard {
            borderRadius += box(3.px)
            borderColor += box(c("#cecece"))

            padding = box(10.px, 20.px, 15.px, 20.px)

            newsCardInner {
                padding = box(0.px, 0.px, 30.px, 0.px)
            }

        }

        newsView {
            prefHeight = 900.px
        }

        icon {
            backgroundColor += TRANSPARENT
            padding = box(10.px)
            cursor = HAND
        }

        generalFeedback {
            padding = box(20.px, 40.px)
        }

        progressIndicator {
            prefHeight = 100.px
            prefWidth = 100.px
        }

        scrollPane {
            scrollBar and vertical {
                backgroundRadius += box(0.px)
            }
            corner {
                backgroundRadius += box(0.px)
            }
        }

        scrollBar {
            prefWidth = 18.px
            backgroundColor += WHITE

            thumb {
                backgroundColor += c("#ededed")
                backgroundRadius += box(8.px)

                and(hover) {
                    backgroundColor += c("#e1e1e1")
                }
            }

            s(incrementButton, decrementButton, incrementArrow, decrementArrow) {
                backgroundColor += WHITE
                padding = box(0.px)
            }
        }

    }
}
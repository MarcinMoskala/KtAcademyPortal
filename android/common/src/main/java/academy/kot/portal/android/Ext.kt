package academy.kot.portal.android

fun String?.nullIfBlank(): String? = if(this == null || isBlank()) null else this

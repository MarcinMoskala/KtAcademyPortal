package com.marcinmoskala.kotlinacademy

import com.google.gson.GsonBuilder

val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()!!

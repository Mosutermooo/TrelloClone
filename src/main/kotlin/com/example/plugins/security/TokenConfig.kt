package com.example.plugins.security

import javax.management.monitor.StringMonitor

data class TokenConfig(
    val issuer: String,
    val audience: String,
    val secret: String
)

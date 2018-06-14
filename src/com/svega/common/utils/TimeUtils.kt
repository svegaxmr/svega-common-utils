package com.svega.common.utils

import java.time.Duration

object TimeUtils {
    fun formatDuration(duration: Duration): String {
        val seconds = duration.seconds
        val absSeconds = Math.abs(seconds)
        val positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                absSeconds % 3600 / 60,
                absSeconds % 60)
        return if (seconds < 0) "-$positive" else positive
    }
}
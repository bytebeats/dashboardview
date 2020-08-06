package me.bytebeats.dbv.intplt

import android.animation.TimeInterpolator

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 11:57
 *
 * @see {@linkurl http://inloop.github.io/interpolator/}
 */
abstract class BaseTimeInterpolator(val factor: Float = DEFAULT_FACTOR) : TimeInterpolator {
    companion object {
        private const val DEFAULT_FACTOR = 0.75F
    }
}
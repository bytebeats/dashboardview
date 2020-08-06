package me.bytebeats.dbv

import android.animation.TimeInterpolator

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/5 15:42
 */
class SpringInterpolator(val factor: Float = DEFAULT_FACTOR) : TimeInterpolator {

    override fun getInterpolation(input: Float): Float {
        return (Math.pow(2.0, -10.0 * input)
                * Math.sin((input - factor / 4.0) * (2.0 * Math.PI) / factor)
                + 1.0).toFloat()
    }

    companion object {
        private const val DEFAULT_FACTOR = 0.75F
    }
}
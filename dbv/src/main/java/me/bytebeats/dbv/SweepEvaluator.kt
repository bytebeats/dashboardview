package me.bytebeats.dbv

import android.animation.TimeInterpolator
import android.animation.TypeEvaluator
import me.bytebeats.dbv.tplt.SpringInterpolator

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/5 15:52
 */
class SweepEvaluator(var timeInterpolator: TimeInterpolator = SpringInterpolator()) : TypeEvaluator<Float> {
    companion object {
        private val TAG = SweepEvaluator::class.java.simpleName
    }

    override fun evaluate(fraction: Float, startValue: Float, endValue: Float): Float {
        return startValue + (endValue - startValue) * timeInterpolator.getInterpolation(fraction)
    }
}
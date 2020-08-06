package me.bytebeats.dbv.intplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:30
 */
class AccelerateInterpolator : BaseTimeInterpolator() {
    override fun getInterpolation(input: Float): Float {
        return if (factor == 1.0F) {
            input * input
        } else {
            Math.pow(input.toDouble(), 2.0 * factor).toFloat()
        }
    }
}
package me.bytebeats.dbv.intplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:16
 */
class DecelerateInterpolator : BaseTimeInterpolator() {
    override fun getInterpolation(input: Float): Float {
        return if (factor == 1.0F) {
            1.0F - (1.0F - input) * (1.0F - input)
        } else {
            1.0F - Math.pow(1.0 - input, 2.0 * factor).toFloat()
        }
    }
}
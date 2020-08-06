package me.bytebeats.dbv.intplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:31
 */
class AccelerateDecelerateInterpolator : BaseTimeInterpolator() {
    override fun getInterpolation(input: Float): Float = Math.cos((input + 1) * Math.PI).toFloat() / 2.0F + .5F
}
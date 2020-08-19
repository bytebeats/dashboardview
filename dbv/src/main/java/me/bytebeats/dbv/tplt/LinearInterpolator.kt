package me.bytebeats.dbv.tplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:34
 */
class LinearInterpolator : BaseTimeInterpolator() {
    override fun getInterpolation(input: Float): Float = input
}
package me.bytebeats.dbv.tplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:20
 */
class CycleInterpolator : BaseTimeInterpolator() {
    var cycles: Float = 1.0F

    override fun getInterpolation(input: Float): Float {
        return Math.sin(2.0 * cycles * Math.PI * input).toFloat()
    }
}
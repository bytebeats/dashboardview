package me.bytebeats.dbv.intplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/6 12:33
 */
class SmoothStepInterpolator : BaseTimeInterpolator() {
    override fun getInterpolation(input: Float): Float = input * input * (3 - 2 * input)
}
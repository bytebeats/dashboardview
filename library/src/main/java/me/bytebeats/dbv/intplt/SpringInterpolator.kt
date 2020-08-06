package me.bytebeats.dbv.intplt

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/5 15:42
 */
class SpringInterpolator : BaseTimeInterpolator() {

    override fun getInterpolation(input: Float): Float {
        return (Math.pow(2.0, -10.0 * input)
                * Math.sin((input - factor / 4.0) * (2.0 * Math.PI) / factor)
                + 1.0).toFloat()
    }
}
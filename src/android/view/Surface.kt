package android.view

import android.graphics.Canvas

class Surface {

    private val mCanvas = Canvas()

    fun lockCanvas(): Canvas {
//        nativeLock(mCanvas)
        return mCanvas
    }

    fun unLockCanvasAndPost(canvas: Canvas) {
        if (canvas != mCanvas) {
            throw IllegalArgumentException("canvas object must be the same instance that "
                    + "was previously returned by lockCanvas")
        }
//        nativeUnlockCanvasAndPost()
    }
}
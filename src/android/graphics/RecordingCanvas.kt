package android.graphics

class RecordingCanvas : BaseCanvas() {
    override fun isHardwareAccelerated(): Boolean = true
}
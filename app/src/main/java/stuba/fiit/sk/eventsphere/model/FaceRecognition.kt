package stuba.fiit.sk.eventsphere.model

import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

val highAccuracyOpts = FaceDetectorOptions.Builder()
    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
    .build()

val detector = FaceDetection.getClient(highAccuracyOpts)


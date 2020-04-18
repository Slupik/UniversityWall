/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */
package io.github.slupik.universitywall.device.camera.scanner;

import android.graphics.Bitmap;

import com.google.firebase.ml.common.FirebaseMLException;

import java.nio.ByteBuffer;

/** An inferface to process the images with different ML Kit detectors and custom image models. */
public interface VisionImageProcessor {

  /** Processes the images with the underlying machine learning models. */
  void process(ByteBuffer data, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay)
      throws FirebaseMLException;

  /** Processes the bitmap images. */
  void process(Bitmap bitmap, GraphicOverlay graphicOverlay);

  /** Stops the underlying machine learning model and release resources. */
  void stop();
}

package fastaieval.vision;

import fastaieval.metrics.EvalResult;

public final class VisionEvaluator {

    /**
     * Evaluates Bounding Box Intersection-over-Union (IoU) accuracy.
     */
    public EvalResult evaluateBoxIoU(float[] predBox, float[] targetBox) {
        long start = System.nanoTime();
        if (predBox.length < 4 || targetBox.length < 4) {
            return new EvalResult(0.0f, false, "IoU", 0, "Invalid box dimensions");
        }

        float x1 = Math.max(predBox[0], targetBox[0]);
        float y1 = Math.max(predBox[1], targetBox[1]);
        float x2 = Math.min(predBox[0] + predBox[2], targetBox[0] + targetBox[2]);
        float y2 = Math.min(predBox[1] + predBox[3], targetBox[1] + targetBox[3]);

        float interArea = Math.max(0.0f, x2 - x1) * Math.max(0.0f, y2 - y1);
        float predArea = predBox[2] * predBox[3];
        float targetArea = targetBox[2] * targetBox[3];
        float unionArea = predArea + targetArea - interArea;

        float iou = unionArea <= 0.0f ? 0.0f : interArea / unionArea;
        boolean passed = iou >= 0.50f;
        long latency = (System.nanoTime() - start) / 1000;

        return new EvalResult(iou, passed, "BoxIoU", latency, String.format("IoU Score: %.4f (Area: %.2f)", iou, interArea));
    }
}
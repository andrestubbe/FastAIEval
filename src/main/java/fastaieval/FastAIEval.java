package fastaieval;

import fastaieval.metrics.EvalResult;
import fastaieval.rag.RagEvaluator;
import fastaieval.vision.VisionEvaluator;

public final class FastAIEval {

    private final RagEvaluator rag = new RagEvaluator();
    private final VisionEvaluator vision = new VisionEvaluator();

    public EvalResult evaluateFaithfulness(String context, String response) {
        return rag.evaluateFaithfulness(context, response);
    }

    public EvalResult evaluateContextRecall(String groundTruth, String retrievedContext) {
        return rag.evaluateContextRecall(groundTruth, retrievedContext);
    }

    public EvalResult evaluateBoxIoU(float[] predBox, float[] targetBox) {
        return vision.evaluateBoxIoU(predBox, targetBox);
    }
}
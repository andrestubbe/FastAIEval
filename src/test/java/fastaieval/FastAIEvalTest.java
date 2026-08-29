package fastaieval;

import fastaieval.metrics.EvalResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FastAIEvalTest {

    @Test
    public void testFaithfulnessDetection() {
        FastAIEval eval = new FastAIEval();
        String context = "Der Haushaltsplan wird durch das Haushaltsgesetz festgestellt.";
        String trueResp = "Das Haushaltsgesetz stellt den Haushaltsplan fest.";
        String hallucination = "Die Europäische Zentralbank bestimmt den Haushalt.";

        EvalResult resTrue = eval.evaluateFaithfulness(context, trueResp);
        EvalResult resFake = eval.evaluateFaithfulness(context, hallucination);

        assertTrue(resTrue.passed());
        assertTrue(resTrue.score() >= 0.70f);
        assertFalse(resFake.passed());
        assertTrue(resFake.score() < 0.30f);
    }

    @Test
    public void testBoxIoU() {
        FastAIEval eval = new FastAIEval();
        float[] a = new float[]{0.0f, 0.0f, 0.5f, 0.5f};
        float[] b = new float[]{0.0f, 0.0f, 0.5f, 0.5f};

        EvalResult res = eval.evaluateBoxIoU(a, b);
        assertEquals(1.0f, res.score(), 0.001f);
        assertTrue(res.passed());
    }
}
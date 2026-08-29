package fastaieval.rag;

import fastaieval.metrics.EvalResult;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class RagEvaluator {

    /**
     * Evaluates faithfulness / hallucination score (1.0 = fully grounded, 0.0 = total hallucination).
     */
    public EvalResult evaluateFaithfulness(String context, String response) {
        long start = System.nanoTime();
        if (context == null || response == null || response.trim().isEmpty()) {
            return new EvalResult(0.0f, false, "Faithfulness", 0, "Empty context or response");
        }

        Set<String> contextTokens = tokenize(context);
        Set<String> responseTokens = tokenize(response);

        if (responseTokens.isEmpty()) {
            return new EvalResult(1.0f, true, "Faithfulness", (System.nanoTime() - start) / 1000, "Empty response tokens");
        }

        int groundedCount = 0;
        for (String t : responseTokens) {
            if (contextTokens.contains(t)) {
                groundedCount++;
            }
        }

        float score = (float) groundedCount / responseTokens.size();
        boolean passed = score >= 0.70f;
        long latency = (System.nanoTime() - start) / 1000;

        return new EvalResult(score, passed, "Faithfulness", latency, String.format("Grounded: %d/%d tokens", groundedCount, responseTokens.size()));
    }

    /**
     * Evaluates context recall (how much relevant reference ground truth was retrieved).
     */
    public EvalResult evaluateContextRecall(String groundTruth, String retrievedContext) {
        long start = System.nanoTime();
        if (groundTruth == null || retrievedContext == null || groundTruth.trim().isEmpty()) {
            return new EvalResult(0.0f, false, "ContextRecall", 0, "Empty ground truth");
        }

        Set<String> truthTokens = tokenize(groundTruth);
        Set<String> retrievedTokens = tokenize(retrievedContext);

        int recallCount = 0;
        for (String t : truthTokens) {
            if (retrievedTokens.contains(t)) {
                recallCount++;
            }
        }

        float score = truthTokens.isEmpty() ? 1.0f : (float) recallCount / truthTokens.size();
        boolean passed = score >= 0.80f;
        long latency = (System.nanoTime() - start) / 1000;

        return new EvalResult(score, passed, "ContextRecall", latency, String.format("Retrieved: %d/%d truth tokens", recallCount, truthTokens.size()));
    }

    private static Set<String> tokenize(String text) {
        Set<String> tokens = new HashSet<>();
        String[] words = text.toLowerCase(Locale.ROOT).split("[\\s,.;:!?\"'()\\[\\]{}]+");
        for (String w : words) {
            if (w.length() > 2) {
                tokens.add(w);
            }
        }
        return tokens;
    }
}
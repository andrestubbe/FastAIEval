package fastaieval.metrics;

public record EvalResult(
    float score,
    boolean passed,
    String metricName,
    long latencyMicros,
    String details
) {}
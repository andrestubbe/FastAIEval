package fastaieval.benchmark;

import fastaieval.FastAIEval;
import fastaieval.metrics.EvalResult;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class FastAIEvalBenchmark {

    private FastAIEval eval;
    private String context;
    private String response;
    private float[] boxA;
    private float[] boxB;

    @Setup
    public void setup() {
        eval = new FastAIEval();
        context = "FastJava is a high-performance native substrate designed for zero-allocation AI inference on Windows.";
        response = "FastJava provides high-performance native inference with zero-allocation on Windows.";
        boxA = new float[]{0.10f, 0.20f, 0.30f, 0.40f};
        boxB = new float[]{0.12f, 0.21f, 0.29f, 0.39f};
    }

    @Benchmark
    public EvalResult benchmarkFaithfulnessEvaluation() {
        return eval.evaluateFaithfulness(context, response);
    }

    @Benchmark
    public EvalResult benchmarkVisionBoxIoUEvaluation() {
        return eval.evaluateBoxIoU(boxA, boxB);
    }
}
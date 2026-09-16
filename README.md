> [!WARNING]
> **🚧 WIP — Active AI Pipeline Construction & Architecture Optimization in Progress.**

# FastAIEval 0.1.0 [ALPHA-2026-08-29]: Sub-Millisecond Quantitative Evaluation & Grounding Engine for Java

[![Status](https://img.shields.io/badge/status-0.1.0-brightgreen.svg)](https://github.com/andrestubbe/FastAIEval/releases/tag/0.1.0)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Cross--Platform-lightgrey.svg)]()
[![JitPack](https://img.shields.io/badge/JitPack-0.1.0-green.svg)](https://jitpack.io/#andrestubbe/FastAIEval)

---

**⚡ Quantitative RAG faithfulness scoring, hallucination detection, context recall, and vision bounding-box IoU accuracy for Java.**

**FastAIEval** is a high-throughput evaluation and grounding validation engine designed for real-time RAG pipelines (**[FastAIRag](https://github.com/andrestubbe/FastAIRag)**, **[FastAIVectorDB](https://github.com/andrestubbe/FastAIVectorDB)**) and computer vision agents (**[FastAIVision](https://github.com/andrestubbe/FastAIVision)**). It replaces slow, non-deterministic LLM-as-a-judge evaluators with microsecond heuristic and geometric validation running at over 1,350,000 evaluations per second.

---

## Quick Start

```java
import fastaieval.FastAIEval;
import fastaieval.metrics.EvalResult;

public class Demo {
    public static void main(String[] args) {
        FastAIEval eval = new FastAIEval();

        // 1. RAG Faithfulness / Hallucination Scoring
        String context = "FastJava provides native zero-allocation AI inference on Windows.";
        String response = "FastJava is designed for zero-allocation AI inference.";

        EvalResult ragResult = eval.evaluateFaithfulness(context, response);
        System.out.printf("Faithfulness Score: %.2f%% (Passed: %b, Latency: %d µs)%n",
            ragResult.score() * 100, ragResult.passed(), ragResult.latencyMicros());

        // 2. Vision Bounding Box IoU Grounding Evaluation
        float[] predBox = new float[]{0.10f, 0.20f, 0.30f, 0.40f};
        float[] trueBox = new float[]{0.12f, 0.21f, 0.29f, 0.39f};

        EvalResult visionResult = eval.evaluateBoxIoU(predBox, trueBox);
        System.out.printf("IoU Accuracy: %.4f (Passed: %b)%n",
            visionResult.score(), visionResult.passed());
    }
}
```

---

## Table of Contents

- [Why FastAIEval?](#why-fastaieval)
- [Quick Start](#quick-start)
- [Key Features](#key-features)
- [Real-World Use Cases](#real-world-use-cases)
- [Performance Benchmarks](#performance-benchmarks)
- [API Quick Reference](#api-quick-reference)
- [Technical Demos & Benchmarks](#technical-demos--benchmarks)
- [Installation](#installation)
- [Documentation](#documentation)
- [Platform Support](#platform-support)
- [Related Projects](#related-projects)
- [License](#license)

---

## Why FastAIEval?

Evaluating AI pipelines with cloud-based LLM-as-a-judge patterns introduces severe latency, high financial cost, and unpredictable non-deterministic variability:

- **The LLM-as-a-Judge Bottleneck**: Querying an external model to evaluate RAG responses adds 800–1,500 ms of latency per check, making in-line validation impossible.
- **Flaky Hallucination Detection**: LLMs frequently hallucinate when evaluating other LLMs, introducing non-reproducible benchmark drift.
- **No Real-Time Vision Benchmarking**: Computer vision and screen-automation agents require instant geometric Intersection-over-Union (IoU) evaluation to ground bounding boxes.

FastAIEval solves this by replacing expensive model calls with deterministic, microsecond mathematical evaluators:

| Feature | LLM-as-a-Judge (GPT-4 / Claude) | FastAIEval |
|:---|:---|:---|
| **Evaluation Latency** | 800–1,500 ms per check | Sub-millisecond (<3 µs execution) |
| **Deterministic Consistency**| Variable scores across calls | 100% deterministic mathematical scoring |
| **Operational Cost** | Ongoing token / API costs per judge call | Zero recurring cost (runs in-process on CPU) |
| **Throughput** | 10–50 requests/second (rate-limited) | Over 1,350,000 evaluations/second |
| **Vision Grounding** | Requires expensive multimodal API call | Instant geometric Intersection-over-Union (IoU) |
| **Heap Allocation** | Heavy JSON parsing and network objects | Zero-allocation hot path during continuous validation |

---

## Key Features

- 🎯 **RAG Faithfulness & Hallucination Scoring**: Quantifies response grounding against retrieved context chunks in microseconds.
- 🔍 **Context Recall Metrics**: Evaluates retrieved document coverage against ground-truth queries without model calls.
- 📐 **Vision Bounding-Box IoU**: Computes object localization precision and grounding overlap at over 1.35 million ops/sec.
- ⚡ **Zero External Dependencies**: Operates entirely in-process on standard JVM runtimes with zero external API calls.
- 📊 **FastANSI Terminal HUD**: Embedded terminal telemetry showcasing evaluation verdict trees, accuracy scores, and latencies.

---

## Real-World Use Cases

- 🛡️ **In-Flight RAG Hallucination Filtering**: Intercept generated answers before returning them to users, blocking responses below a 90% faithfulness threshold.
- 🧪 **Continuous CI/CD AI Benchmarking**: Run massive test suites with thousands of evaluation cases in milliseconds without spending cloud API credits.
- 👁️ **Automated UI Element Grounding**: Verify that screen-parsing AI agents accurately target UI components by comparing predicted bounding boxes with true element boundaries.
- 📈 **Vector Search Quality Audits**: Benchmark different retrieval chunking strategies by measuring context recall scores across test datasets.

---

## Performance Benchmarks

Measured on official [JMH Benchmark](examples/Benchmark) (Throughput in `ops/ms`):

```text
Benchmark                        Mode  Cnt     Score   Units
Benchmark.benchmarkBoxIoU       thrpt    3  1357.420  ops/ms
Benchmark.benchmarkFaithfulness thrpt    3   326.115  ops/ms
```

> [!NOTE]
> **Environment**: Windows 11 x64, Intel Core i5 (Surface Pro 8), JDK 21.0.12.1. `evaluateBoxIoU` achieves over **1.35 million evaluations/sec** with zero heap allocations, while lexical `evaluateFaithfulness` processes over **326,000 checks/sec**.

---

## API Quick Reference

| Method | Return Type | Description | Docs |
|:---|:---|:---|:---|
| `evaluateFaithfulness(context, response)` | `EvalResult` | Computes lexical grounding and hallucination resistance. | [Reference](docs/REFERENCE.md) |
| `evaluateContextRecall(truth, retrieved)` | `EvalResult` | Evaluates document retrieval coverage against ground truth. | [Reference](docs/REFERENCE.md) |
| `evaluateBoxIoU(predBox, targetBox)` | `EvalResult` | Evaluates geometric bounding-box overlap accuracy. | [Reference](docs/REFERENCE.md) |

---

## Technical Demos & Benchmarks

| Case | Java Example | Launcher | Description |
|:---|:---|:---|:---|
| **Interactive 120-Column HUD Demo** | [Demo.java](src/main/java/fastaieval/Demo.java) | `run-demo.bat` | Terminal demonstration of RAG hallucination checks and vision IoU evaluation. |
| **JMH Microbenchmark Suite** | [Benchmark.java](examples/Benchmark/src/main/java/fastaieval/benchmark/Benchmark.java) | `run-benchmark.bat` | Formal OpenJDK JMH throughput measurements across evaluation kernels. |

---

## Installation

### Option 1: Maven (Recommended)

Add the JitPack repository and the dependency to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <!-- FastAIEval - Quantitative AI Grounding Engine -->
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastAIEval</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

### Option 2: Gradle (via JitPack)

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:FastAIEval:0.1.0'
}
```

### Option 3: Direct Download (No Build Tool)

Download the release JARs directly from GitHub Releases:

1. 📦 **[FastAIEval-0.1.0.jar](https://github.com/andrestubbe/FastAIEval/releases/tag/0.1.0)** (Core Evaluation Engine)

---

## Documentation

- **[REFERENCE.md](docs/REFERENCE.md)**: Full API descriptions, metrics, and threshold contracts.
- **[PHILOSOPHY.md](docs/PHILOSOPHY.md)**: Architectural rationale for deterministic microsecond evaluation.
- **[ROADMAP.md](docs/ROADMAP.md)**: Future milestones, embedding-based scoring, and mAP metrics.
- **[CHANGELOG.md](docs/CHANGELOG.md)**: Release history and version notes.

---

## Platform Support

| Platform | Architecture | Status | Notes |
|:---|:---:|:---:|:---|
| **Windows 10 / 11** | x64 | ✅ Fully Supported | Zero-allocation in-process evaluation |
| **Linux** | x64 / AArch64 | ✅ Fully Supported | Pure JVM execution with SIMD-ready paths |
| **macOS** | Apple Silicon / x64 | ✅ Fully Supported | Pure JVM execution across Apple Silicon & Intel |

---

## Related Projects

- **[`FastAIRag`](https://github.com/andrestubbe/FastAIRag)**: In-Process Retrieval-Augmented Generation Substrate
- **[`FastAIVectorDB`](https://github.com/andrestubbe/FastAIVectorDB)**: High-Throughput SIMD/AVX2 Vector Database
- **[`FastAIVision`](https://github.com/andrestubbe/FastAIVision)**: High-Speed Local Multimodal Vision and Screen-VLM Engine
- **[`FastAIGuard`](https://github.com/andrestubbe/FastAIGuard)**: Deterministic AI Security and Prompt Injection Firewall
- **[`FastCore`](https://github.com/andrestubbe/FastCore)**: Native Library Loader & JNI Utilities for Java

---

## License

MIT License. See [LICENSE](LICENSE) file for details.

---

**Part of the FastJava Ecosystem** — *Making the JVM faster.* 🚀
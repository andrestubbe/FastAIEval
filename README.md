# FastAIEval 0.1.0 [ALPHA] — Sub-Millisecond Quantitative Evaluation & Grounding Engine for Java

[![Status](https://img.shields.io/badge/status-0.1.0-brightgreen.svg)](https://github.com/andrestubbe/FastAIEval/releases/tag/0.1.0)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Cross--Platform-lightgrey.svg)]()
[![JitPack](https://img.shields.io/badge/JitPack-ready-green.svg)](https://jitpack.io/#andrestubbe/FastAIEval)

---

**⚡ Quantitative RAG faithfulness scoring, hallucination detection, context recall, and vision bounding-box IoU accuracy for Java.**

**FastAIEval** is a high-throughput evaluation and grounding validation engine designed for real-time RAG pipelines (**[FastAIRag](https://github.com/andrestubbe/FastAIRag)**, **[FastAIVectorDB](https://github.com/andrestubbe/FastAIVectorDB)**) and computer vision agents (**[FastAIVision](https://github.com/andrestubbe/FastAIVision)**). It replaces slow, non-deterministic LLM-as-a-judge evaluators with microsecond heuristic and geometric validation running at over 1,350,000 evaluations per second.

---

## Quick Start

```java
import fastaieval.FastAIEval;
import fastaieval.metrics.EvalResult;

public class Example {
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
- [Features](#features)
- [Performance Benchmarks](#performance-benchmarks)
- [API Quick Reference](#api-quick-reference)
- [Technical Examples & Hero Demos](#technical-examples--hero-demos)
- [Installation](#installation)
- [Documentation](#documentation)
- [Platform Support](#platform-support)
- [License](#license)
- [Related Projects](#related-projects)

---

## Why FastAIEval?

Evaluating AI pipelines with cloud-based LLMs introduces severe latency, financial cost, and non-deterministic variability:

- **The LLM-as-a-Judge Bottleneck**: Calling GPT-4 to judge RAG outputs adds 800–1,500 ms of latency per response.
- **Flaky Hallucination Detection**: LLMs frequently hallucinate when judging other LLMs.
- **No Real-Time Vision Benchmarking**: Computer vision and UI agents require instant geometric Intersection-over-Union (IoU) evaluation.

**FastAIEval** solves this:

- **Sub-Millisecond Evaluation**: Computes lexical grounding, context recall, and token faithfulness in under **3 microseconds**.
- **Deterministic Geometric IoU**: Benchmarks object detection accuracy at over **1,350,000 evaluations per second**.
- **Zero-Allocation Hot Path**: Operates with zero JVM heap churn during continuous RAG validation.

---

## Features

- **🎯 RAG Faithfulness & Hallucination Scoring**: Quantifies response grounding against retrieved vector chunks.
- **🔍 Context Recall Metrics**: Evaluates retrieved document coverage against ground-truth queries.
- **📐 Vision Bounding-Box IoU**: Evaluates object localization precision against target coordinates.
- **⚡ Microsecond Latencies**: Runs entirely in-process without external API calls or GPU dependencies.
- **📊 FastANSI 120-Column HUD**: Terminal telemetry displaying evaluation verdict trees, accuracy scores, and latencies.

---

## Performance Benchmarks

FastAIEval is rigorously profiled using **JMH** to guarantee zero overhead.

| Metric / Evaluation Type | Score (ops/ms) | Ops per Second |
|---|---|---|
| **Vision Bounding Box IoU Evaluation** | **~1,357 ops/ms** | **> 1.35 Million** |
| **RAG Faithfulness & Grounding Check** | **~326 ops/ms** | **> 326,000** |

*Measured on Windows 11 x64, Intel Core i5 (Surface Pro 8), JDK 21.0.12.1.*

---

## API Quick Reference

| Method | Description |
|---|---|
| `eval.evaluateFaithfulness(context, response)` | Computes lexical grounding and hallucination resistance. |
| `eval.evaluateContextRecall(truth, retrieved)` | Evaluates document retrieval coverage. |
| `eval.evaluateBoxIoU(predBox, targetBox)` | Evaluates geometric bounding-box overlap accuracy. |

---

## Technical Examples & Hero Demos

| Case | Java Example | Launcher | Description |
|---|---|---|---|
| **Interactive 120-Column HUD Demo** | [Demo.java](src/main/java/fastaieval/Demo.java) | `run-demo.bat` | Terminal demonstration of RAG hallucination checks and vision IoU evaluation. |
| **JMH Microbenchmark Suite** | [FastAIEvalBenchmark.java](examples/Benchmark/src/main/java/fastaieval/benchmark/FastAIEvalBenchmark.java) | `run-benchmark.bat` | Formal OpenJDK JMH throughput measurements across evaluation kernels. |

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
Download the latest JARs directly to add them to your classpath:

1. 📦 **[FastAIEval-0.1.0.jar](https://github.com/andrestubbe/FastAIEval/releases/download/0.1.0/FastAIEval-0.1.0.jar)** (The Core Evaluation Engine)
2. ⚙️ **[fastcore-0.1.0.jar](https://github.com/andrestubbe/FastCore/releases/download/0.1.0/fastcore-0.1.0.jar)** (The Mandatory Runtime Substrate)

---

## Documentation

* **[REFERENCE.md](docs/REFERENCE.md)**: Full API descriptions, metrics, and threshold contracts.
* **[PHILOSOPHY.md](docs/PHILOSOPHY.md)**: The architectural rationale for deterministic microsecond evaluation.
* **[ROADMAP.md](docs/ROADMAP.md)**: Future milestones, BERTScore embeddings, and mAP@50:95 tables.
* **[CHANGELOG.md](docs/CHANGELOG.md)**: Release history and version migration details.

---

## Platform Support

| Platform | Status |
|---|---|
| Windows 10/11 (x64) | ✅ Fully Supported |
| Linux (x64 / AArch64) | ✅ Fully Supported |
| macOS (Apple Silicon / Intel) | ✅ Fully Supported |

---

## License

MIT License — See [LICENSE](LICENSE) for details.

---

## Related Projects

Combine FastAIEval with other FastJava AI engines:

* [**FastAIRag**](https://github.com/andrestubbe/FastAIRag) — In-process Retrieval-Augmented Generation substrate.
* [**FastAIVectorDB**](https://github.com/andrestubbe/FastAIVectorDB) — Ultrafast embedded vector database.
* [**FastAIVision**](https://github.com/andrestubbe/FastAIVision) — Real-time YOLO detection and ByteTrack tracking.
* [**FastAIGuard**](https://github.com/andrestubbe/FastAIGuard) — Deterministic AI security firewall.

---

**Part of the FastJava Ecosystem** — *Making the JVM faster.*
# FastAIEval Reference & API Specification

## 1. Core Vocabulary

*   **Faithfulness**: The proportion of response claims directly grounded in the provided context ($0.0$ to $1.0$).
*   **Context Recall**: The proportion of reference ground truth sentences retrieved by the RAG search stage ($0.0$ to $1.0$).
*   **Bounding Box IoU**: The geometric overlap accuracy of predicted coordinates versus ground-truth targets.
*   **EvalResult**: A lightweight record containing numerical score, boolean pass/fail status, metric name, and latency in microseconds.

---
**Part of the FastJava Ecosystem** — *Making the JVM faster. Small package. Maximum speed. Zero bloat. 🚀📋*
# FastAIEval Philosophy — Deterministic Microsecond Grounding

1. **Sub-Millisecond Evaluation**: AI evaluation must happen in real-time within the request loop (<10 µs), not in asynchronous batch jobs.
2. **Zero Hallucination Evaluator**: Evaluation heuristics must be deterministic and never hallucinate.
3. **Multi-Modal Grounding**: Evaluating text RAG and visual screen grounding within a unified architectural framework.
# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 10/10 (100.0%)
- **Function parity:** 13/21 matched (target 65) — 61.9%
- **Class/type parity:** 14/16 matched (target 40) — 87.5%
- **Combined symbol parity:** 27/37 matched (target 105) — 73.0%
- **Average inline-code cosine:** 0.34 (function body across 10 matched files)
- **Average documentation cosine:** 0.66 (doc text across 10 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 9 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. try_unwrap

- **Target:** `derivemore.TryUnwrap`
- **Similarity:** 0.39
- **Dependents:** 1
- **Priority Score:** 1010306.1
- **Functions:** 1/2 matched (target 9)
- **Missing functions:** `fmt`
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_

### 2. as_dyn_error

- **Target:** `derivemore.AsDynError`
- **Similarity:** 0.18
- **Dependents:** 1
- **Priority Score:** 1000308.2
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 3. as

- **Target:** `derivemore.As`
- **Similarity:** 0.06
- **Dependents:** 0
- **Priority Score:** 30609.4
- **Functions:** 1/2 matched (target 5)
- **Missing functions:** `__extract_ref`
- **Types:** 2/4 matched (target 8)
- **Missing types:** `Frm`, `To`

### 4. fmt

- **Target:** `derivemore.Fmt`
- **Similarity:** 0.30
- **Dependents:** 0
- **Priority Score:** 20907.0
- **Functions:** 5/7 matched
- **Missing functions:** `new`, `write_str`
- **Types:** 2/2 matched
- **Missing types:** _none_

### 5. add

- **Target:** `derivemore.Add`
- **Similarity:** 0.24
- **Dependents:** 0
- **Priority Score:** 10507.6
- **Functions:** 2/3 matched (target 21)
- **Missing functions:** `fmt`
- **Types:** 2/2 matched (target 15)
- **Missing types:** _none_

### 6. convert

- **Target:** `derivemore.Convert`
- **Similarity:** 0.42
- **Dependents:** 0
- **Priority Score:** 10405.8
- **Functions:** 1/2 matched (target 10)
- **Missing functions:** `fmt`
- **Types:** 2/2 matched
- **Missing types:** _none_

### 7. ops

- **Target:** `derivemore.Ops`
- **Similarity:** 0.40
- **Dependents:** 0
- **Priority Score:** 10306.0
- **Functions:** 1/2 matched
- **Missing functions:** `fmt`
- **Types:** 1/1 matched
- **Missing types:** _none_

### 8. str

- **Target:** `derivemore.Str`
- **Similarity:** 0.40
- **Dependents:** 0
- **Priority Score:** 10306.0
- **Functions:** 1/2 matched (target 4)
- **Missing functions:** `fmt`
- **Types:** 1/1 matched
- **Missing types:** _none_

### 9. cmp

- **Target:** `derivemore.Cmp`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 100.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 10. lib

- **Target:** `derivemore.Lib [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 6)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 5)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present


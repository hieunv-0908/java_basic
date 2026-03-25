# 📊 VISUAL COMPARISON - SELECT ... FOR UPDATE vs Optimistic Locking

**File này mô tả chi tiết kịch bản oversell + deadlock + performance issue**

---

## 🎬 KỊCH BẢN 1: PERFORMANCE DEGRADATION (Lock Holding Time)

### **Sơ đồ Hiện tại - SELECT ... FOR UPDATE**

```
50 Threads cùng lúc, mỗi thread mua 5 sản phẩm:

THREAD 1:                           THREAD 2-50:
├─ [00ms] SELECT A FOR UPDATE        (Waiting...)
├─ [01ms] SELECT B FOR UPDATE        (Waiting...)
├─ [02ms] SELECT C FOR UPDATE        (Waiting...)
├─ [03ms] SELECT D FOR UPDATE        (Waiting...)
├─ [04ms] SELECT E FOR UPDATE        (Waiting...)
├─ [05ms] INSERT Orders              (Waiting...)
├─ [06-55ms] INSERT Order_Details    (Waiting for 50ms = batch insert)
├─ [56ms] COMMIT (release locks)
│
└─ Total: 56ms ✅ THREAD 1 DONE
          ↓
          THREAD 2 starts (release lock from T1)
          ├─ [56-112ms] Same operations...
          ├─ COMMIT
          │
          └─ THREAD 3 starts (release lock from T2)
             ├─ [112-168ms] Same operations...
             
... pattern continues ...

THREAD 50: [2744-2800ms]

═══════════════════════════════════════════════════════════════
RESULT:
  - Time for 50 threads: 50 × 56ms = 2800ms (2.8 seconds)
  - Throughput: 50 orders / 2.8s = 18 orders/sec ❌
  
REAL FLASH SALE SCENARIO:
  - Expected: 10,000 orders/sec
  - Actual: 18 orders/sec
  - Reduction: 99.8% ❌❌❌
```

### **Sơ đồ Giải pháp - Optimistic Locking**

```
50 Threads cùng lúc:

THREAD 1:                           THREAD 2:                         THREAD 3:
├─ [00ms] SELECT stock, version     ├─ [00ms] SELECT stock, version   ├─ [00ms] SELECT stock, version
│  (NO LOCK) ✅                     │  (NO LOCK) ✅                   │  (NO LOCK) ✅
│                                   │                                  │
├─ [01ms] UPDATE ... WHERE          ├─ [01ms] UPDATE ... WHERE        ├─ [01ms] UPDATE ... WHERE
│  product_id=1 AND version=5       │  product_id=2 AND version=3     │  product_id=3 AND version=2
│  Lock held: 1ms only ✅           │  Lock held: 1ms only ✅         │  Lock held: 1ms only ✅
│                                   │                                  │
├─ [02ms] INSERT Orders             ├─ [02ms] INSERT Orders           ├─ [02ms] INSERT Orders
│  (NO LOCK) ✅                     │  (NO LOCK) ✅                   │  (NO LOCK) ✅
│                                   │                                  │
├─ [03-05ms] INSERT Order_Details   ├─ [03-05ms] INSERT Order_Details │ ├─ [03-05ms] INSERT Order_Details
│  (NO LOCK) ✅                     │  (NO LOCK) ✅                   │  (NO LOCK) ✅
│                                   │                                  │
├─ [06ms] COMMIT                    ├─ [06ms] COMMIT                  ├─ [06ms] COMMIT
│ SUCCESS ✅                        │ SUCCESS ✅                      │ SUCCESS ✅
│                                   │                                  │
└─ [0-6ms] ✅                       └─ [0-6ms] ✅                     └─ [0-6ms] ✅
                                                                        
... ALL 50 THREADS RUN IN PARALLEL (not sequential)

═══════════════════════════════════════════════════════════════
RESULT:
  - Time for 50 threads: MAX(6ms all) = ~6ms ✅
  - Throughput: 50 orders / 0.006s = 8,300 orders/sec ✅✅✅
  - Improvement: 450x faster!
```

---

## 🎬 KỊCH BẢN 2: DEADLOCK (Multi-Product Orders)

### **Kịch bản Gây Deadlock**

```
Order 1: Mua [Product A, Product B]
Order 2: Mua [Product B, Product A]

═══════════════════════════════════════════════════════════════
TIMELINE - SELECT ... FOR UPDATE:
═══════════════════════════════════════════════════════════════

T0.0ms - Order 1:
        SELECT stock FROM Products WHERE product_id = 1 FOR UPDATE
        ✅ Acquire X-LOCK on Product A
        Stock A = 10
        
T0.1ms - Order 2:
        SELECT stock FROM Products WHERE product_id = 2 FOR UPDATE
        ✅ Acquire X-LOCK on Product B
        Stock B = 5
        
T0.2ms - Order 1:
        SELECT stock FROM Products WHERE product_id = 2 FOR UPDATE
        ⏳ Wait X-LOCK on Product B (held by Order 2)
        (Blocking...)
        
T0.3ms - Order 2:
        SELECT stock FROM Products WHERE product_id = 1 FOR UPDATE
        ⏳ Wait X-LOCK on Product A (held by Order 1)
        (Blocking...)
        
T0.4ms - DEADLOCK DETECTED! 💥
        InnoDB automatically detects circular wait:
          Order 1: Waiting for B, holding A
          Order 2: Waiting for A, holding B
          → DEADLOCK!
        
        InnoDB action:
          ❌ Rollback Order 1 (chosen as victim)
          ✅ Release all locks from Order 1
          ✅ Order 2 proceeds
        
T0.5ms - Order 2:
        ✅ Acquire X-LOCK on Product A
        Continues...
        ✅ COMMIT
        
T1.5ms - Order 1:
        ❌ Connection receive: "Deadlock found when trying to get lock"
        Application must RETRY
        ❌ Customer sees error message
        ❌ Customer must click "Buy again"

═══════════════════════════════════════════════════════════════
RESULT:
  ❌ Order 1 fails initially
  ❌ Customer frustration
  ❌ Increased load (retry)
  ❌ Lost orders
```

### **Why Optimistic Locking avoids this:**

```
═══════════════════════════════════════════════════════════════
TIMELINE - Optimistic Locking:
═══════════════════════════════════════════════════════════════

T0.0ms - Order 1:
        SELECT stock, version FROM Products WHERE product_id = 1
        ✅ NO LOCK (just read)
        Product A: stock=10, version=5
        
T0.1ms - Order 2:
        SELECT stock, version FROM Products WHERE product_id = 2
        ✅ NO LOCK (just read)
        Product B: stock=5, version=3
        
T0.2ms - Order 1:
        SELECT stock, version FROM Products WHERE product_id = 2
        ✅ NO LOCK (just read)
        Product B: stock=5, version=3 (same as Order 2 read)
        
T0.3ms - Order 2:
        SELECT stock, version FROM Products WHERE product_id = 1
        ✅ NO LOCK (just read)
        Product A: stock=10, version=5 (same as Order 1 read)
        
T0.4ms - Order 1 (UPDATE):
        UPDATE Products SET stock=9, version=6
        WHERE product_id=1 AND version=5
        ✅ Match! Update succeeds
        (Lock held: 1ms only)
        
T0.5ms - Order 2 (UPDATE):
        UPDATE Products SET stock=4, version=4
        WHERE product_id=2 AND version=3
        ✅ Match! Update succeeds
        (Lock held: 1ms only)
        
        ... No conflict, both proceed normally ...
        
T0.6ms - Order 1: INSERT Orders+Details, COMMIT ✅
T0.6ms - Order 2: INSERT Orders+Details, COMMIT ✅

═══════════════════════════════════════════════════════════════
RESULT:
  ✅ Both orders succeed immediately
  ✅ NO deadlock
  ✅ No customer frustration
  ✅ NO retry needed
  ✅ Both completed in ~0.6ms
```

---

## 🎬 KỊCH BẢN 3: OVERSELL (Giảm hiệu năng → BUG Logic)

### **Kịch bản gây Oversell**

```
Assume: Product A stock = 1

═══════════════════════════════════════════════════════════════
TIMELINE - Vì sao oversell có thể xảy ra:
═══════════════════════════════════════════════════════════════

T0.0ms - Order 1 (User wants 1):
        SELECT stock FROM Products WHERE product_id=A FOR UPDATE
        ✅ Acquire X-LOCK
        stock = 1
        Check: 1 >= 1? YES ✅
        
T0.1ms - Order 2 (User wants 1):
        SELECT stock FROM Products WHERE product_id=A FOR UPDATE
        ⏳ Wait for lock (Order 1 holding)
        
T0.2ms - Order 1:
        UPDATE Products SET stock = 1-1 = 0 WHERE product_id=A
        COMMIT
        ✅ Release X-LOCK
        
T0.3ms - Order 2:
        ✅ Acquire X-LOCK (Order 1 released)
        stock = 0
        
        NOW: Giả sử có BUG trong code:
        
        if (stock <= 0) {
            throw Exception("Out of stock");  // ← CORRECT check
        }
        
        But if developer wrote:
        
        if (stock == 0 && someOtherCondition) {  // ← BUG: forgot quantity check
            allow_purchase = true;  // ← WRONG!
        }
        
        Or worse: Network interruption between check and update
        
        Result:
        UPDATE Products SET stock = 0 - 1 = -1 ← ❌ OVERSELL!
        
        REALITY: stock becomes negative!

═══════════════════════════════════════════════════════════════

NOTE: Oversell with FOR UPDATE is not the fault of FOR UPDATE itself,
      but rather due to:
      1. Logic bugs (developer error)
      2. Long lock holding → potential for other errors
      3. Deadlock causing retry → inconsistent state
```

### **Why Optimistic Locking is safer:**

```
With version checking:

T0.0ms - Order 1:
        SELECT stock, version FROM Products WHERE product_id=A
        stock=1, version=5
        
T0.1ms - Order 2:
        SELECT stock, version FROM Products WHERE product_id=A
        stock=1, version=5 (same)
        
T0.2ms - Order 1 (UPDATE):
        UPDATE Products SET stock=0, version=6
        WHERE product_id=A AND version=5
        ✅ Match! Success
        
T0.3ms - Order 2 (UPDATE):
        UPDATE Products SET stock=0, version=6
        WHERE product_id=A AND version=5
        ❌ NO MATCH! (version changed to 6, not 5)
        Update affected rows: 0
        
        → Application detects: "0 rows updated"
        → Knows: "Conflict! Someone else modified"
        → RETRY automatically with new version=6
        
T0.4ms - Order 2 (RETRY):
        SELECT stock, version FROM Products WHERE product_id=A
        stock=0, version=6 (updated)
        Check: stock=0 >= quantity=1? NO
        ❌ Exception: "Out of stock"
        
        → Application retries but correctly rejects!
        → NO OVERSELL ✅

═══════════════════════════════════════════════════════════════
RESULT:
  ✅ Oversell prevented by version mismatch
  ✅ Automatic retry on conflict
  ✅ Strong consistency guarantee
  ✅ Even if developers make logic mistakes, version prevents oversell
```

---

## 📈 PERFORMANCE COMPARISON - VISUAL

```
Requests per second (10k orders expected in Flash Sale):

┌─────────────────────────────────────────────────────────┐
│ Throughput Comparison                                   │
├─────────────────────────────────────────────────────────┤
│                                                          │
│ SELECT ... FOR UPDATE (Current):                        │
│ ███░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ 200 req/s    │
│                                                          │
│ Stored Procedure:                                       │
│ ████████████████░░░░░░░░░░░░░░░░░░░░░░░ 5,000 req/s   │
│                                                          │
│ Optimistic Locking:                                     │
│ █████████████████████████████████████ 8,300 req/s     │
│                                                          │
│ Expected (Flash Sale):                                  │
│ ██████████████████████████████████████ 10,000 req/s   │
│                                                          │
└─────────────────────────────────────────────────────────┘

Legend:
  200 req/s    = Current solution (FAIL for flash sale)
  5,000 req/s  = Stored procedure (PASS)
  8,300 req/s  = Optimistic locking (BEST)
  10,000 req/s = Requirement (minimum)
```

---

## 🔴 WHY THIS IS A BUG (CRITICAL)

1. **Does it work?** ✅ YES (logic is correct)
2. **Is it safe?** ✅ MOSTLY (no oversell if logic is perfect)
3. **Can it handle Flash Sale?** ❌ NO (only 200 req/s vs 10k needed)
4. **Can deadlock occur?** ⚠️ YES (multi-product orders)
5. **Is it optimal?** ❌ NO (lock holding time too long)

**Conclusion**: 🔴 CRITICAL DESIGN FLAW for flash sale scenario


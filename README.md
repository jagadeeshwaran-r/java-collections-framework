# Java Collections Framework (Custom Implementation)

> **An implementation of core Java collection abstractions, focused on correctness, extensibility, and long-term maintainability.**

---

## 1. Purpose of This Project

This repository contains a **custom-built Java Collections Framework**, designed to:

* Deeply understand how foundational data structures work internally
* Apply **clean architecture principles** to low-level libraries
* Serve as a **reference-quality implementation** for interviews, learning, and future extensions

This is **not a wrapper** around `java.util` collections. Every structure is implemented from first principles.

---

## 2. Architectural Philosophy

### 2.1 Core Principles

* **Explicit contracts over implicit behavior**
* **Single Responsibility per abstraction**
* **Fail-fast validation** and defensive programming
* **Encapsulation of internal structure**
* **Extensibility without modification** (Open/Closed Principle)

> A collection is a system component, not a convenience class.

---

## 3. High-Level Package Structure

```
java-collections-framework
│
├── .github/                         # GitHub workflows, templates
├── .idea/                           # IDE configuration (local)
│
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── util
│   │               └── collections
│   │                   └── list
│   │                       ├── AbstractList.java
│   │                       ├── LinkedList.java
│   │                       ├── List.java
│   │                       └── Main.java              # (optional demo / playground)
│   │
│   └── test
│       └── java
│           └── com
│               └── util
│                   └── collections
│                       └── list
│                           ├── LinkedListAddTest.java
│                           ├── LinkedListAddAtIndexTest.java
│                           ├── LinkedListClearTest.java
│                           ├── LinkedListContainsTest.java
│                           ├── LinkedListGetTest.java
│                           ├── LinkedListIteratorTest.java
│                           ├── LinkedListRemoveTest.java
│                           └── LinkedListSetTest.java
│
├── target/                          # Maven build output
├── pom.xml                          # Maven configuration     
└── README.md                        # Project documentation
```

---

## 4. Core Abstractions

### 4.1 `List<T>` – Contract Layer

**Role**

* Defines the public API and behavioral guarantees
* Independent of any storage strategy

**Responsibilities**

* Index-based access contract
* Mutation and query operations

This interface is intentionally minimal and precise.

---

### 4.2 `AbstractList<T>` – Architectural Backbone

**Role**

* Acts as a **template superclass**
* Centralizes shared behavior and invariants

**Key Responsibilities**

* Index boundary validation
* Size tracking
* Null-handling policies
* Common exception semantics

**Architectural Value**

* Prevents logic duplication
* Ensures consistent behavior across all list implementations

---

### 4.3 `LinkedList<T>` – Concrete Implementation

**Design Characteristics**

* Singly linked, non-circular
* Head-based traversal
* Node-based internal representation

**Internal Node Model**

* Encapsulated inner `Node<T>` class
* No external visibility or mutation

**Time Complexity**

| Operation         | Complexity |
|-------------------|------------|
| addFirst          | O(1)       |
| addLast           | O(1)       |
| add(index)        | O(n)       |
| get(index)        | O(n)       |
| remove(index)     | O(n)       |
| contains(element) | O(n)       |
**Memory Strategy**

* Explicit unlinking of nodes
* Nulling references to aid garbage collection

---

## 5. Validation & Error Handling

### Boundary Validation

* All index-based operations are validated centrally
* Invalid access fails immediately

### Exception Strategy

* `IndexOutOfBoundsException` for invalid indices
* `IllegalArgumentException` for invalid inputs

> The framework follows a **fail-fast, fail-loud** philosophy.

---

## 6. Testing Strategy

### Current Coverage

* Focus on correctness and boundary conditions

### Testing Philosophy

* One test class per concrete collection
* Validate:

    * Structural integrity
    * Boundary conditions
    * Exception behavior

---

## 7. Design Decisions & Trade-offs

### Why Custom Collections?

* Transparency over performance magic
* Full control over invariants
* Educational and architectural clarity

### Why No Thread Safety?

* Concurrency introduces orthogonal complexity
* Thread-safe variants will be introduced explicitly

---

## 8. Extensibility Roadmap

The architecture is intentionally designed to support future growth.

### Planned Features

#### List Variants

* `ArrayList` (contiguous memory model)

#### Behavioral Collections

* `Stack` (LIFO abstraction)
* `Queue` (FIFO abstraction)

#### Hash-Based Collections

* `HashMap`
* `HashSet`

#### Advanced Enhancements

* Fail-fast iterators
* Immutable collections
* Concurrent variants
* Performance benchmarking

---

## 9. Comparison with `java.util` Collections

| Dimension        | java.util | This Framework |
| ---------------- | --------- | -------------- |
| Transparency     | Low       | High           |
| Extensibility    | Limited   | High           |
| Learning Value   | Moderate  | Very High      |
| Internal Control | Hidden    | Explicit       |

---

## 10. Intended Audience

* Software engineers preparing for **system design or core Java interviews**
* Developers seeking **deep understanding of data structures**
* Architects evaluating clean low-level library design

---

## 11. Project Status & Delivery Roadmap

The project is being developed in incremental, architecture-first phases to ensure correctness, extensibility,
and long-term maintainability.

### Phase 1 – Core List Architecture (In Progress)
* ✅ List<T> abstraction – public contract definition
* ✅ AbstractList<T> – shared behavior, validation, and invariants
* 🟡 LinkedList<T> – concrete implementation (ongoing refinement & hardening)
---

## 12. Final Note

> **Good collections scale in size. Great collections scale in understanding.**

This project prioritizes **architectural correctness, clarity, and future readiness** over shortcuts.

---

# 🖥️ Modern Day Computing System — 16-bit Computer Architecture Project

## 📌 Overview

This project is a ground-up implementation of a **16-bit computer architecture**, built using fundamental digital logic principles. The system is designed by strictly following the **abstraction → implementation methodology**, a core concept used in computer engineering for decades.

Instead of relying on pre-built components, every module in this system is constructed from **elementary logic gates**, gradually abstracted into higher-level components such as registers, memory units, and ultimately a fully functional CPU.

---

## 🧠 Design Philosophy

### 🔹 Abstraction → Implementation Approach

The entire system is built using a layered design strategy:

1. **Truth Tables & K-Maps** → Define logical behavior
2. **Gate-Level Implementation** → Build circuits using basic gates
3. **Chip-Level Abstraction** → Combine circuits into reusable modules
4. **System Integration** → Assemble modules into a working computer

Each constructed component is reused as an abstraction (API-like building block), enabling scalable and modular design.

---

## ⚙️ Core Concepts & Optimizations

* **16-bit Data Bus Architecture**
  Each bit is processed independently using dedicated logic gates.

* **Efficient ALU Flag Design**

  * `zr (zero flag)` is derived using the property that only **zero produces zero in both OR and ADD operations consistently**.
  * `ng (negative flag)` is determined directly from the **most significant bit (MSB)**.

* **MUX Optimization**
  Leveraging binary properties to simplify select-line logic, reducing gate complexity.

---

## 🧩 Components Implemented

### 🔸 Memory System

* **16-bit Register** → Built using 16 D Flip-Flops

* **RAM8** → Basic memory unit

* **Full RAM System**

  * **Capacity:** 32 KB → Extended to **64 KB**
  * **Addressable Locations:** 16,384 (initial) → Expanded in final design
  * **Total Storage:**

    * 32 KB = 262,144 bits
    * 64 KB (final system)

* Supports **simultaneous addressing and sequential read/write operations**

---

### 🔸 CPU Architecture

Inspired by a **Harvard Architecture**, separating instruction and data memory.

#### Includes:

* 🧮 **ALU (Arithmetic Logic Unit)**
* 📦 **Data Register**
* 📍 **Address Register**
* 🔁 **Program Counter (PC)**
* 🔀 **Jump Logic (Loop / GOTO Control)**
* 🔌 **System Buses**
* 💾 **ROM (64 KB)**
* 🧠 **RAM (64 KB)**

---

## ⏱️ Execution Model

* The system operates on a **clock-driven mechanism**
* Instructions are executed on the **falling edge of each clock cycle**
* Ensures synchronized and predictable behavior across all components

---

## 🚀 Current Status

✅ Core architecture design complete
✅ Memory and CPU modules implemented
✅ Instruction execution functional
🔄 **Part 1: Nearly Complete**

---

## 📚 What This Project Demonstrates

* Building a computer from **first principles**
* Deep understanding of:

  * Digital logic design
  * Memory architecture
  * CPU organization
* Practical application of abstraction in hardware design

---

## 🔮 Future Scope

* Instruction set expansion
* Basic operating system support
* I/O integration (Display, Keyboard, etc.)
* Performance optimizations

---

## 👨‍💻 Author

**Dhruv Sud**

---

## 📄 License

This project is open-source and available for learning and educational purposes.

---

> *“From logic gates to a working CPU — this project is a journey through the fundamentals of computing.”*

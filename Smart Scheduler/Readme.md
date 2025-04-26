
# 📌 SmartScheduler – Java Task Prioritizer

SmartScheduler is a CLI-based task manager written in Java. It lets users input tasks with **deadlines** and **priorities**, and uses a **PriorityQueue** to sort and display them in the most efficient order.

### ✅ Features
- Task input with deadlines and priorities
- Displays task order using **heap-based** logic (min-heap on deadline, max-heap on priority)
- Saves top tasks to `TopTasks.txt`
- Automatically opens Notepad
- Sends **Windows Toast Notifications** using PowerShell

### 📂 Technologies Used
- Java 17
- Collections (PriorityQueue, Comparator)
- FileWriter
- ProcessBuilder (for Notepad + Notifications)

### 🛠 How to Run
```bash
javac SmartSchedulerWithNotification.java
java SmartSchedulerWithNotification
```

---

**Example Output:**
```
Top Priority Tasks:
1. Prepare Resume (Deadline: 1 days, Priority: 10)
2. Finish DSA assignment (Deadline: 2 days, Priority: 9)
3. Buy groceries (Deadline: 3 days, Priority: 5)
```

---

## 📬 Connect

- [LinkedIn](https://linkedin.com/in/aman-tripathi-898106243)

---
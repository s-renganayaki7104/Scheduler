# Schedule 

This project implements and simulates fundamental **CPU Scheduling Algorithms** in Java, designed for operating system process management.  

## Implemented Algorithms  
- **First In First Out (FIFO)** – Non-preemptive scheduling where the earliest arriving process executes first.  
- **Shortest Job First (SJF)** – Optimized using **priority queues** to minimize average waiting time.  
- **Round Robin (RR)** – Uses **quantum counters** to allocate fixed CPU time slices to each process, ensuring fairness and preventing starvation.  
- **Uni-Programming Scheduling** – A simple model where one process runs to completion before another starts.  

## Key Features  
- Simulation of **process scheduling policies** in Java.  
- Utilization of **priority queues** for SJF implementation.  
- **Quantum counters** for Round Robin to manage fair time sharing.  
- Prevention of **starvation** through fair scheduling strategies.  
- Console-based results showing **completion time, turnaround time, response time, and waiting time** for each process.  

## Technologies Used  
- **Java** (Core Language)  
- **Java Collections Framework** (`PriorityQueue`, `ArrayList`, etc.)  

## How to Run  
1. Clone the repository:  
   ```bash
   git clone https://github.com/s-renganayaki7104/Scheduler.git
   cd Scheduler
2. Compile the file:
   ```bash
   javac *.java
3. Run the program:
   ```bash
   java Main

## Example Output
The program prints scheduling results in the console, including process execution order and performance metrics.

## Algorithm Comparison  

| Algorithm            | Type            | Preemptive | Fairness | Starvation Risk              | Use Case Example              |
|----------------------|-----------------|------------|----------|-----------------------------|-------------------------------|
| **FIFO**             | Non-preemptive  | No         | Low      | Possible                    | Simple batch jobs             |
| **SJF**              | Non-preemptive  | No         | Medium   | High (long jobs may starve) | Minimizing average waiting time |
| **Round Robin (RR)** | Preemptive      | Yes        | High     | None (due to quantum)       | Time-sharing systems          |
| **Uni-programming**  | Non-preemptive  | No         | Very Low | None                        | Very simple OS models         |


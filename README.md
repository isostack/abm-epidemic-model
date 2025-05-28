
# Agent-Based COVID Policy Simulator

This repository contains an agent-based simulation model built in Java using the MASON framework. The model explores the effects of public health interventions — specifically mask usage, social distancing, and population density — on the spread of infectious diseases like COVID-19.

## 🧠 Overview

This project models the dynamics of an epidemic by simulating individuals (agents) with attributes such as infection status, mask-wearing, and social distancing behavior. It provides insights into how varying levels of policy compliance affect transmission rates, mortality, and infection peaks under different population densities.

## 📌 Research Question

**What percentage of people wearing masks and social distancing is required to prevent disease spread across varying population densities?**

## 🔬 Key Features

- Agent-based simulation using the MASON Java framework
- Adjustable parameters: mask compliance, distancing, infection radius, etc.
- Tracks key metrics: infection spread rate, mortality, peak infection percentage
- Multiple parameter sweeps (e.g. mask % vs density, infection radius vs SD radius)
- Visual display of simulation using custom GUI

## 📊 Parameters & Inputs

| Variable | Description |
|----------|-------------|
| `maskPercentage` | % of agents wearing masks |
| `socialDistancePercentage` | % of agents practicing social distancing |
| `populationDensity` | Agent density within simulation grid |
| `infectionRadius` | Distance over which an infected agent can transmit |
| `socialDistanceRadius` | Distance within which agents attempt to avoid others |
| `maskEffectiveness` | Reduction in transmission probability with masks |
| `deathRate` | Probability of death upon infection |
| `infectionPeriod` | Minimum steps before recovery or death |

## 🧪 Simulation Experiments

1. **Masking Levels** — Varying mask compliance from 10% to 80%
2. **Distancing Levels** — Varying distancing compliance under spatial constraints
3. **Infection Radius** — Analyzing effectiveness of distancing at different ranges
4. **Social Distance Radius** — Evaluating interaction radius vs. infection spread

## 📈 Results Highlights

- Infection reduction is **nonlinear** — 50% to 80% mask usage sees diminishing returns.
- Distancing has **decreasing effectiveness** at high population density due to crowding.
- Neither infection radius nor social distance radius had significant effect under certain density constraints, indicating **physical limits of policy impact**.

## 🛠️ Installation

```bash
git clone https://github.com/isostack/abm-epidemic-model.git
cd abm-pandemic-policy-model
# Open in an IDE (e.g., IntelliJ, Eclipse) with MASON dependency configured
```

## 🧱 Tech Stack

- Java 11+
- [MASON Simulation Library](https://cs.gmu.edu/~eclab/projects/mason/)
- Custom parameter sweeping and visualization

## 📁 File Structure

- `Agent.java`: Defines agent behavior (movement, infection, distancing)
- `Environment.java`: Simulation setup, infection logic, agent spawning
- `AgentsGUI.java`: Visual GUI for displaying agent states
- `Experimenter.java`: Handles parameter sweeps and statistical output
- `InfectionState.java`: Enum for agent health status


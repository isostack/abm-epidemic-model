package khModel;

import sim.util.Bag;
import sweep.SimStateSweep;

public class Environment extends SimStateSweep {
    public int gridWidth = 100; // Grid width
    public int gridHeight = 100; // Grid height
    public double populationDensity = 0.1; // Population to Space Ratio
    public double maskEffectiveness = 0.5; // Effectiveness of masks in reducing infection probability
    public int infectionRadius = 5; // Radius within which infection spreads
    public int socialDistanceRadius = 5; // Radius for social distancing
    public double deathRate = 0.01; // Probability of death for infected agents
    public double recoveryRate = 0.01; //Probability of recovering for disease
    public int infectionPeriod = 10; //Minimum infection period
    
    public double baseInfectionProb = 0.1; //Base Chance of infection
    public double baseInfectionFalloff = 1; // The rate that infection chance decreases by distance
    public double startingInfection = 0.1; // Percentage of Agents who begin simulation infected

    public double maskPercentage = 0; // Percentage of Agents who wear masks
    public double socialDistancePercentage = 0.5; // Percentage of Agents who social distance

    public Environment(long seed) {
        super(seed);
    }

    public Environment(long seed, Class observer) {
        super(seed, observer);
    }

    @Override
    public void start() {
        super.start();
        makeSpace(gridWidth, gridHeight);
        makeAgents();
    }

    public void makeAgents() {
        if (populationDensity > 1) {
            System.out.println("Too many agents for the space!");
            return;
        }
        
        int n = (int)(gridWidth * gridHeight * populationDensity);
        int numOfMasks = (int)(n * maskPercentage);
        int numOfSocDis = (int)(n * socialDistancePercentage);

        int infectionHash = n;
        if (startingInfection != 0) {
        	infectionHash = (int)Math.floor(1/startingInfection);
        }
        
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(gridWidth);
            int y = random.nextInt(gridHeight);
            int xdir = random.nextInt(3) - 1; // Random direction: -1, 0, or 1
            int ydir = random.nextInt(3) - 1;
            
            Bag cell = sparseSpace.getObjectsAtLocation(x, y);
            while (cell != null && cell.numObjs > 0) {
                x = random.nextInt(gridWidth);
                y = random.nextInt(gridHeight);
                cell = sparseSpace.getObjectsAtLocation(x, y);
            }

            boolean maskWearing = false;
            if (numOfMasks > 0) {
            	maskWearing = true;
            	numOfMasks--;
            }
            
            boolean socialDistancing = false;
            if (numOfSocDis > 0) {
            	socialDistancing = true;
            	numOfSocDis--;
            }
            
            InfectionState status = InfectionState.SUSCEPTIBLE;
            if (i % infectionHash == 0) {
            	status = InfectionState.INFECTED;
            }

            Agent agent = new Agent(x, y, xdir, ydir, maskWearing, socialDistancing, status);

			agent.event = schedule.scheduleRepeating(agent);
			
			updateColor(agent);
			
            sparseSpace.setObjectLocation(agent, x, y);
        }
    }
    
    public void updateColor(Agent agent) {
    	float r = 0, g = 0, b = 0;
    	if(agent.status == InfectionState.INFECTED) {
    		r = 1;
    	}
    	else if(agent.status == InfectionState.SUSCEPTIBLE) {
    		g = 1;
    	}
    	if(agent.socialDistancing && agent.status != InfectionState.RECOVERED) {
    		b = 1;
    	}
    	
    	gui.setOvalPortrayal2DColor(agent, r, g, b, (float)1, !agent.maskWearing);
    }



    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public double getMaskEffectiveness() {
        return maskEffectiveness;
    }

    public void setMaskEffectiveness(double maskEffectiveness) {
        this.maskEffectiveness = maskEffectiveness;
    }

    public int getInfectionRadius() {
        return infectionRadius;
    }

    public void setInfectionRadius(int infectionRadius) {
        this.infectionRadius = infectionRadius;
    }

    public int getSocialDistanceRadius() {
        return socialDistanceRadius;
    }

    public void setSocialDistanceRadius(int socialDistanceRadius) {
        this.socialDistanceRadius = socialDistanceRadius;
    }

    public double getDeathRate() {
        return deathRate;
    }

    public void setDeathRate(double deathRate) {
        this.deathRate = deathRate;
    }

    public double getMaskPercentage() {
        return maskPercentage;
    }

    public void setMaskPercentage(double maskPercentage) {
        this.maskPercentage = maskPercentage;
    }

    public double getSocialDistancePercentage() {
        return socialDistancePercentage;
    }

    public void setSocialDistancePercentage(double socialDistancePercentage) {
        this.socialDistancePercentage = socialDistancePercentage;
    }

	public double getPopulationDensity() {
		return populationDensity;
	}

	public void setPopulationDensity(double populationDensity) {
		this.populationDensity = populationDensity;
	}

	public double getRecoveryRate() {
		return recoveryRate;
	}

	public void setRecoveryRate(double recoveryRate) {
		this.recoveryRate = recoveryRate;
	}

	public int getInfectionPeriod() {
		return infectionPeriod;
	}

	public void setInfectionPeriod(int infectionPeriod) {
		this.infectionPeriod = infectionPeriod;
	}

	public double getBaseInfectionProb() {
		return baseInfectionProb;
	}

	public void setBaseInfectionProb(double baseInfectionProb) {
		this.baseInfectionProb = baseInfectionProb;
	}

	public double getBaseInfectionFalloff() {
		return baseInfectionFalloff;
	}

	public void setBaseInfectionFalloff(double baseInfectionFalloff) {
		this.baseInfectionFalloff = baseInfectionFalloff;
	}

	public double getStartingInfection() {
		return startingInfection;
	}

	public void setStartingInfection(double startingInfection) {
		this.startingInfection = startingInfection;
	}
}

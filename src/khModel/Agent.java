package khModel;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;

public class Agent implements Steppable {
    int x, y; // Position on grid
    int dirx, diry; // Agent movement direction
    boolean maskWearing; // Whether the agent is wearing a mask
    boolean socialDistancing; // Whether the agent is practicing social distancing
    InfectionState status = InfectionState.SUSCEPTIBLE; //The current infection status 
	public Stoppable event; //allows to remove an agent from the simulation.
	int infectedTime = 0; //Time infected
    
    public Agent(int x, int y, int dirx, int diry, boolean maskWearing, boolean socialDistancing, InfectionState status) {
        this.x = x;
        this.y = y;
        this.dirx = dirx;
        this.diry = diry;
        this.maskWearing = maskWearing;
        this.socialDistancing = socialDistancing;
        this.status = status;
    }

    @Override
    public void step(SimState state) {
        Environment environment = (Environment) state;
        
        move(environment);
        if(status == InfectionState.INFECTED) {
        	infectedTime++;
        	updateInfection(environment);
        	findInfection(environment);
        }
    }
    
    public void placeAgent(Environment state) {
         int tempx = bx(x + dirx, state);//tempx and tempy location
         int tempy = by(y + diry, state);
         Bag b = state.sparseSpace.getObjectsAtLocation(tempx, tempy);
         if(b == null){//if empty, agent moves to new location
               x = tempx;
               y = tempy;
               state.sparseSpace.setObjectLocation(this, x, y);
         }
   }
    
	/**
	 * Agents move randomly to a new location for either one agent per cell or possibly
	 * multiple agents per cell.
	 * @param state
	 */
	public void move(Environment state) {
		if (this.socialDistancing) {
			Agent nearest = findNearest(state);
			if(nearest != null) {
				if(nearest.x - this.x > 0) {
					this.dirx = -1;
				}
				else if(nearest.x - this.x < 0) {
					this.dirx = 1;
				}
				else {
					this.dirx = 0;
				}
				if(nearest.y - this.y > 0) {
					this.diry = -1;
				}
				else if(nearest.y - this.y < 0) {
					this.diry = 1;
				}
				else {
					this.diry = 0;
				}
			}
			else {
				this.dirx = 0;
				this.diry = 0;
			}
		}
		else {
			this.dirx = state.random.nextInt(3)-1;
			this.diry = state.random.nextInt(3)-1;
		}
		placeAgent(state);
	}
	
	public int bx(int tempx, Environment environment) {
		int newx = tempx;
		if(newx < 0) {
			newx = 0;
		}
		else if (newx > environment.gridWidth - 1) {
			newx = environment.gridWidth - 1;
		}
		return newx;
	}
	
	public int by(int tempy, Environment environment) {
		int newy = tempy;
		if(newy < 0) {
			newy = 0;
		}
		else if (newy > environment.gridHeight - 1) {
			newy = environment.gridHeight - 1;
		}
		return newy;
	}
	
	public Agent findNearest(Environment environment) {
		Bag neighbors = environment.sparseSpace.getMooreNeighbors(x, y, environment.socialDistanceRadius, environment.sparseSpace.BOUNDED, false);
		Agent nearest = null;
		double distance = Double.MAX_VALUE;
		for(int i=0;i<neighbors.numObjs;i++) {
			Agent a = (Agent)neighbors.objs[i];
			double d = Math.sqrt((Math.pow(a.x - this.x,2)) + (Math.pow(a.y - this.y,2)));
			if (d < distance) {
				nearest = a;
				distance = d;
			}
		}
		return nearest;
	}
	
    public void findInfection(Environment environment) {
		Bag neighbors = environment.sparseSpace.getMooreNeighbors(x, y, environment.socialDistanceRadius, environment.sparseSpace.BOUNDED, false);
		for(int i=0;i<neighbors.numObjs;i++) {
			Agent a = (Agent)neighbors.objs[i];
			double d = Math.sqrt((Math.pow(a.x - this.x,2)) + (Math.pow(a.y - this.y,2)));
			double infectChance = environment.baseInfectionProb * Math.pow(Math.E, -environment.baseInfectionFalloff * d);
			if(this.maskWearing) {
				infectChance = infectChance * (1 - environment.maskEffectiveness);
			}
			if(environment.random.nextBoolean(infectChance)) {
				a.status = InfectionState.INFECTED;
				environment.updateColor(a);
			}
		}
    }

    public void updateInfection(Environment environment) {
    	if (environment.random.nextBoolean(environment.deathRate)) {
    		remove(environment);
    	}
    	else if(infectedTime > environment.infectionPeriod && environment.random.nextBoolean(environment.recoveryRate)){
    		infectedTime = 0;
    		status = InfectionState.RECOVERED;
    		environment.updateColor(this);
    	}
    }
    
    public void remove(Environment state) {
		state.sparseSpace.remove(this);
		event.stop();
	}
}

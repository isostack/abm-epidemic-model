package khModel;

import java.awt.Color;
import spaces.Spaces;
import sweep.GUIStateSweep;
import sweep.SimStateSweep;

public class AgentsGUI extends GUIStateSweep {

    public AgentsGUI(SimStateSweep state, int gridWidth, int gridHeight, Color backdrop, Color agentDefaultColor,
                     boolean agentPortrayal) {
        super(state, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
    }

    public static void main(String[] args) {
		AgentsGUI.initialize(Environment.class,null, AgentsGUI.class, 600, 600, Color.WHITE, Color.BLUE, false, Spaces.SPARSE);
    }
}

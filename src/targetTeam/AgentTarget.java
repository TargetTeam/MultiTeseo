/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package targetTeam;

import java.util.HashMap;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;
import unalcol.agents.simulate.util.Language;
import unalcol.agents.simulate.util.SimpleLanguage;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author Camilo
 */
public class AgentTarget implements AgentProgram {


    protected Language language;
    protected Vector<String> cmd = new Vector<String>();
    
    HashMap<String, Node> listNodes;
//    public SparseMatrix matrix;
    public int x;
    public int y;
    public Node lastNode;
    public int dir;
    
    public void setLanguage(  SimpleLanguage _language ){
        language = _language;
      }
     public AgentTarget(){
        Data data = new Data();
        Node start = new Node(0, 0, data);
        listNodes = new HashMap<>();
        listNodes.put("0,0", start); 
     }
     
    public void init(){
        cmd.clear();
      }

    /**
     * execute
     *
     * @param perception Perception
     * @return Action[]
     */
    @Override
    public Action compute(Percept p) {
        if (cmd.size() == 0) {

            boolean PF = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("front"))));
            boolean PD = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("right"))));
            boolean PA = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("back"))));
            boolean PI = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("left"))));
            
            boolean AF = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("afront"))));
            boolean AD = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("aright"))));
            boolean AA = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("aback"))));
            boolean AI = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("aleft"))));
            
            boolean MT = ((Boolean) p.getAttribute(language.getPercept(language.getPerceptIndex("exit"))));
            
            int d = accion(PF, PD, PA, PI, MT, AF, AD, AA, AI);
            if (0 <= d && d < 4) {
                for (int i = 1; i <= d; i++) {
                    cmd.add(language.getAction(3)); //rotate
                }
                cmd.add(language.getAction(2)); // advance
            } else {
                cmd.add(language.getAction(0)); // die
            }
        }
        String x = cmd.get(0);
        cmd.remove(0);
        return new Action(x);
    }

    /**
     * goalAchieved
     *
     * @param perception Perception
     * @return boolean
     */
    public boolean goalAchieved(Percept p) {
        return (((Boolean) p.getAttribute(language.getPercept(4))).booleanValue());
    }

    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT,
                      boolean AF, boolean AD, boolean AA, boolean AI ) {
        if (MT)return 10;
        int pathcount = 0;
        if (!PF)pathcount++;
        if (!PD)pathcount++;
        if (!PI)pathcount++;
        if (pathcount == 0) {
            dir = (dir + 2) % 4;
            if(dir==0)y--;
            if(dir==1)x++;
            if(dir==2)y++;
            if(dir==3)x--;
            return 2;
        }else if (pathcount == 1) {
            if (!PD && !AD) {
                dir = (dir + 1) % 4;
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 1;
            } else if (!PI && !AI) {
                dir = (dir + 3) % 4;
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 3;
            }else if (!PF && !AF) {
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 0;
            }
        }else if (pathcount >= 2) {
            Node actualNode = listNodes.get(x+","+y);
            Data actualData;
            if (actualNode == null) {
                Data newData = new Data();
                newData.changePaths((dir + 2) % 4, Data.VISITED);
                if (PF)newData.changePaths(dir % 4, Data.CLOSED);
                if (PD)newData.changePaths((dir + 1) % 4, Data.CLOSED);
                if (PI)newData.changePaths((dir + 3) % 4, Data.CLOSED);
                if (AF)newData.changePaths(dir % 4, Data.AGENT);
                if (AD)newData.changePaths((dir + 1) % 4, Data.AGENT);
                if (AI)newData.changePaths((dir + 3) % 4, Data.AGENT);
                newData.changePaths((dir + 2) % 4, Data.VISITED);
                if (AA)newData.changePaths((dir + 2) % 4, Data.AGENT);
                actualNode = new Node(x,y,newData);
                listNodes.put(x+","+y, actualNode);
            } else {
                actualData = (Data) actualNode.getData();                
                if(PF)actualData.changePaths(dir % 4, Data.CLOSED);
                if(PD)actualData.changePaths((dir + 1) % 4, Data.CLOSED);
                if(PI)actualData.changePaths((dir + 3) % 4, Data.CLOSED);
                if(PA)actualData.changePaths((dir + 3) % 4, Data.CLOSED);
                if (actualData.getPaths(dir%4)!=Data.CLOSED && AF)actualData.changePaths(dir % 4, Data.AGENT);
                if (actualData.getPaths((dir+1)%4)!=Data.CLOSED && AD)actualData.changePaths((dir + 1) % 4, Data.AGENT);
                if (actualData.getPaths((dir+3)%4)!=Data.CLOSED && AI)actualData.changePaths((dir + 3) % 4, Data.AGENT);
                actualData.changePaths((dir + 2) % 4, Data.VISITED);
                if (AA)actualData.changePaths((dir + 2) % 4, Data.AGENT);
            }
            actualData = (Data) actualNode.getData();
            if (actualData.getPaths((dir + 1) % 4) == Data.OPEN) {
                actualData.changePaths((dir +1) % 4, Data.VISITED);
                dir = (dir + 1) % 4;
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 1;
            }
            if (actualData.getPaths((dir + 3) % 4) == Data.OPEN) {
                actualData.changePaths((dir + 3)% 4, Data.VISITED);
                dir = (dir + 3) % 4;
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 3;
            }
            if (actualData.getPaths(dir % 4) == Data.OPEN) {
                actualData.changePaths(dir % 4, Data.VISITED);
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 0;
            }
            if (actualData.getPaths((dir + 1) % 4) == Data.VISITED) {
                actualData.changePaths((dir +1)% 4, Data.CLOSED);
                dir = (dir + 1) % 4;
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 1;
            }
            if (actualData.getPaths((dir + 3) % 4) == Data.VISITED) {
                actualData.changePaths((dir+3) % 4, Data.CLOSED);
                dir = (dir + 3) % 4;
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 3;
            }
            if (actualData.getPaths(dir % 4) == Data.VISITED) {
                actualData.changePaths(dir % 4, Data.CLOSED);
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 0;
            }
            
            if (actualData.getPaths((dir + 1) % 4) == Data.AGENT) {
                actualData.changePaths((dir +1)% 4, Data.VISITED);
                dir = (dir + 1) % 4;
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 1;
            }
            if (actualData.getPaths((dir + 3) % 4) == Data.AGENT) {
                actualData.changePaths((dir+3) % 4, Data.VISITED);
                dir = (dir + 3) % 4;
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 3;
            }
            if (actualData.getPaths(dir % 4) == Data.AGENT) {
                actualData.changePaths(dir % 4, Data.VISITED);
                if(dir==0)y--;
                if(dir==1)x++;
                if(dir==2)y++;
                if(dir==3)x--;
                return 0;
            }
            listNodes.remove(x+","+y);
            
            dir = (dir + 2) % 4;
            if(dir==0)y--;
            if(dir==1)x++;
            if(dir==2)y++;
            if(dir==3)x--;
            actualNode = listNodes.get(x+","+y);
            if(actualNode!=null){
                actualData = (Data) actualNode.getData();
                actualData.changePaths((dir+2)%4, Data.CLOSED);
            }
            return 2;
        }
        return 0;
    }
    
}
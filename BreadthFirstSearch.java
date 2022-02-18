//Imports java utility packages to access all the required methods and classes in the package.
import java.util.*;


/*
   -This class performes the breadth first search on the graph when called by ContactTracer class.
   -It consists a constructor method to initialize the graph and nodes method to return the required
      nodes after breadth first search.
   -It uses queue, arraylist, hashmap data structure with conditions and loops to carryout the algorithm.
*/
public class BreadthFirstSearch
{
   
   //Declaration of graph to be used in the class.
   private Graph graph;
   
   
   /*
      -This constructor initializes the graph after getting called from the ContactTracer class
         with graph as argument.
      -@param graph the graph to be traversed in breadth first search manner.
   */
   public BreadthFirstSearch(Graph graph)
   {
      this.graph = graph;
   }
   
   
   /*
      -This method returns the map containing each vertex with their parent vertex as value to be 
         processes further in ContactTracer class for shortest path.
      -Queue data structure is used to add and remove the vertex throughout the breadth first search
         travesal.
      -visited arrayList is used to keep track of the nodes that has already been checked.
      -shortestPath hashmap is used to map each vertex to their parent vertex.
      -risk arraylist is used to keep track of what is visited and removed in the particular level
         of the graph and prevRisk arraylist keeps track of the vertices at the certain level.
      -risk arraylist decreases everytime the iteration of adjacent vertices occurs.If risk doesn't contain
         all the prevRisk elements, it indicates the completion of one level and we proceed and copy the
         risk elements to prevRisk array.
      -If there is no elements of prevRisk in risk then queue size is update and risk is copied to prevrisk.
      -queueSize indicates the size of queue at certain level and it decreases after each iterations and when 
         it turns 0 the level increases too.
      -if the level is equal to end then it returns the mapping up to that level, otherwise it go on until the 
         end of the vertices and map each vertices.
      @param target the individual who is the subject to determine the path from infected person.
      @param end the level up to which we want to find the path
      @return hashmap containing vertices from the target as key and their parent vertices as values.
      
   */
   public HashMap<String,String> nodes(String target,int end)
   {
      Queue <String> q1 = new LinkedList<String>();
      q1.add(target);
      ArrayList<String> visited = new ArrayList<>();
      visited.add(target);
      
      HashMap<String, String> shortestPath = new HashMap<String, String>();
      
      ArrayList<String> risk = new ArrayList<>();
      risk.add(target);
      ArrayList <String> prevRisk = new ArrayList<>(risk);
      
      int level=0;
      int queueSize= q1.size();
      boolean updateQueueSize= false;
      
      while(!q1.isEmpty())
      {
         for(int i = 0; i<prevRisk.size();i++)
         {
            if((risk.contains(prevRisk.get(i))))
            {
               updateQueueSize=false;
               break;
            }
            else
            {
               updateQueueSize=true;
            }
         }
         
         if(updateQueueSize)
         {
            queueSize=q1.size();
            prevRisk= new ArrayList<>(risk);
         }
         
         String topNode = q1.peek();
         q1.remove();
         
         Iterator <String> iter = graph.getAdjacent(topNode);
         queueSize--;
         risk.remove(0);
         
         if (queueSize<=0) level++;
         
         while(iter.hasNext())
         {
            String next = iter.next();
            if(!visited.contains(next))
            {
               q1.add(next);
               visited.add(next);
               shortestPath.put(next,topNode);
               risk.add(next);
            }
         }
         
         if (level>=end) 
            return shortestPath;
      }
      return shortestPath;
   }
}
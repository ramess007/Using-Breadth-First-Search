import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;

public class ContactTracer
{
    LinkedList<String> queue = new LinkedList<String>(); 
    Set <String> infectedSet = new HashSet <String> ();
    
    private boolean contact;
    private int V;
    Graph graph = new Graph();
    Map<Integer, ArrayList<String>> oneLine = new HashMap<Integer, ArrayList<String>>();
    private ArrayList<String> infectedPeople = new ArrayList<String> ();
    Map<Integer, String> verticesMap= new HashMap<Integer, String>();
    // LinkedList <Integer> adj = new LinkedList <Integer> ();

        public ContactTracer(String filename) // throws FileNotFoundException
    {
        
        try
        {
           File inFile = new File(filename);
           Scanner in = new Scanner(inFile);
           int k = 1;
           

           while(in.hasNextLine())
           {
               Scanner line = new Scanner(in.nextLine());
               ArrayList<String> infected = new ArrayList<String>();
               while(line.hasNext())
               {
                
                // ArrayList<String> infected = new ArrayList<String>();
                line.useDelimiter("/");

                // String temp = " ";
                infected.add(line.next());
               
                

               }
               oneLine.put(k, infected);
               k++;
               
           }

           
           
           in.close();
            
        connectingVertices();
        V = graph.vertices();

        }

        catch(FileNotFoundException e)
      {
         System.out.println("File not found");
         
      }
    }

    private  void connectingVertices()
    {
        Set<Integer> keySet1 = oneLine.keySet(); 
        for(int key: keySet1)
        {
            ArrayList<String> list = new ArrayList<String>();
            list = oneLine.get(key);
            int k = 0;
            for(int i = 0; i < list.size(); i++)
            {
                   String temp = list.get(i);
                    // graph.addVertex(temp);
                    if(graph.addVertex(temp))
                    {
                        verticesMap.put(k, temp);
                        // System.out.println(verticesMap.get(k));
                        k++;
                    }

                    
            }

            for(int i = 0; i < list.size(); i++)
            {
                String temp = list.get(i);
                    for(int j = i; j < list.size(); j++)
                    {
                        if(!list.get(i).equals(list.get(j)))
                        graph.addEdge(temp, list.get(j));
                        // System.out.println(temp + "------" + list.get(j));
                    }

            }

        }
        System.out.println(graph.edges());

    }

    public String infectionConfirmed(String person)
    {
       
        String names = " ";
        int index = 0;
        int number = 0;
        Set<Integer> keySet1 = oneLine.keySet(); 
        ArrayList<String> list = new ArrayList<String>();
        infectedPeople.add(person);
        for(int key: keySet1)
        {
            
            if(oneLine.get(key).contains(person))
            {
                list = oneLine.get(key);
                list.remove(person);

                for(int i = 0; i < list.size(); i++)
                {
                    number ++;
                    names += list.get(i) + " | " ;
                }
            }

        }
        return number + " CONTACTS: " + names.substring(0, names.length()-2);

        
    }




    String path;
    public boolean isReachable(String s, String d) 
{ 
    // Mark all the vertices as not visited(By default 
    // set as false) 
    ArrayList<String> visited = new ArrayList<String>();
    
   
    // visited.add(s);
    queue.add(s);
    
    infectedSet.add(s);

       
        // Dequeue a vertex from queue and print it 
        s = queue.poll(); 
        // result += s + " | ";
        // Get all adjacent vertices of the dequeued vertex s 
        // If a adjacent has not been visited, then mark it 
        // visited and enqueue it 
      
        Iterator<String> i = graph.getAdjacent(s); 
        while (i.hasNext()) 
        { 
            String n = i.next(); 

            // if (n.equals(d))
            // {
            //     return true;
               
            // }
            if (infectedPeople.contains(n)) 
            { 
                // visited.add(n);
                queue.add(n);
                infectedSet.add(n);
                path = d + " | " + n;
                return true;
               
         } 
        while(!queue.isEmpty())
        {
            String name = queue.remove();
            return isReachable(name, name + " | " + d);
        }
    } 


    return false; 
} 

    public String infectionPath(String target)
    {
        String d = " ";
        String result = " ";
        Set<Integer> keySet1 = oneLine.keySet();
        
         
            for(int j = 0; j < infectedPeople.size(); j++)
            {
                String temp = infectedPeople.get(j);

                if(isReachable(target, temp))
                {
                    d = temp;
                    
                }

                else
                {
                    return "No known infection path to " + target + ".";
                }
            }
        
    
            if(isReachable(target, d))
        {
            
            Iterator<String> itr = infectedSet.iterator();
             // traversing over HashSet 
             while(itr.hasNext())
             {
                  result += itr.next() + " | "; 

            }

           
            
        }

        return "[" + result.substring(0, result.length() - 2) + "]";

     
        
   }

    

public int infectionPathLength(String target)
{
   infectionPath(target);

   return infectedSet.size() - 1;
}

}


//Imports java utility and io packages to access all the required methods and classes in the package
import java.util.*;
import java.io.*;


/*
   -This class implements depth first search algorithm to trace the people infected with COVID-19
      and find the risk of spreading to other people in contact.
   -It consists of several public methods to confirm the infection, to find the number and people in 
      infection path and the infection risk
   -It also consists of helper private methods to for calling the BreadthFirstSearch class, for constructing
      appropriate path and minimum infection path.
   -ArrayList data structure is used extensively, with a map and conditional and loop statements.
*/
public class ContactTracer
{
   //Declaration of graph and arraylist to be used in the class.
   private Graph contactGraph = new Graph();
   private ArrayList <String> infected = new ArrayList<>();
   
   
   /*
      -This constructor accepts the data file of persons in contact as argument.
      -It uses scanner method along with delimiter making the use of while loop to get the contacts in each line seperated by "|".
      -All the contacts in the file are added to the graph using addVertex method from graph class.
      -All the contacts in the same line are added as the edges in the graph using addEdges method from graph class
         using for loop to go though each of them.
      -try-catch block is used to check exception.
      @param filename names with people in contact with each other.
   */
   public ContactTracer(String filename)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         while(in.hasNextLine())
         {
            Scanner readLine = new Scanner(in.nextLine());
            readLine.useDelimiter("/");
            ArrayList <String> oneLineNodes = new ArrayList<>();
            while(readLine.hasNext())
            {
               String vertex = readLine.next();
               oneLineNodes.add(vertex);
               contactGraph.addVertex(vertex);
               for(int i =0; i<oneLineNodes.size();i++)
               {
                  if(!oneLineNodes.get(i).equals(vertex)) 
                  {
                     contactGraph.addEdge(vertex,oneLineNodes.get(i));
                  }
               }
            }
         }
         System.out.println(contactGraph);
      }
      
      catch(Exception e)
      {
         System.out.println("Some Exception Occured");
      }
   }
   
   
   /*
      -This method is used to return a string containing the number and names of individual
         who have been in immediate contact with the person given as argumetn, who has been
         confirmed as being infected.
      -It uses iterator to go through all the adjacent vertices as obtained from getAdjacent
         method from Graph class.
      -while loop is used to go through the adjacents and prepare the string containing contacts for output.
      @param person the person who has been confirmed as infected
      @return string containing number and names of people who have been in immediate contact
   */
   public String infectionConfirmed(String person)
   {
      infected.add(person);
      String output = "";
      Iterator iter = contactGraph.getAdjacent(person);
      while(iter.hasNext())
      {
         output+=iter.next() + " | ";
      }
      output+="]";
      output= (contactGraph.degree(person)+ " CONTACTS: " +output.replace(" | ]",""));
      return output.replace("]","");
   }
   
   
   /*
      -This is the private method that controls the functionality of the class to prepare the appropriate path.
      -It create the new BreadthFirstSearch class and calls nodes class to find nodes using bfs.
      -It calls pathConstructor method to form appropriate path and return it.
      @param target the individual who is the subject to determine the path from infected person.
      @param infectedPerson the person who is infected with COVID_19
      @param end the level up to which breadth first search can go maximum
      @return arraylist of path of the graph from infected to target.
   */
   private ArrayList<String> breadthFirstSearch(String target, String infectedPerson, int end)
   {
      BreadthFirstSearch bfs = new BreadthFirstSearch(contactGraph);
      HashMap <String,String> nodes = bfs.nodes(target,end);
      ArrayList <String> path = pathConstructor(target, infectedPerson, nodes);
      return path;
   }
   
   
   
   /*
      -This is the private method used to create appropriate path from infected to target in the graph.
      -It creates shortestPath arrayList to fill with the people in the path.
      -If the infected person is not in contact with anyother people as given by key of map nodes,
         they it returns null.
      -nodes (map) is used to find the persons in contacts with each other parent vertex directly and 
         while loop is used until the target person is found. This way the contacts are added to shortest path.
      @param target the individual who is the subject to determine the path from infected person.
      @param infectedPerson the person who is infected with COVID_19
      @param nodes map containing the person in contact vertex mapped to their parent vertex.
      @return arraylist of shortest path from target to infected individual
   */
   private ArrayList<String> pathConstructor(String target, String infectedPerson, Map<String,String> nodes)
   {
      ArrayList<String> shortestPath = new ArrayList<>();
      
      String key = infectedPerson;
      
      if(!(nodes.containsKey(infectedPerson)))
      {
         return null;
      }
      while(!(shortestPath.contains(target)))
      {
         shortestPath.add(key);
         key = nodes.get(key);
      }
      return shortestPath;
   }
   
   
   /*
      -This is the private method used to create the minimum path from target to infected person
         in case there are more than one infected individual and paths.
      -It uses for loop to go through different paths except the null ones and find the minimum.
      -@param paths arraylist of the arraylist of paths
      -return minset arraylist of minimum path
   */
   private ArrayList<String> minPath(ArrayList<ArrayList<String>> paths)
   {
      int min;
      if(!(paths.get(0)==null))
      {
         min =paths.get(0).size();
      }
      else
      {
         min = -1;
      }
      
      ArrayList<String> minSet= paths.get(0);
      for(int i= 1;i<paths.size();i++)
      {
         if(!(paths.get(i)==null)  && (paths.get(i).size()<min || (min==-1)))
         {
            minSet = paths.get(i);
            min= paths.get(i).size();
         }
      }
      return minSet;
   }
   
   
   /*
      -This method returns the number of edges in the shortest path from the target argument
         to an individual confirmed as infected.It returns -1 if no path existed.
      -It uses infectionPaths which is an arraylist of arraylists of path to infected individuals 
         from the target.
      -It uses arraylist path to store the ararylist of path after getting minimum path from
         the minPath method.
      -For loop is used to go through each infected individuals, getting thier path, and storing
         the arraylists to infectionPaths.
       @param target the individual who is the subject to determine the path from infected person.
       @return number of edges in the shortest path from the target to infected.
   */
   public int infectionPathLength(String target)
   {
      ArrayList <String> path = new ArrayList<>();
      ArrayList <ArrayList <String>> infectionPaths = new ArrayList<>();
      for (int i =0; i<infected.size();i++)
      {
         if(target.equals(infected.get(i)))
         {
            return 0;
         }
         path = breadthFirstSearch(target,infected.get(i),contactGraph.vertices());
         infectionPaths.add(path);
      }
      path = minPath(infectionPaths);
      if((path==null))
      {
         return -1;
      }
      
      return path.size()-1;      
   }
   
   
   /*
      -This method returns the names of the individuals in the shortest path from the target to
         an individual confirmed as being infected.
      -It uses infectionPaths which is an arraylist of arraylists of path to infected individuals 
         from the target.
      -It uses arraylist path to store the ararylist of path after getting minimum path from
         the minPath method.
      -For loop is used to go through each infected individuals, getting thier path, and storing
         the arraylists to infectionPaths and another for loop is used to add all the vertex of the
         minimum path for the output.
       @param target the individual who is the subject to determine the path from infected person.
       @return names of the individuals in the shortest path from  target to infected person.
   */
   public String infectionPath(String target)
   {
      ArrayList <String> path = new ArrayList<>();
      ArrayList <ArrayList <String>> infectionPaths = new ArrayList<>();
      for (int i =0; i<infected.size();i++)
      {
         if(target.equals(infected.get(i)))
         {
            return "["+target+"]";
         }
         path = breadthFirstSearch(target,infected.get(i),contactGraph.vertices());
         infectionPaths.add(path);
      }
      path = minPath(infectionPaths);
      if(path==null)
      {
         return "[]";
      }
      String output="[";
      for(String vertex:path)
      {
         output+= vertex + " | ";
      }
      output+="]";
      return output.replace(" | ]","]");
   }
   
   
   
   /*
      -This method returns the string representing the infection risk for an individual as measured
         by the number and names of infected individuals seperated no more than "distance" edges 
         from the target.
      -It uses infectionPaths which is an arraylist of arraylists of path to infected individuals 
         from the target.
      -It uses arraylist path to store the ararylist of path after getting from breadthFirstSearch method.
      -For loop is used to go through each infected individuals, getting thier path, and storing
         the arraylists to infectionPaths.
      -Another for loop is used to get only the first elements of the paths, as it is the name of 
         infected individual on that path. Output variable is used to add the infected ones.
       @param target the individual who is the subject to determine the path from infected person.
       @param distance the number representing the no of edges from the target
       @return string representing the number and names of infected individuals in "distance" edges.
   */
   public String infectionRisk(String target, int distance)
   {
      ArrayList <String> path = new ArrayList<>();
      ArrayList <ArrayList <String>> infectionPaths = new ArrayList<>();
      for (int i =0; i<infected.size();i++)
      {
         path = breadthFirstSearch(target,infected.get(i),distance);
         if(!(path==null))
         {
            infectionPaths.add(path);
         }
         
      }
      String output=infectionPaths.size() +": [";
      for(ArrayList<String> vertex:infectionPaths)
      {
         output+= vertex.get(0) + " | ";
      }
      output+="]";
      return output.replace(" | ]","]");
   }
   
}
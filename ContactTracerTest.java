public class ContactTracerTest
 //
 //   Sample program to test the basic functionality of the ContactTracer class
{
   public static void main (String[] args)
   {
   
   
   
      ContactTracer covid19 = new ContactTracer("10movies.txt");
      String [] infected = {"Evans, John", "Reed, Albert", "Close, Glenn",
         "Ford, Harrison", "Bacon, Kevin"};
   
      for (String name: infected)
         covid19.infectionConfirmed(name);  
      String out1 = covid19.infectionPath("Doyle, Richard");
   // System.out.println(covid19.infectionRisk("Doyle, Richard",1));
      String out2 = covid19.infectionPath("Saint Duval, Malila");
   // System.out.println(covid19.infectionRisk("Saint Duval, Malila",1));
      String bl = "[\\s]*"; // any number of blanks
   
      System.out.println(out1 + "\n"+ out2);
   
   
   
   
   //                ContactTracer covid19 = new ContactTracer("10movies.txt");
   
   
           //     ContactTracer covid19 = new ContactTracer("test.txt");
   //                         System.out.println("\n" + covid19.infectionConfirmed("John"));
   // //                         System.out.println("\n"+ covid19.infectionConfirmed("Raj"));
   //                         System.out.println("\n"+ covid19.infectionConfirmed("Harry"));
   //                         System.out.println("\n"+ covid19.infectionConfirmed("Arun"));
   // //                         System.out.println("\n"+ covid19.infectionConfirmed("Sham"));
   //                            System.out.println("\n  is " + covid19.infectionPathLength("Ramesh"));
   //                        System.out.println("\nInfected hosts within 1 contact of   --> " + covid19.infectionRisk("Ramesh",6 ));
   //                         System.out.println(covid19.infectionPath("Ramesh"));
               
   
   // 
   //          System.out.println("\n" + covid19.infectionConfirmed("Evans, John"));  // EXPECTED: 23 CONTACTS: contact1 | contact2 | ...
   //             System.out.println(       covid19.infectionConfirmed("Reed, Albert")); // EXPECTED: 50 CONTACTS: contact1 | contact2 | ...
   //             covid19.infectionConfirmed("Close, Glenn");
   //             covid19.infectionConfirmed("Ford, Harrison");
   //             covid19.infectionConfirmed("Bacon, Kevin");
   //          
   //             String target = "Doyle, Richard";
   //             System.out.println("\n" + target + " is " + covid19.infectionPathLength(target) + " contact(s) away from an infected host."); // EXPECTED: 1
   //             System.out.println(covid19.infectionPath(target));                      // EXPECTED: [Ford, Harrison | Doyle, Richard]
   //          
   //             System.out.println("\nInfected hosts within 1 contact of " + target + " --> " + covid19.infectionRisk(target, 1));
   //        target = "Daniels, Jeff";
   //          System.out.println("\nInfected hosts within 5 contacts of " + target + " --> " + covid19.infectionRisk(target, 5));
   }                                                                          // EXPECTED: 3: [Close, Glenn | Evans, John | Ford, Harrison]
}
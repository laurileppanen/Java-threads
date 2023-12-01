package fi.utu.tech.assignment4;

import java.util.ArrayList;
import java.util.List;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
// Allokointifunktiot implementoidaan TaskAllocator-luokassa
import fi.utu.tech.common.TaskAllocator;

import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;


public class App4 {
    public static void main( String[] args )
    {
    	// Otetaan funktion aloitusaika talteen suoritusajan laskemista varten
        long startTime = System.currentTimeMillis();

        // Generoidaan kasa esimerkkitehtäväpalautuksia
        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.STATIC);

        // Tulostetaan tiedot esimerkkipalautuksista ennen arviointia
        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }

        // Luodaan uusi arviointitehtävä
        List<GradingTask> gradingTasks = TaskAllocator.sloppyAllocator(ungradedSubmissions);
        
        List<Thread> gradingThreads = new ArrayList<>();
        
        for (var i : gradingTasks) {
        	gradingThreads.add(new Thread(i));
        }
        
        for (var i : gradingThreads) {
        	i.start();
        }
        
        for (var i : gradingThreads) {
          try {
        	  i.join();
          } catch (InterruptedException e) {
        	  e.printStackTrace();
          }
        }
        
        List<Submission> gradedSubmissions =  new ArrayList<>();      
        
        for (var i : gradingTasks) {
        	gradedSubmissions.addAll(i.getGradedSubmissions());
        }
        
        // Tulostetaan arvioidut palautukset
        System.out.println("------------ CUT HERE ------------");
        for (var gs : gradedSubmissions) {
            System.out.println(gs);
        }

        // Lasketaan funktion suoritusaika
        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis()-startTime);
    }
}

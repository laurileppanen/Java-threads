package fi.utu.tech.assignment6;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
// Allokointifunktiot implementoidaan TaskAllocator-luokassa
import fi.utu.tech.common.TaskAllocator;

import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

public class App6 {
    public static void main(String[] args) {
        
        // Otetaan funktion aloitusaika talteen suoritusajan laskemista varten
           long startTime = System.currentTimeMillis();

           // Generoidaan kasa esimerkkitehtäväpalautuksia
           List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.UNFAIR);

           // Tulostetaan tiedot esimerkkipalautuksista ennen arviointia
           for (var ug : ungradedSubmissions) {
               System.out.println(ug);
           }

           // Luodaan uusi arviointitehtävä
           List<GradingTask> gradingTasks = TaskAllocator.allocate(ungradedSubmissions, ungradedSubmissions.size() / 2);
           
           ExecutorService gradingExecutor = Executors.newFixedThreadPool(4);
           
           for (var i : gradingTasks) {
        	   gradingExecutor.execute(i);
           }
           
           gradingExecutor.shutdown();
           
           try {
        	   if (!gradingExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
        		   gradingExecutor.shutdownNow();
        		   
        		   if (!gradingExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
        			   System.err.println("No termination");
        		   }
        	   } 
           } catch (InterruptedException ex) {
    		   gradingExecutor.shutdownNow();
    		   Thread.currentThread().interrupt();
    	   }
           
           List<Submission> gradedSubmissions = new ArrayList<>();
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


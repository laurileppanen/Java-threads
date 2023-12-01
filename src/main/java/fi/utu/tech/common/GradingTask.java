package fi.utu.tech.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * You need to modify this file
 */

public class GradingTask implements Runnable  {

    private Random rnd = new Random();
    private List<Submission> ungraded;
    private List<Submission> graded;
    

    public GradingTask(List<Submission> submissions) {
    	this.ungraded = submissions;
    }
    
    public List<Submission> gradeAll(List<Submission> submissions) {
        List<Submission> graded = new ArrayList<>();
        for (var s : submissions) {
            graded.add(grade(s));
        }
        return graded;
    }
    
    public void run() {
    	graded = gradeAll(this.ungraded);
    }
    
    public List<Submission> getGradedSubmissions() {
    	return graded;
    }

    
    public Submission grade(Submission s) {
        try {
            Thread.sleep(s.getDifficulty());
        } catch (InterruptedException e) {
            System.err.println("Who dared to interrupt my sleep?!");
        }
        return s.grade(rnd.nextInt(6));
    }
}



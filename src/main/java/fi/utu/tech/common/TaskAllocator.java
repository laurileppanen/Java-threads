package fi.utu.tech.common;

import java.util.List;
import java.util.ArrayList;


public class TaskAllocator {

    public static List<GradingTask> sloppyAllocator(List<Submission> submissions) {
        List<Submission> firstHalf = new ArrayList<>();
        List<Submission> secondHalf = new ArrayList<>();
        
        for (int i=0; i<submissions.size()/2; i++) {
        	firstHalf.add(submissions.get(i));
        }
        
        for (int i=submissions.size()/2; i<submissions.size(); i++) {
        	secondHalf.add(submissions.get(i));
        }
        
        List<GradingTask> gt = new ArrayList<>();
        gt.add(new GradingTask(firstHalf));
        gt.add(new GradingTask(secondHalf));
        
        return gt;
    }


    private static List<GradingTask> roundRobin(List<Submission> submissions, int taskCount) {
        List<GradingTask> gradingTasks = new ArrayList<>();
        List<List<Submission>> groups = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            groups.add(new ArrayList<>());
        }
        for (var s : submissions) {
            var i = submissions.indexOf(s) % taskCount;
            groups.get(i).add(s);
        }
        for (var g : groups) {
            gradingTasks.add(new GradingTask(g));
        }
        return gradingTasks;
    }

    public static List<GradingTask> allocate(List<Submission> submissions, int taskCount) {
        return roundRobin(submissions, taskCount);
    }
}

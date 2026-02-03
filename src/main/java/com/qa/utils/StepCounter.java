package com.qa.utils;

public class StepCounter {
	    private static ThreadLocal<Integer> totalSteps = ThreadLocal.withInitial(() -> 0);
	    private static ThreadLocal<Integer> passedSteps = ThreadLocal.withInitial(() -> 0);
	    private static ThreadLocal<Integer> warningSteps = ThreadLocal.withInitial(() -> 0);
	    private static ThreadLocal<Integer> failedSteps = ThreadLocal.withInitial(() -> 0);

	    public static void reset() {
	        totalSteps.set(0);
	        passedSteps.set(0);
	        warningSteps.set(0);
	        failedSteps.set(0);
	    }

	    public static void stepPassed() {
	        totalSteps.set(totalSteps.get() + 1);
	        passedSteps.set(passedSteps.get() + 1);
	    }

	    public static void stepWarning() {
	        totalSteps.set(totalSteps.get() + 1);
	        warningSteps.set(warningSteps.get() + 1);
	    }

	    public static void stepFailed() {
	        totalSteps.set(totalSteps.get() + 1);
	        failedSteps.set(failedSteps.get() + 1);
	    }

	    public static int getTotalSteps() { return totalSteps.get(); }
	    public static int getPassedSteps() { return passedSteps.get(); }
	    public static int getWarningSteps() { return warningSteps.get(); }
	    public static int getFailedSteps() { return failedSteps.get(); }
	}



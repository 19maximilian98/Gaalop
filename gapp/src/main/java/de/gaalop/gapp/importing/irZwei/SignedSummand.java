package de.gaalop.gapp.importing.irZwei;

/**
 * Represents a container for a signed expression, which is a summand.
 * @author Christian Steinmetz
 */
public class SignedSummand {

    private boolean positiveSigned;
    private ParallelObject parallelObject;

    public SignedSummand(boolean positiveSigned, ParallelObject parallelObject) {
        this.positiveSigned = positiveSigned;
        this.parallelObject = parallelObject;
    }

    public ParallelObject getParallelObject() {
        return parallelObject;
    }

    public boolean isPositiveSigned() {
        return positiveSigned;
    }

    public void setParallelObject(ParallelObject parallelObject) {
        this.parallelObject = parallelObject;
    }

    public void setPositiveSigned(boolean positiveSigned) {
        this.positiveSigned = positiveSigned;
    }

}

package dk.via.trmo.anticheat.model.containers;

public class PairResult {
    public ClassContainer cc1, cc2;
    public String longestCommonSubSequence;
    public int length;
    public double similarity;

    public PairResult(ClassContainer cc1, ClassContainer cc2, String longestCommonSubSequence) {
        this.cc1 = cc1;
        this.cc2 = cc2;
        this.longestCommonSubSequence = longestCommonSubSequence;
        length = longestCommonSubSequence.length();
        float longest = Math.max(cc1.classContent.length(), cc2.classContent.length());
        similarity = longestCommonSubSequence.length() / (double)longest;
        similarity = round(similarity, 2);
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public String toString() {
        return "Result{" +
                "\n  cc1=" + cc1.absolutePath +
                "\n  cc2=" + cc2.absolutePath +
                "\n  similarity=" + similarity +
                "\n}";
    }


}

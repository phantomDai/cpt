package cli;

import result.paeser.MRkilledInfo;
import result.paeser.MutantBeKilledInfo;
import result.paeser.MutationScore;

/**
 * @author phantom
 */
public class ResultParse {
    public ResultParse() {
    }

    public static void main(String[] args) {
//        String[] SUTName = {"SimpleLinear","SimpleTree","SequentialHeap","FineGrainedHeap","SkipQueue"};
        String[] SUTName = {"SkipQueue"};
        MutationScore mutationScore = new MutationScore();
        for (int j = 0; j < SUTName.length; j++) {
            mutationScore.calculateMutationScore(SUTName[j]);
            MutantBeKilledInfo.change(SUTName[j]);
            MRkilledInfo mRkilledInfo = new MRkilledInfo();
            mRkilledInfo.parseMutantBeKilledInfo(SUTName[j],TestSimpleLinear.loops);
        }
    }
}

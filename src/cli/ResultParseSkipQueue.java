package cli;

import result.parse.MRkilledInfo;
import result.parse.MutantBeKilledInfo;
import result.parse.MutationScore;

/**
 * @author phantom
 */
public class ResultParseSkipQueue {
    public ResultParseSkipQueue() {
    }

    public static void main(String[] args) {
//        String[] SUTName = {"SimpleLinear","SimpleTree","SequentialHeap","FineGrainedHeap","SkipQueue"};
        String[] SUTName = {"SkipQueue"};
        MRkilledInfo mRkilledInfo = new MRkilledInfo();
        MutationScore mutationScore = new MutationScore();
        for (int j = 0; j < SUTName.length; j++) {
            mutationScore.calculateMutationScore(SUTName[j]);
            MutantBeKilledInfo.change(SUTName[j]);
            mRkilledInfo.parseMutantBeKilledInfo(SUTName[j],TestSimpleLinear.loops);
        }
    }
}

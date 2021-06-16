package y.w.study.alg.array;

import org.junit.Test;

public class MaxConsecutiveOnes {

    @Test
    public void test() {
        assert findMaxConsecutiveOnes(new int[]{1,1,0,1,1,1}) == 3;
        assert findMaxConsecutiveOnes(new int[]{1,0,1,1,0,1}) == 2;
        assert findMaxConsecutiveOnes(new int[]{0,0,1,1,0,1,0}) == 2;

        assert findMaxConsecutiveOnes2(new int[]{1,1,0,1,1,1}) == 3;
        assert findMaxConsecutiveOnes2(new int[]{1,0,1,1,0,1}) == 2;
        assert findMaxConsecutiveOnes2(new int[]{0,0,1,1,0,1,0}) == 2;
    }

    public int findMaxConsecutiveOnes(int[] nums) {
        int ones = 0;

        for (int i=0; i<nums.length;) {
            int newOnes = 0;
            if (nums[i] != 1) {
                i++;
                continue;
            }

            newOnes++;
            i++;
            for (int j=i; j< nums.length; j++) {
                if (nums[j] != 1) {
                    i = j + 1;
                    break;
                }
                newOnes++;
                i = j + 1;
            }
            ones = Math.max(ones, newOnes);
        }

        return ones;
    }

    public int findMaxConsecutiveOnes2(int[] nums) {
        int count=0;
        int res=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==1){
                count++;
            }
            else{
                count=0;
            }
            res=Math.max(res,count);
        }
        return res;
    }
}

/**
 * This class contains all the static functions for MMN 14
 * 
 * @author Koren Bar 318810595
 * @version 11.12.2020
 */
public class Ex14 {

    //#region Question 1

    /**
     * Get the single integer between the couples on the array. O(log n)
     * 
     * Time complexity: O(log n)
     * Memory complexity: O(1)
     * @param a Array that contains couples of integers and a single one (*can't be empty and must contains the single one*)
     * @return The single int between the couples on the array.
     */
    public static int findSingle (int [] a)
    {
        //for (int i = 1; i < a.length / 2; i += 2) if(a[i] == a[i - 1]) return a[i - 1];
        //return a[a.length - 1]; // The last one by default.
        // O(n)

        // It could be better if I will do that using the binary search idea. O(logn)
        // based on the fact that array with 2 equal couples in a row is invalid. for example [7,7,7,7,5,7,7,7,7,7,7,7,7] is an invalid array. (otherwise we can't do it that way)

        if (a.length == 1) return a[0]; // only a single int in that array (not empty), return it. (optional line)

        // Note that array length is allways odd number!
        int iMiddle, iFirst = 0, iLast = a.length - 1;
        
        while (iFirst < iLast)
        {
            iMiddle = (iFirst + iLast) / 4 * 2; // = avg(iFirst, iLast) / 2 * 2 = the *even* middle index. for example: (0 + 14) / 2 = 7 (avg) => 7 / 2 = 3 (bcs it's an integer) => 3 * 2 = 6 = the middle even index.
            if (a[iMiddle] == a[iMiddle + 1])
                iFirst = iMiddle + 2; // The single one is in the right side.
            else iLast = iMiddle; // This single one is in the left side.
        }

        // Don't pay attention to this line, just an irrelevant idea.
        //if (iFirst > iLast) throw new InvalidParameterException("There is no a single number on that array"); 

        return a[iLast]; // preffered to return the left index bcs if it was possible that the array length is even number and there is no a single number the first index may will be out of range and exception will be thrown. (but it's irrelevant on that case bcs we get only a valid array)
    }
    //#endregion

    //#region Question 2

    /**
     * Get the smallest sequence of numbers on a given array that greater than a given number x. O(n)
     * 
     * Time complexity: since the worst case is to move 2 pointers from start to end - 2n => O(n)
     * Memory complexity: O(1)
     * @param arr the array to search in
     * @param x a number the sum should be greater from.
     * @return the smallest sequence on the given array with sum that greater than x.
     */
    public static int smallestSubSum(int arr[], int x)
    {
        int firstIndex = 0, lastIndex = 0, sum = 0, smallestSequence = Integer.MAX_VALUE;

        while (lastIndex < arr.length) // Worst case is 2n loops for both. O(n)
        {
            sum += arr[lastIndex++]; // Sum the last and then move the right pointer one to the right.

            while (sum > x) // That loop will take the left pointer to the right until the sum will not be greater than x.
            {
                // Remember the smallest sequence.
                smallestSequence = Math.min(lastIndex - firstIndex, smallestSequence);

                if (smallestSequence <= 1) break; // Best possible, break here!

                sum -= arr[firstIndex++]; // Un-sum the first and then move the left pointer one to the right.
            }
        }

        // if there is no sequence that greater than x, -1 will be returned.
        return smallestSequence == Integer.MAX_VALUE // used the MAX int value as "flag" instead of unnecessary additional condition inside the loop.
            ? -1 : smallestSequence; 
    }
    //#endregion

    //#region Question 3

    /**
     * Get the count of expressions in the format x1+x2+x3 with the result of the given number when each x is between 1 and 10.
     * 
     * Time complexity: The func will run 10^3 times so O(1)
     * @param num the expected result.
     * @return Returns the count of solutions for this number using the formual x1 + x2 + x2 when each x is between 1 to 10.
     */
    public static int solutions(int num)
    {
        return solutions(num, 1, 1, 1);
    }

    // The inner recursive private function.
    private static int solutions(int num, int x1, int x2, int x3)
    {
        int sum = x1 + x2 + x3; // calc the sum first

        // If it is a solution, print it!
        if (sum == num) System.out.println(x1 + " + " + x2 + " + " + x3);

        // Set the Xs' (they are structs and not references so there is no problem to set them instead creating new integers)
        if (++x3 > 10)
        {
            x3 -= 10;
            if (++x2 > 10)
            {
                x2 -= 10;
                if (++x1 > 10) // End here, we reached the limit. (stop condition)
                    return sum == num ? 1 : 0;
            }
        }

        return (sum == num ? 1 : 0) + solutions(num, x1, x2, x3);
    }
    //#endregion

    //#region Question 4

    /**
     * Get the count of true regions on an array.
     * 
     * Time complexity: 
     * it's tricky a bit, for each true that we find we call to the clear func, but since it clearing the all region actually we'll call to the clear func as the number of the regions so in total it will run for max of w*h.
     * so we have w*h of cntTrueReg + w*h of clearReg => O(w*h). and because it's a square array O(n^2).
     * 
     * @param mat A boolean array
     * @return The count of true regions.
     */
    public static int cntTrueReg (boolean[][]mat)
    {
        if (mat.length == 0) return 0;
        return cntTrueReg(mat, 0, 0);
    }

    // When finding "true" I will set it and all its region to "false" and will count 1.
    private static int cntTrueReg (boolean[][]mat, int x, int y)
    {
        if (x >= mat[0].length || y >= mat.length) return 0; // out of range

        // Dividing the next x by the width will give 1 if we have to move on to the next line, otherwise 0.
        return (clearReg(mat, x, y) > 0 ? 1 : 0) // Add 1 if the current is true. (otherwise clearReg will return 0)
            + cntTrueReg(mat, (x + 1) % mat[0].length, y + (x + 1) / mat[0].length);
    }

    /**
     * Set the region that contains the specified location to false on a boolean metrix.
     * @param mat the boolean metrix
     * @param x x location
     * @param y y location
     * @return count of values that was on the cleared region, or 0 if the location is not a part of a region.
     */
    private static int clearReg(boolean[][]mat, int x, int y)
    {
        if (x < 0 || y < 0) return 0; // out of range
        if (x >= mat[0].length || y >= mat.length) return 0; // out of range
        if (mat[y][x] == false) return 0; // out of region

        // Still inside the region, continue to clear.
        mat[y][x] = false;
        return 1 + clearReg(mat, x - 1, y) // Left
                 + clearReg(mat, x, y - 1) // Top
                 + clearReg(mat, x + 1, y) // Right
                 + clearReg(mat, x, y + 1); // Bottom
    }
    //#endregion
}

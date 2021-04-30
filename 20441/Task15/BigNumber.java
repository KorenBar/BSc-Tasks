/**
 * Represents a big number with no memory limit.
 * 
 * @author Koren Bar 318810595
 * @version 22.01.21
 */
public class BigNumber {
    // Notes: 
    // - We'll never convert this BigNumber to a variable here since this BigNumber chould represent a much bigger number than the max value of the biggest value type. (unlimited, the avaliable RAM capacity will be the limit:)
    // - long value type will be more than enough (actully int value also will be enough unless you have more than 26GB RAM available :)
    // - less code duplication is preferred when complexity is still best possible. (even if we'll pass over the list again as lond as not affecting the complexity)

    private IntNode _head; // RTL (ones digit is first)

    /**
     * Create an empty BigNumber instance
     * Time complexity: O(1)
     * Memory complexity: O(1)
     */
    public BigNumber()
    {
        _head = new IntNode(0);
    }

    /**
     * Create an instance of BigNumber that represent a long number
     * Time complexity: O(n) when n is the number of digits.
     * Memory complexity: O(n)
     * @param num the long value this BigNumber will represent
     */
    public BigNumber(long num)
    { // assuming that number is not negative.
        this(); // will set the head to 0 (so it will not be null)

        addAfter(_head, num); // that inserting method replacing the previous code below.

        // IntNode lastNode = _head;
        // do
        // {
        //     lastNode.setNext(lastNode = new IntNode((int)(num % 10)));
        //     num /= 10;
        // }
        // while(num != 0);

        // Remove the first default node (it was for preventing code duplication)
        _head = _head.getNext();
    }

    /**
     * Deep clone constructor
     * Time complexity: O(n)
     * Memory complexity: O(n)
     * @param other the source object to clone
     */
    public BigNumber(BigNumber other)
    {
        IntNode lastClone = _head = new IntNode(other._head.getValue()); // Clone head first.

        // Start loop from the second node, while not arrived to the end of the source, move on.
        for(IntNode sourceNode = other._head.getNext(); sourceNode != null; sourceNode = sourceNode.getNext())
            lastClone.setNext(lastClone = new IntNode(sourceNode.getValue())); // Clone that node and point to it.
        // I've learned something intresting here, lastClone.setNext was called before the lastClone pointer was changed. (otherwise, the next would point to itself)
    }

    /**
     * Addition between two big numbers.
     * Time complexity: O(n) when n is the longer number length
     * Memory complexity: O(n) for creating the new result object
     * @param other the other big number to add.
     * @return a new big number that represents the result of this number + the other.
     */
    public BigNumber addBigNumber (BigNumber other) // O(n) when n is the longer number length.
    { // Using the "Vertical Addition" technique.
        int temp = 0, digitRes;
        BigNumber result = new BigNumber(); // 0

        IntNode nodeA = this._head, 
            nodeB = other._head, 
            lastResNode = result._head;

        while (nodeA != null || nodeB != null)
        {
            digitRes = temp; // add the excess from the last addition.
            if (nodeA != null) digitRes += nodeA.getValue();
            if (nodeB != null) digitRes += nodeB.getValue();
            temp = digitRes / 10; // save the excess of that result to the next digits.

            // Set the next digit on the result and remember the last result node.
            //lastResNode = result.addAfter(lastResNode, digitRes % 10);
            lastResNode.setNext(lastResNode = new IntNode(digitRes % 10)); // saves some conditions compared to addAfter.

            // Move to the next digits. (where not arrived to the end yet)
            if (nodeA != null) nodeA = nodeA.getNext();
            if (nodeB != null) nodeB = nodeB.getNext();
        }

        // Don't forget to add the last excess.
        if (temp != 0) lastResNode.setNext(new IntNode(temp));

        // And before returning the result, remove the first 0 node that was there for convenience.
        result._head = result._head.getNext();

        return result;
    }

    /**
     * Subtraction between two big numbers.
     * Time complexity: O(n) when n is the longer number length
     * Memory complexity: O(n) for creating the new result object
     * @param other the other big number.
     * @return a new big number that represents the difference between this number and the other number.
     */
    public BigNumber subtractBigNumber (BigNumber other)
    { // Using the "Vertical Subtraction" technique.

        /* I will do it with a single pass over the list but the code will be longer, 
        the idea is to consider 2 options while passing once over the list until finding the smaller number and then take the correct option. 
        (instead of using the compareTo method) it's actually still O(n) but you required that :| */
        /// Update: No, I will not do that idea because the memory and time complexity will be the same. (it will be very stupid)

        BigNumber result = new BigNumber(); // 0
        IntNode biggerNode, smallerNode;

        // find which number is bigger
        switch(this.compareTo(other))
        {
            case 1: // this is bigger
                biggerNode = this._head;
                smallerNode = other._head;
                break;
            case -1: // the other is bigger
                biggerNode = other._head;
                smallerNode = this._head;
                break;
            default: // probably 0 so they are equals
                return result; // = 0
        }

        int temp = 0, digitRes;
        IntNode lastResNode = result._head, realLastResNode = lastResNode;
        
        // the bigger will be longer or in the same length.
        while (biggerNode != null)
        {
            digitRes = biggerNode.getValue() + temp;
            if (smallerNode != null) digitRes -= smallerNode.getValue();
            temp = digitRes < 0 ? -1 : 0; // if smaller than 0, take 1 from the next
            if (digitRes < 0) digitRes += 10; // and add 10 to the result of this digit

            // Set the next digit on the result and remember the last result node
            lastResNode.setNext(lastResNode = new IntNode(digitRes));

            // If the new node is not 0, remember it as the current real last node.
            if (digitRes != 0) realLastResNode = lastResNode;

            // Move to the next digits. (where not arrived to the end yet)
            biggerNode = biggerNode.getNext();
            if (smallerNode != null) smallerNode = smallerNode.getNext();
        }

        // It's not possible that temp will not be 0. (otherwise the result is negative, so the compareTo method may failed)
        if (temp != 0) new BigNumber(); // = 0

        // Disconnect possible extra zeros.
        realLastResNode.setNext(null);

        // Before returning the result, remove the first 0 node that was there for convenience.
        result._head = result._head.getNext();

        return result;
    }

    /**
     * Add a long value to this big number. (non-negative number)
     * Time complexity: O(n) when n is the longer number length
     * Memory complexity: O(n) for creating the new result object
     * @param num the value to add.
     * @return a new big number that represents the result of the addition.
     */
    public BigNumber addLong (long num)
    { // assuming that number is not negative.
        /* Since the max length of long value is 19, the memory complexity when converting it to a BigNumber will be O(1)
        (The total memory complexity of this method is O(n) bcs it's actually create a new object as result)
        SO THERE IS NO REASON TO DO IT DIFFERENTLY! */
        return this.addBigNumber(new BigNumber(num));
    }

    /**
     * Multiplication of two big numbers.
     * Time complexity: O(n^2) when n is the AVERAGE number length
     * Memory complexity: O(n) for creating the new result object
     * @param other the other big number.
     * @return a new big number that represents the multiplication of this number and the other number.
     */
    public BigNumber multBigNumber (BigNumber other)
    {
        BigNumber result = new BigNumber(); // 0

        // Simple cases:
        if (this._head.getNext() == null) // this is a single digit
            if (this._head.getValue() == 0) return result; // = 0
            else if (this._head.getValue() == 1) return new BigNumber(other); // = other

        if (other._head.getNext() == null) // the other is a single digit
            if (other._head.getValue() == 0) return result; // = 0
            else if (other._head.getValue() == 1) return new BigNumber(this); // = this

        // from here it will be O(n^2)

        int excess = 0 , digitsRes, countA = 0;
        BigNumber tempRes = result; // point it first to the same object of the result, so will don't clone the first result for nothing (by using addBigNumber with 0).
        IntNode lastResNode = tempRes._head; // pointer to the last node on the temp result.
        // take one of the numbers and multiple each digit of it with each of the other number digits.
        for (IntNode nodeA = this._head; nodeA != null; nodeA = nodeA.getNext())
        {
            countA++;
            excess = 0; // reset the excess (if not already 0 we have added it on the previous digit using the addAfter method)
            for (IntNode nodeB = other._head; nodeB != null; nodeB = nodeB.getNext())
            {
                digitsRes = nodeA.getValue() * nodeB.getValue(); // multiple digits
                digitsRes += excess; // add the excess from the previous step
                excess = digitsRes / 10; // save the new excess for the next step

                if (nodeB.getNext() == null) // it's the last step for that digit of A,
                    lastResNode = addAfter(lastResNode, digitsRes); // add all the remining digits including the excess.
                else lastResNode.setNext(lastResNode = new IntNode(digitsRes % 10)); // not the last step, add just the ones. (excess was saved for later)
            }
            tempRes._head = tempRes._head.getNext(); // remove the first 0 node that was there for convenience.
            if (tempRes != result) // this is not the first temp result
                result = result.addBigNumber(tempRes); // add this temp result to the total result.
            tempRes = new BigNumber(); // reset the temp result for the next digit.
            lastResNode = tempRes._head; // point to the last node of the new temp result (to the head since it's empty)
            for (int i = 0; i < countA; i++) lastResNode = addAfter(lastResNode, 0); // move the next temp result so the ones of it will be under the next nodeA. (by adding zeros)
        }

        return result;
    }

    /**
     * Insert all of the digits of a specified long value after a spescified node. (digits will be added from right to left)
     * Time complexity: O(n) when n is the number of digits.
     * Memory complexity: O(n) for the new nodes.
     * @param node The node to add after.
     * @param value long value as digits to add.
     * @return The last digit that was added.
     */
    private IntNode addAfter(IntNode node, long value) // O(n) when n is the number of digits.
    { // DON'T MAKE THIS METHOD PUBLIC AND KEEP RETURN AN IntNode FROM THAT LIST!
        IntNode newDigit = new IntNode((int)(value % 10)); // create a new node of the last digit.

        // These two lines is optional if you want to insert without cutting the list.
        IntNode temp = node.getNext();
        newDigit.setNext(temp);

        node.setNext(newDigit);

        if (value == 0) return newDigit; // The initial value was 0 and we've added it.
        if (value / 10 == 0) return newDigit; // That it. no more digits, we've added all of them.

        return addAfter(newDigit, value / 10);
    }

    /**
     * Compare between two big numbers.
     * Time complexity: O(n)
     * Memory complexity: O(1)
     * @param other the big number to compare with.
     * @return 1 => this number is bigger than the other, 0 => the numbers are equals, -1 => the other number is bigger than this.
     */
    public int compareTo (BigNumber other) // O(n)
    { // The list is RTL, doing it with recursion.
        return compareTo(_head, other._head);
    }

    private int compareTo (IntNode myNode, IntNode oNode)
    { // O(n)
        if (myNode != null && oNode == null) return 1; // more digits on this
        if (myNode == null && oNode != null) return -1; // more digits on the other

        if (myNode == null && oNode == null) return 0; // both in the same length, check the digits in the direction back.

        int res = compareTo(myNode.getNext(), oNode.getNext());
        // From here it's in direction back to the first call. (since it's RTL - from the higher digits to the ones digit)

        if (res != 0) return res; // Got an answer, keep return it.
        // Until now the numbers are equals, check the current digit.
        if (myNode.getValue() > oNode.getValue()) return 1; // this number is bigger from the current digit.
        if (myNode.getValue() < oNode.getValue()) return -1; // the other number is bigger from the current digit.

        return 0; // still equals.
    }

    // This method was relevant for LTR list and was replaced
    private int compareTo_LTR (IntNode myNode, IntNode oNode, boolean thisIsBigger, boolean otherIsBigger)
    { // O(n)
        if (myNode != null && oNode == null) return 1; // more digits on this
        if (myNode == null && oNode != null) return -1; // more digits on the other

        if (myNode == null && oNode == null) // both in the same length, check the flags.
            return thisIsBigger ? 1 : otherIsBigger ? -1 : 0; 

        // not arrived to the end yet on both, compare digits and move on.
        if (!thisIsBigger && !otherIsBigger) // until now they are equals, compare the current digits.
            if (myNode.getValue() != oNode.getValue()) // the numbers are diffrent from this digit.
                thisIsBigger = !(otherIsBigger = myNode.getValue() < oNode.getValue()); // set both flags (I don't like curly brackets)

        return compareTo_LTR(myNode.getNext(), oNode.getNext(), thisIsBigger, otherIsBigger);
    }

    /**
     * Get string representation of this BigNumber.
     * Time complexity: O(n)
     * Memory complexity: O(n)
     * @return Returns this big number as string.
     */
    public String toString()
    {
        return toString(this._head);
    }
    
    private String toString(IntNode node) // n => O(n)
    { // Get string with all of the values from that node.
        if (node == null) return ""; // Stop condition: add nothing.
        //return node.getValue() + toString(node.getNext()); // LTR
        return toString(node.getNext()) + node.getValue(); // RTL
    }
}

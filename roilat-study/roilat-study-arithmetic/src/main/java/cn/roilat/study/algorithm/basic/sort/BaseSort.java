package cn.roilat.study.algorithm.basic.sort;

public class BaseSort {

    /**
     * 数组顺序反转
     * 
     * @param a
     * @param start
     * @param end
     */
    public void rangeRevert(Integer[] a, int start, int end) {
        assert start <= end;
        if (start == end) {
            return;
        }
        while (start < end) {
            Integer obj = a[start];
            a[start++] = a[end];
            a[end--] = obj;
        }
    }

    public void sort(Integer[] a, int pos, int len) {
        assert pos >= 0 && len > 0 && a.length >= pos + len;
        if (len < 2) {
            return;
        }
        int initRunLen = countRunAndMakeAscending(a, pos, pos + len - 1);
        doSort(a, pos, len, initRunLen);
    }

    protected void doSort(Integer[] a, int pos, int len, int start) {

    }

    protected void exchange(Integer[] a, int x, int y) {
        a[x] = a[x] ^ a[y];//$x^$xy
        a[y] = a[x] ^ a[y];//$x^$y^$y=$x
        a[x] = a[x] ^ a[y];//$x^$y^$x=$y;y此时已经等于x的值；
    }

    /**
     * 这里做一种尝试，在数据首部查询是否有已经排好序的子部分，并得出其长度做为初始部分
     * @param a
     * @param lo
     * @param hi
     * @return
     */
    private int countRunAndMakeAscending(Integer[] a, int lo, int hi) {
        assert lo < hi;
        int runHi = lo + 1;//从下一位（第二位）开始
        if (runHi == hi) {//如果需要计算的总长度是2，则返回1，认为只有第一数是排序好的
            /**
             * 因为这里只是做一种尝试，必然需要后续排序计算，这里不过多进行处理，当然这里肯定也是可以处理之后直接返回2的，出于功能单一性考虑（只查找已排好序的部分）也是逻辑重复
             * if(a[runHi].compareTo(a[hi]) > 0) {//降序
             *  temp = a[hi];
             *  a[hi] = a[runHi];
             *  a[runHi] = temp;
             *  return 2;
             * }
             */
            return 1;
        }

        //如果只需求出的长度是连接增加或者减少即可
        if (a[runHi++].compareTo(a[lo]) > 0) {//正常升序
            while (runHi < hi && a[runHi].compareTo(a[runHi - 1]) >= 0) {
                runHi++;
            }
        } else {
            //降序时需要反转
            while (runHi < hi && a[runHi].compareTo(a[runHi]) < 0) {
                runHi++;
            }
            rangeRevert(a, lo, runHi - 1);
        }
        return runHi - lo;
    }
}
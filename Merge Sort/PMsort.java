import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class PMsort extends RecursiveAction{
	static final int SEQ_THRESHOLD = 200000;
	
	int low;
	int high;
	int[] array;
	int[] tempMergArr;

	PMsort(int[] arr, int Low, int High){
		array = arr;
		low = Low;
		high = High - 1;
		tempMergArr = new int[High];
	}
	
    	protected void compute(){
		if((high - low) <= SEQ_THRESHOLD){
			Arrays.sort(array, low, high + 1);
		}
		else{
			int mid = low + (high - low) / 2;
			PMsort left = new PMsort(array, low, mid + 1);
			PMsort right = new PMsort(array, mid + 1, high + 1);
			invokeAll(left, right);
			mergeParts(low, mid, high);
		}	
	}
	
    	private void mergeParts(int low, int mid, int high){
		 
        	for (int i = low; i <= high; i++){
            		tempMergArr[i] = array[i];
        	}
        	int i = low;
        	int j = mid + 1;
        	int k = low;
        	while(i <= mid && j <= high){
            		if(tempMergArr[i] <= tempMergArr[j]){
                		array[k] = tempMergArr[i++];
            		} 
            		else{
                		array[k] = tempMergArr[j++];
            		}
            		k++;
        	}
        	while (i <= mid) {
            		array[k++] = tempMergArr[i++];
        	}
		while(j <= high){
	    		array[k++] = tempMergArr[j++];
		}
    	}
}



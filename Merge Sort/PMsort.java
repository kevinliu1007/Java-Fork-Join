import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

<<<<<<< HEAD
public class sort extends RecursiveAction{
	static final int SEQ_THRESHOLD = 20;

=======
public class PMsort extends RecursiveAction{
	static final int SEQ_THRESHOLD = 200000;
	
>>>>>>> 5a5587a53250a0b4d167292665f225b15abdec19
	int low;
	int high;
	int[] array;
	int[] tempMergArr;

	sort(int[] arr, int Low, int High){
		array = arr;
		low = Low;
		high = High - 1;
		tempMergArr = new int[High];
	}
<<<<<<< HEAD

	protected void compute(){
=======
	
    	protected void compute(){
>>>>>>> 5a5587a53250a0b4d167292665f225b15abdec19
		if((high - low) <= SEQ_THRESHOLD){
			Arrays.sort(array, low, high + 1);
		}
		else{
			int mid = low + (high - low) / 2;
<<<<<<< HEAD
			sort left = new sort(array, low, mid + 1);
			sort right = new sort(array, mid + 1, high + 1);
			left.fork();
			right.compute();
			left.join();
=======
			PMsort left = new PMsort(array, low, mid + 1);
			PMsort right = new PMsort(array, mid + 1, high + 1);
			invokeAll(left, right);
>>>>>>> 5a5587a53250a0b4d167292665f225b15abdec19
			mergeParts(low, mid, high);
		}
	}
<<<<<<< HEAD
=======
	
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
>>>>>>> 5a5587a53250a0b4d167292665f225b15abdec19

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
		while(i <= mid){
			array[k++] = tempMergArr[i++];
		}
		while(j <= high){
			array[k++] = tempMergArr[j++];
		}
	}
}
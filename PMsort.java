import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class PMsort extends RecursiveAction{
	static final int SEQ_THRESHOLD = 2000000;
	
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
			Arrays.sort(array, low, high);
			Arrays.sort(tempMergArr, low, high);
		}
		else{
			int mid = low + (high - low) / 2;
			PMsort left = new PMsort(array, low, mid);
			PMsort right = new PMsort(array, mid + 1, high);
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
    }
	
	public class merge extends RecursiveAction{
		int middle;
		int lowerIndex;
		int higherIndex;
		
		merge(int m, int l, int h){
			middle = m;
			lowerIndex = l;
			higherIndex = h;
		}
		
		private int bisearch(int a, int b, int[] arr, int x){
			while(a < b){
				int mid = (a + b) / 2;
				if(x <= arr[mid]){
					b = mid;
				}
				else{
					a = mid + 1;
				}
			}
			return b;
		}
		
		protected void compute(){
			if(higherIndex - lowerIndex < 10000){
				for (int i = lowerIndex; i <= higherIndex; i++) {
		            tempMergArr[i] = array[i];
		        }
		        int i = lowerIndex;
		        int j = middle + 1;
		        int k = lowerIndex;
		        while (i <= middle && j <= higherIndex) {
		            if (tempMergArr[i] <= tempMergArr[j]) {
		                array[k] = tempMergArr[i];
		                i++;
		            } else {
		                array[k] = tempMergArr[j];
		                j++;
		            }
		            k++;
		        }
		        while (i <= middle) {
		            array[k] = tempMergArr[i];
		            k++;
		            i++;
		        }
		    }
			else{
				merge left = new merge(lowerIndex, (middle - lowerIndex) / 2, middle);
				merge right = new merge(middle + 1, (higherIndex - middle + 1) / 2, higherIndex);
				left.fork();
				right.compute();
				left.join();
			}
		}
	}
	
}



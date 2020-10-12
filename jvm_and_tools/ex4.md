
# Exercise 4

Run and inspect the program GCstrange using visualvm, in particular check the evolution of the heap, the activity of the GC and the activity of the Finalizer thread. Using GCstrange as a template, write a simple class that overrides the method finalize in order to count how many times the garbage collector is invoked. Write a main to test this class.

<b> Goal: </b> 

Understanding the finalization of objects in Java; exploiting the finalize method to infer simple properties of garbage collection.

<b>Expected output:</b>

 A clear explanation of why the occupation of the memory increases during execution of the given program; a working Java program implementing the given specification.

# Analysis

When `finalize()` gets called, it inserts the current instance reference in the list <b> dump </b>. Dump is a static field, and as such it gets stored in non-heap memory. Since the reference is not stored in the heap, the GC cannot delete the object, and the memory usage keeps growing, as seen in visualVM.

![heap usage](Immagine.png)

If we want to count how many times `finalize()` we can override it so that,instead of adding the instance to a list, it increments a static counter.
```
import java.util.*;

class GCCount {
    static int dump = 0;    
    static final  int COUNT =  10000000;

    public static void main(String [] args) throws InterruptedException{
	GCCount tmp = null;
	for (int i = 1; i < COUNT; i++){
	    if (i%(COUNT/100)==0) {
		System.out.println(i + " => " + dump);
		Thread.sleep(1000);
	    }
	    tmp = new GCCount();
	    
	}
    }

    public GCCount(){
    }
    protected void finalize(){
	dump += 1;
    }	
}
    

```
In this version, the GC can delete the instance created, freeing the heap memory occupied.

![heap usage of GCCount](Immagine2.png)

## Finalizer
The finalizer thread is much more active in the version that simply counts the finalize calls.
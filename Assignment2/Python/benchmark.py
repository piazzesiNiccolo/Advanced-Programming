import threading
from functools import wraps
import time
import csv



def _timeFunctionCalls(rounds,function,*args,**kwargs):
    results = dict()
    for i in range(rounds):
        run = 0
        time1 = time.perf_counter()
        function(*args, **kwargs)
        run = time.perf_counter() - time1
        results[i+1] = run
    return results

def benchmark(warmups=0, iter=1, verbose=False, csv_file=None):
    def decorator(func):
        @wraps(func)
        def wrapper(*args,**kwargs):
            warm = dict()
            invoke = _timeFunctionCalls(iter,func,*args,**kwargs)
            if verbose:
                if warmups==0:
                    print("---skipping warmups---")
                else:
                    print("WARMUP ROUNDS\n")
                    warm = _timeFunctionCalls(warmups,func,*args,**kwargs)
                    print("{:<8} {:<15}".format('round', 'time'))
                    for k,v in warm.items():
                        print("{:<8} {:<15}".format(k,v))
                
                print("\nINVOCATION ROUNDS")  
                print("{:<8} {:<15}".format('round', 'time'))
                for k,v in invoke.items():
                    print("{:<8} {:<15}".format(k,v))
            
            avg = sum(invoke.values()) / iter
            variance = sum((v - avg) ** 2 for v in invoke.values()) / iter
            print("{:<8} {:<15}".format('average time',avg))
            print("{:<8} {:<15}".format('variance', variance))
            
            if csv_file:
                with open(csv_file, "w") as f:
                    writer = csv.writer(f)      
                    writer.writerow(['run num', 'is warmup', 'timing'])
                    for k,v in warm.items():
                        writer.writerow([k,'yes',v])
                    for k,v in invoke.items():
                        writer.writerow([k+warmups,'no',v])
                    
                    writer.writerow(['average', avg])
                    writer.writerow(['variance',variance])
                    
            return func
        return wrapper
    return decorator


def fibonacci(n=0):
    if n==0:
        return 0
    elif n==1:
        return 1
    else:
        return fibonacci(n-1) + fibonacci(n-2)


def test():
    fib = benchmark(warmups=3,iter=3,verbose=True,csv_file='file.csv')
    fib = fib(fibonacci)
    fib(5)
   

test()
   

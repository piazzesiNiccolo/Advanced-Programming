import threading
from functools import wraps

def benchmark(warmups=0, iter=1, verbose=False, csv_file=None):
    def decorator(func):
        @wraps(func)
        def wrapper(*args,**kwargs):
            for i in range(warmups):
                if verbose:
                    print(" warmup round n.{}: ".format(i) )
                print(func(*args,**kwargs))
            
            
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
    fib = benchmark(warmups=2,verbose=True)
    fib = fib(fibonacci)
    print(fib(10))
   

test()
   

import threading
from functools import wraps
import time
import csv


def _timeFunctionCalls(rounds, function, *args, **kwargs):
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
        def wrapper(*args, **kwargs):
            result = func(*args,**kwargs) ##store the function result for return
            warm = dict()
            if warmups != 0:
                warm = _timeFunctionCalls(warmups, func, *args, **kwargs)
            invoke = _timeFunctionCalls(iter, func, *args, **kwargs)
            if verbose:
                print("{:<8} {:<15} {:<10}".format(
                    'run num', 'is warmup', 'time'))
                
                for k, v in warm.items():
                    print("{:<8} {:<15} {:<10}".format(k, 'yes', v))

                for k, v in invoke.items():
                    print("{:<8} {:<15} {:<10}".format(k+warmups, 'no', v))

            avg = sum(invoke.values()) / iter
            variance = sum((v - avg) ** 2 for v in invoke.values()) / iter
            print("{:<8} {:<15}".format('average time', avg))
            print("{:<8} {:<15}".format('variance', variance))

            if csv_file:
                with open(csv_file, "w") as f:
                    writer = csv.writer(f)
                    writer.writerow(['run num', 'is warmup', 'timing'])
                    for k, v in warm.items():
                        writer.writerow([k, 'yes', v])
                    for k, v in invoke.items():
                        writer.writerow([k+warmups, 'no', v])
                    writer.writerow(['average', avg])
                    writer.writerow(['variance', variance])
            return result
        return wrapper
    return decorator


def _rec_fibonacci(n):
    if n == 0:
        return 0
    elif n == 1:
        return 1
    else:
        return _rec_fibonacci(n-1) + _rec_fibonacci(n-2)

@benchmark(verbose=True,iter=10)
def fibonacci(n=0):
    #helper function so the decorator doesnt execute its extra code for each recursive call
    return _rec_fibonacci(n)


def test():
  fibonacci(4)


if __name__ == "__main__":
    test()

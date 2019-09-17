package y.w.concurrency.jcp
//
// jcip.net
// Using FutureTask to preload data that is needed later
//
import java.util.concurrent.*

class ProductInfo {
    String name;
    Date   date;
    
    String toString() { return name + ' ' + date; }
}

class DataloadException extends Exception {}

class PreLoader {
    ProductInfo loadProductInfo() throws DataloadException { return null; }
    
    private final FutureTask<ProductInfo> future = 
        new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
            public ProductInfo call() throws DataloadException {
                return loadProductInfo();
            }
        });
    
    private final Thread thread = new Thread(future);
    
    public void start() { thread.start(); }
    
    public ProductInfo get() throws DataloadException, InterruptedException {
        try { return future.get(); }
        catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataloadException) throw (DataloadException) cause;
            else throw LaunderThrowable.launderThrowable(cause);
        }
    }
}

class PreLoaderImpl extends PreLoader {
    ProductInfo loadProductInfo() throws DataloadException {
        return new ProductInfo(name : "Yang Wang", date : new Date())
    }
}

def preloader = new PreLoaderImpl()
preloader.start()
println preloader.get()
//preloader.start()
//println preloader.get()


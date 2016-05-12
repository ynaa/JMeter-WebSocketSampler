public class TestThreadStackSizes
{
    public static void main(final String[] args)
    {
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(final Thread t, final Throwable e)
            {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        });
        int numThreads = 1000;
        if (args.length == 1)
        {
            numThreads = Integer.parseInt(args[0]);
        }

        for (int i = 0; i < numThreads; i++)
        {
            try
            {
                Thread t = new Thread(new SleeperThread(i));
                t.start();
            }
            catch (final OutOfMemoryError e)
            {
                throw new RuntimeException(String.format("Out of Memory Error on Thread %d", i), e);
            }
        }
    }

    private static class SleeperThread implements Runnable
    {
        private final int i;

        private SleeperThread(final int i)
        {
            this.i = i;
        }

        public void run()
        {
            try
            {
                System.out.format("Thread %d about to sleep\n", this.i);
                Thread.sleep(1000 * 60 * 60);
            }
            catch (final InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}

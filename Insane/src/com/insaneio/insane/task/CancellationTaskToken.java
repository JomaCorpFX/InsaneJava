package com.insaneio.insane.task;

/**
 * Created by Joma Espinoza Bone on 02/11/2016.
 */
public class CancellationTaskToken
{

    private Boolean cancelled = false;
    private final Inner inner;

    public CancellationTaskToken()
    {
        inner = new Inner();
    }

    
    public Boolean isCancelled()
    {
        return cancelled;
    }

    public void cancel(Boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    public void throwIfCanceled() throws Exception
    {
        inner.throwIfCanceledX();
    }

    private class Inner
    {

        private void throwIfCanceledX() throws Exception
        {
            if (isCancelled())
            {
                throw new TaskCancelledException(X.get("hgf6QQpQeVXwDTG0EQ6i1_mLB7yM7UYRTvTMUUnNy_Y,", true)/* "Cancelled Task" */);
            }
        }
    }
}

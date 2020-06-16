package in.techware.lataxidriverapp.listeners;

/**
 * Created by Jemsheer K D on 29 November, 2016.
 * Package com.company.sample.listeners
 * Project Telugu Catholic Matrimony
 */

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}

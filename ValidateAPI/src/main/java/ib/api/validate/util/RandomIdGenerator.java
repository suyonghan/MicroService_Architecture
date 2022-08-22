package ib.api.validate.util;

import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class RandomIdGenerator
{
    private static final int DB_COLUMN_LEN = 28;
    private static AtomicLong globalIndex = new AtomicLong();

    public static String get(String instanceId)
    {
        globalIndex.compareAndSet(999999L, 0);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = format.format(new Date());
        String index = String.format("%06d", globalIndex.getAndIncrement());

        int extentionLen = DB_COLUMN_LEN - (date.length() + instanceId.length() + index.length());
        String millisecond = Long.toString(System.currentTimeMillis());
        String extention =  millisecond.substring(millisecond.length() - extentionLen);

        return date + instanceId + index + extention;
    }
}
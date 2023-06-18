package course.concurrency.m2_async.cf.report;

import java.nio.ByteBuffer;

public class Others {

    static class Item {}
    static class Customer {}
    public static class Report {}

    public static void main(String[] args) {

        byte f = (byte) 0XFF;
        System.out.println(f);
        System.out.println(-1 & 0XFF);
        String s = Integer.toHexString(165);
        long mask = 0xFF00FF00FF00FF00L;
        byte [] b = new byte[]{(byte) 0XA5};
        System.out.println(mask);
    }

    public byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }
}

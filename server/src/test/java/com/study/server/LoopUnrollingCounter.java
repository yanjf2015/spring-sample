package com.study.server;

import org.openjdk.jmh.annotations.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode( Mode. Throughput)
@OutputTimeUnit( TimeUnit. SECONDS)
@State( Scope. Thread)
public class LoopUnrollingCounter {

    private static final int MAX = 100000;
    private long[] data = new long[MAX];

    @Setup
    public void createData() {
        Random random = new Random();
        for (int i = 0; i < MAX; i++) {
            data[ i] = random. nextLong();
        }
    }

    @Benchmark
    public long intStride1() {
        long sum = 0;
        for (int i = 0; i < MAX; i++) {
            sum += data[ i];
        }
        return sum;
    }

    @Benchmark
    public long longStride1() {
        long sum = 0;
        for (long l = 0; l < MAX; l++) {
            sum += data[( int) l];
        }
        return sum;
    }

    public static void main(String[] args) throws Throwable {
        MethodType mt = MethodType.methodType(int.class);
        MethodHandles.Lookup l = MethodHandles.lookup();
        MethodHandle mh = l. findVirtual( String.class, "hashCode", mt);
        String receiver = "b";
        int ret = (int) mh.invoke(receiver);
        System.out.println( ret);
    }
}

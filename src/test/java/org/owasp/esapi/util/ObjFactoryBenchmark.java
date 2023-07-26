package org.owasp.esapi.util;

import javax.crypto.NullCipher;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@Fork(value = 3)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
public class ObjFactoryBenchmark {

    @Benchmark
    public void benchmark1(Blackhole hole) {
        NullCipher nullCipher = ObjFactory.make("javax.crypto.NullCipher", "NullCipher");
        hole.consume(nullCipher);
    }

    @Benchmark
    public void benchmark2(Blackhole hole) {
        String key = ObjFactory.make("java.lang.String", "testMakeNotASubclass");
        hole.consume(key);
    }

}

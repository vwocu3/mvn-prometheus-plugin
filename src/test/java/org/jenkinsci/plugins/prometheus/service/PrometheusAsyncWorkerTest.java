package org.jenkinsci.plugins.prometheus.service;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

public class PrometheusAsyncWorkerTest {

    @Test
    public void shouldCollectMetrics() {
        // given
        PrometheusAsyncWorker asyncWorker = new PrometheusAsyncWorker();
        PrometheusMetrics metrics = new TestPrometheusMetrics();
        asyncWorker.setPrometheusMetrics(metrics);

        // when
        asyncWorker.execute(null);

        // then
        String actual = metrics.getMetrics();
        assertThat(actual).isEqualTo("1");
    }

    private static class TestPrometheusMetrics implements PrometheusMetrics {
        private final AtomicReference<String> cachedMetrics = new AtomicReference<>("");
        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public String getMetrics() {
            return cachedMetrics.get();
        }

        @Override
        public void collectMetrics() {
            String metrics = String.valueOf(counter.incrementAndGet());
            cachedMetrics.set(metrics);
        }

    }
}

package week5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureLesson6 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println(Thread.currentThread().getName() + " - Starting...");

        CompletableFuture<String> klockiAsync = new AsyncTask("klocki").fetch();
        CompletableFuture<String> tasmaAsync = new AsyncTask("tasma").fetch();
        CompletableFuture<String> papierAsync = new AsyncTask("papier").fetch();

        CompletableFuture<List<String>> listCompletableFuture = klockiAsync.thenCombine(tasmaAsync, (klocki, tasma) -> {
                    List<String> elements = new ArrayList<>();
                    elements.add(klocki);
                    elements.add(tasma);
                    return elements;
                }).thenCombine(papierAsync, (lista, papier) -> {
                    lista.add(papier);
                    return lista;
                })
                .whenComplete((lista, error) -> {
                    if (error != null) {
                        System.err.println("Something went wrong. Unable to proceed with your order");
                    } else {
                        System.out.println("Parcel: " + lista.toString());
                    }
                });

        System.out.println(listCompletableFuture.get());

        System.out.println(Thread.currentThread().getName() + " - Done");

    }

    private static class AsyncTask {

        private String result;

        public AsyncTask(String result) {
            this.result = result;
        }

        public CompletableFuture<String> fetch() {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " - Preparing result");
                    Thread.sleep(2_000);
                    System.out.println(Thread.currentThread().getName() + " - Done - " + result);
                    return result;
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            });
        }
    }

}

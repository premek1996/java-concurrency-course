package week4;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolLesson {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + " - Starting...");

        //ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        ForkJoinPool forkJoinPool = new ForkJoinPool(10);

        TreeNode tree = new TreeNode(
                10,
                new TreeNode(19),
                new TreeNode(7,
                        new TreeNode(3),
                        new TreeNode(8))
                , new TreeNode(1)
        );

        Integer sum = forkJoinPool.invoke(new CountingTask(tree));
        System.out.println(sum);
        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    @Getter
    static class TreeNode {

        private final int value;
        private final List<TreeNode> children;

        public TreeNode(int value, TreeNode... children) {
            this.value = value;
            this.children = List.of(children);
        }

    }

    static class CountingTask extends RecursiveTask<Integer> {

        private final TreeNode treeNode;

        public CountingTask(TreeNode treeNode) {
            this.treeNode = treeNode;
        }

        @Override
        protected Integer compute() {
            System.out.println(Thread.currentThread().getName() + ": Computing value ");
            if (treeNode.getChildren().isEmpty()) {
                return treeNode.getValue();
            } else {
                var tasks = new ArrayList<CountingTask>();
                for (var child : treeNode.getChildren()) {
                    tasks.add(new CountingTask(child));
                }
                for (var countingTask : tasks) {
                    countingTask.fork();
                }
                int sum = treeNode.getValue();
                for (var countingTask : tasks) {
                    sum += countingTask.join();
                }
                return sum;
            }
        }

    }

}

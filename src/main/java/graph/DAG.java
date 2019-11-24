package graph;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAG {

    Map<Integer,List<Integer>> adjacencyList = new HashMap<>();

    public DAG()
    {

    }

    //TODO - return an immutable copy .
    public  Map<Integer,List<Integer>> getAdjacencyList()
    {
        return  adjacencyList;
    }

    public void addNode(int num)
    {

        adjacencyList.put(num,new ArrayList<>());

    }

    public void addEdge(int src, int tgt)
    {

        List<Integer> list  = adjacencyList.computeIfAbsent(src, key-> new ArrayList<>());
        adjacencyList.computeIfAbsent(tgt, key-> new ArrayList<>()); // so that tgt is a node in the graph .


        if (list.contains(tgt))
            return;
        else
            list.add(tgt);
    }

    public void removeEdge(int src, int tgt)
    {
        List<Integer> list = adjacencyList.get(src);

        list.remove((Object)tgt);
    }


    public List<Integer> getEdges(int node)
    {

            return adjacencyList.get(node); //TODO - return a immutable copy here , also Optional

    }

    public static void print(DAG dag)
    {
            var adjacencyList = dag.getAdjacencyList();

            adjacencyList.entrySet().stream().forEach(entry->{

                System.out.println(entry.getKey() + "---> " + entry.getValue());

            });


    }

    public static DAG reverse(DAG dag)
    {
        DAG rev = new DAG();
        for (int i=0;i<dag.getAdjacencyList().size();i++)
        {
            int tgt = i;
            List<Integer> list = dag.getAdjacencyList().get(i);
            for (int j=0;j<list.size();j++)
            {
                int src = list.get(j);
                rev.addEdge(src,tgt);
            }
        }

        return rev;
    }


    public static void main(String[] args) {

        DAG dag = new DAG();
        dag.addEdge(0,1);
        dag.addEdge(0,2);
        dag.addEdge(0,3);
        dag.addEdge(1,2);
        dag.addEdge(1,3);
        dag.addEdge(3,0);
        dag.addEdge(3,1);

        DAG.print(dag);

        System.out.println();

        dag = DAG.reverse(dag);

        DAG.print(dag);

        System.out.println();

        dag = DAG.reverse(dag);

        DAG.print(dag);




    }
}

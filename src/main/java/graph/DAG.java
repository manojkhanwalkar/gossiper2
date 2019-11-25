package graph;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAG<T> {

    Map<T,List<T>> adjacencyList = new HashMap<>();

    public DAG()
    {

    }

    //TODO - return an immutable copy .
    public  Map<T,List<T>> getAdjacencyList()
    {
        return  adjacencyList;
    }

    public void addNode(T num)
    {

        adjacencyList.put(num,new ArrayList<>());

    }

    public void deleteNode(T key)
    {
        adjacencyList.remove(key);
    }

    public void addEdge(T src, T tgt)
    {

        List<T> list  = adjacencyList.computeIfAbsent(src, key-> new ArrayList<>());
        adjacencyList.computeIfAbsent(tgt, key-> new ArrayList<>()); // so that tgt is a node in the graph .


        if (list.contains(tgt))
            return;
        else
            list.add(tgt);
    }

    public void removeEdge(T src, T tgt)
    {
        List<T> list = adjacencyList.get(src);

        list.remove((Object)tgt);
    }


    public List<T> getEdges(T node)
    {

            return adjacencyList.get(node); //TODO - return a immutable copy here , also Optional

    }

    public static<T> void print(DAG<T> dag)
    {
            var adjacencyList = dag.getAdjacencyList();

            adjacencyList.entrySet().stream().forEach(entry->{

                System.out.println(entry.getKey() + "---> " + entry.getValue());

            });


    }

    public static<T> DAG<T> reverse(DAG<T> dag)
    {
        DAG rev = new DAG();
        for (int i=0;i<dag.getAdjacencyList().size();i++)
        {
            int tgt = i;
            List<T> list = dag.getAdjacencyList().get(i);
            for (int j=0;j<list.size();j++)
            {
                T src = list.get(j);
                rev.addEdge(src,tgt);
            }
        }

        return rev;
    }


    public static void main(String[] args) {

        DAG<Integer> dag = new DAG();
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

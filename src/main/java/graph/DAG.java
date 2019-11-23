package graph;



import java.util.ArrayList;
import java.util.List;

public class DAG {

    List<List<Integer>>  adjacencyList = new ArrayList<List<Integer>>();

    public DAG(int initialCapacity)
    {
        for (int i=0;i<initialCapacity;i++)
        {
            adjacencyList.add(new ArrayList<Integer>());
        }
    }

    //TODO - return an immutable copy .
    public List<List<Integer>> getAdjacencyList()
    {
        return  adjacencyList;
    }

    public void addNode(int num)
    {
        if (num<adjacencyList.size())
            return;
        else
        {
            int initialSize = adjacencyList.size();
            for (int i=initialSize;i<=num;i++)
            {
                adjacencyList.add(new ArrayList<Integer>());
            }
        }
    }

    public void addEdge(int src, int tgt)
    {
        int max = (src>tgt? src: tgt);
        addNode(max);

        List<Integer> list = adjacencyList.get(src);
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
        if (node<adjacencyList.size())
        {
            return adjacencyList.get(node); //TODO - return a immutable copy here
        }
        else
        {
            return null;   // TODO - return optional here
        }
    }

    public static void print(DAG dag)
    {
            List<List<Integer>> adjacencyList = dag.getAdjacencyList();

            for (int i=0;i<adjacencyList.size();i++)
            {
                System.out.println(i + "---> " + adjacencyList.get(i));
            }
    }

    public static DAG reverse(DAG dag)
    {
        DAG rev = new DAG(dag.getAdjacencyList().size());
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

        DAG dag = new DAG(4);
        dag.addEdge(0,1);
        dag.addEdge(0,2);
        dag.addEdge(0,3);
        dag.addEdge(1,2);
        dag.addEdge(1,3);
        dag.addEdge(3,0);
        dag.addEdge(3,1);

        DAG.print(dag);

        System.out.println();

        dag = DAG.reverse(DAG.reverse(dag));

        DAG.print(dag);




    }
}
